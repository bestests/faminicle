package chronicle.domain;

public class Chronicle {
	private int no;
	private String content;
	private String regDate;
	private String picDate;
	private String filePath;
	private Object rNum;
	
	public Object getrNum() {
		return rNum;
	}
	public void setrNum(Object rNum) {
		this.rNum = rNum;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getPicDate() {
		return picDate;
	}
	public void setPicDate(String picDate) {
		this.picDate = picDate;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
