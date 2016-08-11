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

/**
 * Servlet implementation class displayQuiz
 */
public class displayQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public displayQuiz() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//get usr, grp and sid
		request.getSession().setAttribute("usr", request.getParameter("usr"));
		request.getSession().setAttribute("grp", request.getParameter("grp"));
		request.getSession().setAttribute("sid", request.getParameter("sid"));
//		String usr=(String) request.getSession().getAttribute("user");
		String usr = getServletContext().getInitParameter("usr");
		
		
		
		/** This servlet is after menu.jsp and points to QuizDisplay.jsp **/
		boolean verbose = getServletContext().getInitParameter("verbose").equals("true") ? true : false;
		// get and set the title of the quiz
		String title = request.getParameter("title");
		if (title == null) {
			title = (String) request.getAttribute("title");
		}
		request.setAttribute("title", title);

		// get and set the rdfID of the quiz
		String rdfID = request.getParameter("rdfID");
		if (rdfID == null) {
			rdfID = (String) request.getAttribute("rdfID");
		}
		if (verbose) {
			System.out.println("rdfID: " + rdfID);
		}

		// get all info of a certain quiz according to rdfID
		Jquiz jquiz = null;
		try {
			jquiz = new JquizDAO().getJquiz(rdfID);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("cannot find such jquiz");
		}

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
		String[] extraCode = null;
		// get the specified code according to the quiz (main class's code)
		String codeStr = Coding.getCode(jquiz);
		if (verbose) {
			System.out.println(Coding.code2Exec(codeStr));
		}

		// determine the situation of OS
		String appDir = getServletContext().getInitParameter("appDir");
		String language = getServletContext().getInitParameter("language");
		boolean windows = getServletContext().getInitParameter("windows").equals("true") ? true : false;
		String compilerHomeDir = getServletContext().getInitParameter("compilerHomeDir");
		
		String extraClassDir = FileOperation.getExtraClassDir4Multiclasses(appDir, windows);
		extraCode = new String[list.size()];
		// if this quiz has more than one class, the following is to get
		// other
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
		}

		// Generate the commands for compiling and executing, and then get
		// the
		// result based the codes.
		String curActionDir = FileOperation.createActionFilePath(appDir, compilerHomeDir, usr, windows);
		String testerSurfix = FileOperation.createTempFile(curActionDir, multiClasses, jquiz.getQuesType(),
				Coding.code2Exec(codeStr), language, null);
		String compile = Coding.getCmd2compile(windows, compilerHomeDir, curActionDir, testerSurfix, language,
				multiClasses);
		String exec = Coding.getCmd2execute(windows, compilerHomeDir, curActionDir, testerSurfix, language,
				multiClasses);
		String answer = null;
		if (verbose) {
			System.out.println("compile:" + compile);
			System.out.println("exec: " + exec);
		}
		try {
			answer = Coding.runCode(compile, exec, multiClasses, curActionDir, language, testerSurfix,
					jquiz.getQuesType(), list.size(), fileNames, extraClassDir);
			if (verbose) {
				System.out.println("answer:\t" + answer);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(codeStr);
		
		
		request.setAttribute("codeStr", codeStr);
		request.setAttribute("displayAnswer", FileOperation.stringTransfer(answer));
		request.setAttribute("answer", answer);

		request.setAttribute("jquiz", jquiz);
		request.setAttribute("multiClasses", multiClasses);
		if (multiClasses) {
			request.setAttribute("fileNames", fileNames);
			request.setAttribute("extraCodes", extraCode);
		}
		request.getRequestDispatcher("QuizDisplay2.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
