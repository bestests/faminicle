package chronicle.domain;

public class Family {
	int famNo;
	int famReqIdNo;
	int famResIdNo;
	String famName;
	String reqStatus;
	
	public int getFamNo() {
		return famNo;
	}
	public void setFamNo(int famNo) {
		this.famNo = famNo;
	}
	public String getFamName() {
		return famName;
	}
	public void setFamName(String famName) {
		this.famName = famName;
	}
	public String getReqStatus() {
		return reqStatus;
	}
	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}
	public int getFamReqIdNo() {
		return famReqIdNo;
	}
	public void setFamReqIdNo(int famReqIdNo) {
		this.famReqIdNo = famReqIdNo;
	}
	public int getFamResIdNo() {
		return famResIdNo;
	}
	public void setFamResIdNo(int famResIdNo) {
		this.famResIdNo = famResIdNo;
	}
	
}
