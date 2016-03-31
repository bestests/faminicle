package chronicle.domain;

public class EventDay {
	private int evNo;
	private int memNo;
	private String evTitle;
	private String evStart;
	private String evEnd;
	private String evType;
	private String evColor;
	
	public String getEvColor() {
		return evColor;
	}
	public void setEvColor(String evColor) {
		this.evColor = evColor;
	}
	public int getEvNo() {
		return evNo;
	}
	public void setEvNo(int evNo) {
		this.evNo = evNo;
	}
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public String getEvTitle() {
		return evTitle;
	}
	public void setEvTitle(String evTitle) {
		this.evTitle = evTitle;
	}
	public String getEvStart() {
		return evStart;
	}
	public void setEvStart(String evStart) {
		this.evStart = evStart;
	}
	public String getEvEnd() {
		return evEnd;
	}
	public void setEvEnd(String evEnd) {
		this.evEnd = evEnd;
	}
	public String getEvType() {
		return evType;
	}
	public void setEvType(String evType) {
		this.evType = evType;
	}
}
