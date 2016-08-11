package servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.Coding;
import Util.FileOperation;
import bean.Jclasses;
import bean.Jquiz;
import dao.JclassesDAO;
import dao.JquizDAO;
import edu.pitt.sis.paws.cbum.report.ReportAPI;

/**
 * Servlet implementation class EvaluateAnswer
 */
public class EvaluateAnswer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EvaluateAnswer() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * This servlet is after QuizDisplay.jsp and points to
		 * QuizDisplay.jsp(when users click submit) or displayQuiz servlet(when
		 * users click try again)
		 **/
		
//		String sid = "test";
//		String grp = getServletContext().getInitParameter("grp");
//		String usr = getServletContext().getInitParameter("usr");
		String sid = (String) request.getSession().getAttribute("sid");
		String grp = (String) request.getSession().getAttribute("grp");
		String usr = (String) request.getSession().getAttribute("usr");
		
		// what's the functions of these paramters?
		String appID = getServletContext().getInitParameter("appID");
		String domain = getServletContext().getInitParameter("language");
		String appName = getServletContext().getInitParameter("appName");
		
		
		boolean verbose = getServletContext().getInitParameter("verbose").equals("true") ? true : false;
		
		// decide which web page will it redirect
		String kind = request.getParameter("kind");
		// get the id of quiz and get this quiz's info
		String rdfID = request.getParameter("rdfID");
		Jquiz jquiz = null;
		try {
			jquiz = new JquizDAO().getJquiz(rdfID);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("cannot find such jquiz");
		}
		request.setAttribute("jquiz", jquiz);
		String title = request.getParameter("title");
		request.setAttribute("title", title);

		// determine whether it has more than one class
		boolean multiClasses = false;
		ArrayList<String> fileNames = null;
		List<Jclasses> list = new JclassesDAO().getAllClasses(jquiz.getQuizeID());
		if (list.size() >= 1) {
			multiClasses = true;
			fileNames = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				fileNames.add(list.get(i).getClassName());
			}
		}
		if (verbose) {
			System.out.println("FileNames:" + fileNames);
		}
		String appDir = getServletContext().getInitParameter("appDir");
		String language = getServletContext().getInitParameter("language");
		boolean windows = getServletContext().getInitParameter("windows").equals("true") ? true : false;
		String compilerHomeDir = getServletContext().getInitParameter("compilerHomeDir");
//		String usr = getServletContext().getInitParameter("usr");
		String extraClassDir = FileOperation.getExtraClassDir4Multiclasses(appDir, windows);
		String[] extraCode = new String[list.size()];
		// if this quiz has more than one class, the following is to get other
		// class's code
		if (multiClasses) {
			BufferedReader br1 = null;
			for (int i = 0; i < fileNames.size(); i++) {
				extraCode[i] = new String();
				br1 = new BufferedReader(new FileReader(extraClassDir + fileNames.get(i)));
				String fileLine = null;
				while ((fileLine = br1.readLine()) != null) {
					extraCode[i] += fileLine + "\n";
				}
			}
			br1.close();
			request.setAttribute("fileNames", fileNames);
			request.setAttribute("extraCodes", extraCode);
		}

		Integer queType = Integer.parseInt(request.getParameter("type"));
		String userCode = request.getParameter("userCode").replaceAll("<br/>", "\n");
		String codeStr=request.getParameter("codeStr");
		String answer = request.getParameter("answer");
		boolean correct = false;
		request.setAttribute("userCode", userCode);
		request.setAttribute("codeStr", codeStr);
		
		// if kind==1, redirect to QuizDisplay2.jsp
		// if kind==2 redirect to the servlet displayQuiz with
		if ("1".equals(kind)) {
//			request.setAttribute("userCode", userCode);
			if (verbose) {
				System.out.println("userCode:\n" + userCode);
			}

			String curActionDir = FileOperation.createActionFilePath(appDir, compilerHomeDir, usr, windows);
			String testerSurfix = FileOperation.createTempFile(curActionDir, multiClasses, queType, userCode, language,
					null);
			String compile = Coding.getCmd2compile(windows, compilerHomeDir, curActionDir, testerSurfix, language,
					multiClasses);
			String exec = Coding.getCmd2execute(windows, compilerHomeDir, curActionDir, testerSurfix, language,
					multiClasses);
			
			if (verbose) {
				System.out.println("compile2:" + compile);
				System.out.println("exec2: " + exec);
			}
			String userAnswer = null;
			try {
				userAnswer = Coding.runCode(compile, exec, multiClasses, curActionDir, language, testerSurfix, queType,
						list.size(), fileNames, extraClassDir);
				if (verbose) {
					System.out.println("answer:" + answer);
					System.out.println("userAnswer:" + userAnswer);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (FileOperation.stringTransfer(answer).equals(FileOperation.stringTransfer(userAnswer))) {
				correct = true;
			}
			request.setAttribute("correct", correct);
			if (verbose) {
				System.out.println("codepart:" + userCode);
			}

			/********** report to UM:start ********************/
			boolean reportToUM = getServletContext().getInitParameter("reportToUM").equalsIgnoreCase("true") ? true
					: false;
			if (reportToUM) {
				System.out.println("appID:\t" + getServletContext().getInitParameter("appID"));
				int appId = Integer.parseInt(getServletContext().getInitParameter("appID"));// 25;
				String act = jquiz.getTitle();
				String sub = jquiz.getRdfID();
				
				double res = correct ? 1 : 0;
				
				String svc = appName;
				// initialize ReportAPI object with URL of the User Model
				// http://adapt2.sis.pitt.edu/wiki/CUMULATE_protocol
				ReportAPI r_api = new ReportAPI(getServletContext().getInitParameter("userModelURL"));// "http://adapt2.sis.pitt.edu/cbum/um");
				try {
					// Send report
					if (verbose) {
						log("INFO: Reporting to um!");

					}
					System.out.println("ReportAPI:" + appId + "\t" + act + "\t" + sub + "\t" + sid + "\t" + res + "\t"
							+ grp + "\t" + usr + "\t" + svc);
					r_api.report(appId, act, sub, sid, res, grp, usr, svc);
				} catch (IOException ioe) {
					System.out.println(ioe);
				}
			} else {
				log("INFO: Not reporting to um!");
			}
			/***** report to UM:end ************************/

			request.setAttribute("answer", answer);
			request.setAttribute("displayAnswer", FileOperation.stringTransfer(answer));
			request.getRequestDispatcher("QuizDisplay2.jsp?multiClasses=" + multiClasses).forward(request, response);
		} else if ("2".equals(kind)) {
			request.setAttribute("rdfID", rdfID);
			request.getRequestDispatcher("displayQuiz?rdfID=" + jquiz.getRdfID() + "&multiClasses=" + multiClasses)
					.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
