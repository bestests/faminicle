package chronicle.dao;

import java.util.List;

import chronicle.domain.EventDay;
import chronicle.domain.FamMember;
import chronicle.domain.Family;
import chronicle.domain.LoginCheck;
import chronicle.domain.Members;
import chronicle.domain.Page;
import chronicle.domain.Regist;

public interface ChronicleDAO {
	//쿼리문에서 id값(insert) result타입을 같게해주면 자동적으로 xml에 들어가서 처리해서 반환값으로 자동 처리하게해준다.
	public List<Regist> selectList(Page page);
	public List<Regist> selectNextList(Page page);
	public List<Regist> selectPrevList(Page page);
	public void insertMember(Members members);
	public Integer checkId(Members members);
	public Members loginCheck(LoginCheck loginInfo);
	public void insertpic(Regist regist);
	public int insertEvent(EventDay evDay);
	public EventDay selectEventOne(int enNo);
	public List<EventDay> selectEventByMem(int memNo);
	public void deleteEvent(int evNo);
	public void updateEvent(Regist chronicle);
	public void deletePic(int no);
	public Members checkPass(Members members);
	public void updateMember(Members members);
	public Members memberInfo(Members members);
	public void updateMemberPic(Members members);
	public List<Regist> seletePicByEvent(EventDay evDay);
	public void updateFamAfterAccept(Family fam);
	public int selectFamByName(String famName);
	public FamMember selectfam(int memNo);
	public int selectFamReq(int memNo);
	public void insertFamMember(FamMember famMem);
}
