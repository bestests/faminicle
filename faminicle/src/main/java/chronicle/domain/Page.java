package chronicle.domain;

public class Page {
	private String startDate;
	private int pageNo;
	private int memNo;
	private String call;
	
	public Page(){}
	public Page(String startDate,int pageNo){
		this.startDate = startDate;
		this.pageNo = (pageNo -1 ) * 24;
	}

	public String getStartDate() {
		return startDate;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getMemNo() {
		return memNo;
	}

	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}
	
	
	
	
}
