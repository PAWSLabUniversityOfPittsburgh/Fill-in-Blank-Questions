package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Util.DBConnect;
import bean.Jquestion;

public class JquestionDAO {
	private DBConnect dbc;
	private ResultSet rs;
	/**
	 * get all question from database
	 * @param language py or java
	 * @return A list
	 * @throws SQLException
	 */
	public List<Jquestion> getListQuestion(String language) throws SQLException{
		String sql="select q.QuestionID, q.Title, count(*) FROM ent_jquiz z, rel_question_quiz r, ent_jquestion q "
				+ "where q.Privacy='1' and z.Privacy='1' and z.QuizID=r.QuizID and q.QuestionID=r.QuestionID and q.domain=? "
				+ "group by r.QuestionID";
		List<Jquestion> list=new ArrayList<Jquestion>();
		dbc=new DBConnect();
		dbc.prepareStatement(sql);
		dbc.setString(1, language);
		rs=dbc.executeQuery();
		while(rs.next()){
			Jquestion q=new Jquestion();
			q.setQuestionID(rs.getInt(1));
			q.setTitle(rs.getString(2));
			list.add(q);
		}
		dbc.close();
		return list;
	}
	
	
}
