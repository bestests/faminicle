package chronicle.domain;

public class Page {
	private String startDate;
	private int pageNo;
	
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
	
}
