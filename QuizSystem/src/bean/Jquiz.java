package bean;

import java.sql.Blob;

public class Jquiz {
	private int quizeID;
	private String Title;
	private Blob code;
	private int start;
	private int end;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	private String rdfID;
	private int AnsType;
	private int minVar;
	private int maxVar;
	private int privacy;
	private int quesType;
	public int getQuizeID() {
		return quizeID;
	}
	public void setQuizeID(int quizeID) {
		this.quizeID = quizeID;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public Blob getCode() {
		return code;
	}
	public void setCode(Blob code) {
		this.code = code;
	}
	public int getMinVar() {
		return minVar;
	}
	public void setMinVar(int minVar) {
		this.minVar = minVar;
	}
	public int getMaxVar() {
		return maxVar;
	}
	public void setMaxVar(int maxVar) {
		this.maxVar = maxVar;
	}
	public int getPrivacy() {
		return privacy;
	}
	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}
	public int getQuesType() {
		return quesType;
	}
	public void setQuesType(int quesType) {
		this.quesType = quesType;
	}
	public String getRdfID() {
		return rdfID;
	}
	public void setRdfID(String rdfID) {
		this.rdfID = rdfID;
	}
	public int getAnsType() {
		return AnsType;
	}
	public void setAnsType(int ansType) {
		AnsType = ansType;
	}
	
	
}
