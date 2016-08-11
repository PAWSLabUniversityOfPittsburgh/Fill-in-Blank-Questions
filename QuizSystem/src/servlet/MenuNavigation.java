package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Jquestion;
import dao.JquestionDAO;

/**
 * Servlet implementation class MenuNavigation
 */
public class MenuNavigation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuNavigation() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Jquestion> list=null;
		try {
			list=new JquestionDAO().getListQuestion(getServletContext().getInitParameter("language"));
		} catch (SQLException e) {
			System.out.println("cannot get that list");
			e.printStackTrace();
		}
		request.setAttribute("list", list);
		request.getRequestDispatcher("menu.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
