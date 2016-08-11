package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Util.DBConnect;
import bean.Jquiz;

public class JquizDAO {
	private DBConnect dbc;
	private ResultSet rs;
	
	/**
	 * get all quiz according to the qustionId
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public List<Jquiz> getListQuizebyQuestionID(int id ) throws SQLException{
		List<Jquiz> list=new ArrayList<Jquiz>();
		String sql=" SELECT distinct z.QuizID,z.Title as ContentTitle,z.rdfID,q.Title as TopicTitle,z.QuesType "
				+ "FROM ent_jquiz z,rel_question_quiz r,ent_jquestion q "
				+ "where z.Privacy='1' and z.QuizID=r.QuizID and q.QuestionID=r.QuestionID and r.QuestionID=?";
		dbc=new DBConnect();
		dbc.prepareStatement(sql);
		dbc.setInt(1, id);
		rs=dbc.executeQuery();
		while(rs.next()){
			Jquiz q=new Jquiz();
			q.setQuizeID(rs.getInt("QuizID"));
			q.setTitle(rs.getString("ContentTitle"));
			q.setRdfID(rs.getString("rdfID"));
			q.setQuesType(rs.getInt("QuesType"));
			list.add(q);
			
		}
		dbc.close();
		return list;
	}
	
	/**
	 * get the total quiz's info according to the rdfID
	 * @param rdfID
	 * @return
	 * @throws SQLException
	 */
	public Jquiz getJquiz(String rdfID) throws SQLException{
		Jquiz jquiz=null;
		String sql="select QuizID,Code,MinVar,MaxVar,AnsType,QuesType,Title,start,end from ent_jquiz where rdfID=?";
		dbc=new DBConnect();
		dbc.prepareStatement(sql);;
		dbc.setString(1, rdfID);
		rs=dbc.executeQuery();
		if(rs.next()){
			jquiz=new Jquiz();
			jquiz.setQuizeID(rs.getInt("QuizID"));
			jquiz.setCode(rs.getBlob("Code"));
			jquiz.setMinVar(rs.getInt("MinVar"));
			jquiz.setMaxVar(rs.getInt("MaxVar"));
			jquiz.setAnsType(rs.getInt("AnsType"));
			jquiz.setQuesType(rs.getInt("QuesType"));
			jquiz.setTitle(rs.getString("Title"));
			jquiz.setRdfID(rdfID);
			jquiz.setStart(rs.getInt("start"));
			jquiz.setEnd(rs.getInt("end"));
		}
		return jquiz;
	}
	
	/**
	 * get the total quiz's info according to the quizID
	 * @param quizID
	 * @return
	 * @throws SQLException
	 */
	public Jquiz getJquizByQuizId(int quizID) throws SQLException{
		Jquiz jquiz=null;
		String sql="select QuizID,Code,MinVar,MaxVar,AnsType,QuesType,Title,rdfID,start,end from ent_jquiz where QuizID=?";
		dbc=new DBConnect();
		dbc.prepareStatement(sql);;
		dbc.setInt(1, quizID);
		rs=dbc.executeQuery();
		if(rs.next()){
			jquiz=new Jquiz();
			jquiz.setQuizeID(rs.getInt("QuizID"));
			jquiz.setCode(rs.getBlob("Code"));
			jquiz.setMinVar(rs.getInt("MinVar"));
			jquiz.setMaxVar(rs.getInt("MaxVar"));
			jquiz.setAnsType(rs.getInt("AnsType"));
			jquiz.setQuesType(rs.getInt("QuesType"));
			jquiz.setTitle(rs.getString("Title"));
			jquiz.setRdfID(rs.getString("rdfID"));
			jquiz.setStart(rs.getInt("start"));
			jquiz.setEnd(rs.getInt("end"));
		}
		return jquiz;
	}
}
