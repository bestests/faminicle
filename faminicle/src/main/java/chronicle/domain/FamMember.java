package chronicle.domain;

public class FamMember {
	private String famName;
	private int memNo;
	
	public FamMember(String famName,int memNo){
		this.famName = famName;
		this.memNo = memNo;
	}
	public String getFamName() {
		return famName;
	}
	public int getMemNo() {
		return memNo;
	}

}
