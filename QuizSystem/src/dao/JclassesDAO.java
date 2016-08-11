package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import Util.DBConnect;
import bean.Jclasses;

public class JclassesDAO {
	private DBConnect dbc;
	private ResultSet rs;

	/**
	 * get all classes according to the id of quiz
	 * @param quizId
	 * @return
	 */
	public List<Jclasses> getAllClasses(int quizId) {
		List<Jclasses> list = new ArrayList<>();
		String sql = "select c.ClassID,ClassName,ClassTester,QuizID "
				+ "from ent_class c, rel_quiz_class r where c.ClassID = r.ClassID and r.QuizID= ?";
		try {
			dbc = new DBConnect();
			dbc.prepareStatement(sql);
			dbc.setInt(1, quizId);
			rs=dbc.executeQuery();
			while(rs.next()){
				Jclasses jclasses=new Jclasses();
				jclasses.setClassId(rs.getInt(1));
				jclasses.setClassName(rs.getString("ClassName"));
				jclasses.setClassTester(rs.getString("ClassTester"));
				jclasses.setQuizID(rs.getInt("QuizID"));
				list.add(jclasses);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

}
