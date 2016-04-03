package chronicle.service;

import java.util.List;

import chronicle.domain.EventDay;
import chronicle.domain.FamMember;
import chronicle.domain.Family;
import chronicle.domain.LoginCheck;
import chronicle.domain.Members;
import chronicle.domain.Page;
import chronicle.domain.Regist;

public interface ChronicleService {
	public List<Regist> selectList(Page page);
	public List<Regist> selectNextList(Page page);
	public List<Regist> selectPrevList(Page page);
	public void registMember(Members members);
	public Integer checkId(Members members);
	public Members loginCheck(LoginCheck loginInfo);
	public void registpic(Regist regist);
	public EventDay registEvent(EventDay evDay);
	public List<EventDay> selectEvent(int memNo);
	public void deleteEvent(int evNo);
	public void updateEvent(Regist chronicle);
	public void deletePic(int no);
	public Members checkPass(Members members);
	public Members memberInfo(Members members); 
	public void updateMember(Members members);
	public void updateMemberPic(Members members);
	public List<Regist> seletePicByEvent(EventDay evDay);
	public void registFam(Family fam);
	public FamMember selectFam(int memNo);
}
