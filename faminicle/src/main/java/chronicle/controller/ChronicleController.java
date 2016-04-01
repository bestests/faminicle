package chronicle.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import chronicle.domain.EventDay;
import chronicle.domain.Family;
import chronicle.domain.LoginCheck;
import chronicle.domain.Members;
import chronicle.domain.Page;
import chronicle.domain.Regist;
import chronicle.service.ChronicleService;

@Controller
@RequestMapping("/chronicle/*")
public class ChronicleController {
	
	@Autowired
	ChronicleService service;
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping("list.do")
	public Map<String, Object> list (Page page,HttpServletRequest req) {
		Map<String, Object> result = new HashMap<>();
		Members member = (Members)req.getSession().getAttribute("loginInfo");
		
		System.out.println("list.do에 타고 call값 : "+page.getCall());
		page.setMemNo(member.getMemNo());
		
		String filePath = member.getMemPicPath();
		if(filePath != null) {
			filePath = filePath.substring(0, filePath.lastIndexOf("."));
			filePath += "_mini.jpg";
			member.setPicMiniFilePath(filePath);
		}
		
		result.put("member", member);
		result.put("eventDay", service.selectEvent(member.getMemNo()));
		result.put("registList", service.selectList(page));
		
		return result;
	}
	
	@RequestMapping("next.do")
	public Map<String,Object> nextList(int pageNo,String startDate,String call,HttpServletRequest req){
		
		Page page = new Page(startDate,pageNo);
		page.setCall(call);
		// 자신의 정보값과 가족들과 비교하기 위하여 세션값 사용 ****
		Members member = (Members)req.getSession().getAttribute("loginInfo");
		page.setMemNo(member.getMemNo());
		
		List<Regist> list =service.selectNextList(page); 
		Map<String, Object> result = new HashMap<>();
		result.put("registList", list);
		result.put("member", member);
		
		return result;
	}
//	@RequestMapping("prev.do")
	
	
	
	@RequestMapping("registMember.do")
	public Map<String,Object> insertMember(Members members){	
		
		service.registMember(members);	
		
		Map<String,Object> result = new HashMap<>();
		result.put("data", "전송성공");
//		return new AjaxResult("data","전송성공");
		return result;
	}
	
	@RequestMapping("checkId.do")
	public Map<String,Object> checkId(Members members){
		System.out.println("체크할 아이디 : "+members.getId());
		
		int ckId = service.checkId(members);
		Map<String,Object> result = new HashMap<>();
		result.put("data", ckId);
		
		return result;
	}

	@RequestMapping("checkPass.do")
	public AjaxResult checkPass(Members members, String check) {
		boolean flag = false;
		
		if(check.equals(service.checkPass(members).getPass())) flag = true;
		
		return new AjaxResult("checked", flag);
	}
	
	@RequestMapping("selectInfo.do")
	public AjaxResult selectInfo(Members members) {
		System.out.println(members.getId());
		Members memberInfo = (Members)service.memberInfo(members); 
		
		return new AjaxResult("MemberInfo" , memberInfo);
		
	}
	
	@RequestMapping("login.do")
	public AjaxResult login(LoginCheck loginInfo, HttpSession session){
//		System.out.println(loginInfo.getId()+" : "+loginInfo.getPass());		
		
		Members member = (Members) service.loginCheck(loginInfo);
		
		if(member !=null){
			System.out.println("아이디 찾기성공^^!");
			
//			session.setAttribute("loginInfo", loginInfo);
			session.setAttribute("loginInfo", member);
//			session.setMaxInactiveInterval(3600);
			
			
//			Members mem = (Members)session.getAttribute("loginInfo");
//			System.out.println("세션에서 가져온값 " + mem.getName());
			
			
			
		}else{
			 System.out.println("찾는 아이디 없음");
//	         session.invalidate(); // 세션을 무효화시킴. => 새로 세션 객체 생성!
			
			return new AjaxResult("fail",null);
		}
		//return 객체를 보낸다. html에서 사용시 
		// 받을변수명.return하는객체명.객체내변수명&Object타입으로 접근
		return new AjaxResult("success", member);
	}
	
	@RequestMapping("logout.do")
	public AjaxResult logOut(HttpSession session){
		
		session.invalidate();
		
		return new AjaxResult("logout", null);
	}
	
	@RequestMapping(value="updateMember.do", method=RequestMethod.POST)
	public AjaxResult updateMembers(Members members){
		
		System.out.println(members.getId());
		System.out.println(members.getPass());
		
		service.updateMember(members);

		return new AjaxResult("success", "success"); 
	}
	
	@RequestMapping("updateMemberPic.do")
	@ResponseBody
	public AjaxResult updateMemberPic(HttpSession session, @RequestParam("file") MultipartFile image) throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String realPath = servletContext.getRealPath("/upload/");
		System.out.println("realPath : " + realPath);
		realPath = realPath.replace("\\", "/");
		System.out.println("realPath  dd: " + realPath);
		String sdfPath = sdf.format(new Date());
		String filePath = realPath + sdfPath;
		File file = new File(filePath);
		file.mkdirs();
		String oriFileName = image.getOriginalFilename();
		Members members = (Members) (session.getAttribute("loginInfo")); // 세션 아이디값 넣어 주기
		if(oriFileName != null && !oriFileName.equals("")){
			System.out.println("ofiFileName : " + oriFileName);
			String ext = oriFileName.substring(oriFileName.lastIndexOf("."));
			String realFileName = UUID.randomUUID().toString()+ext;
			String saveFullFileName = filePath + "/" + realFileName;
//			String srcPath = "../upload/" + sdfPath ;
			image.transferTo(new File(saveFullFileName));
			saveFullFileName =saveFullFileName.replace(realPath,"../upload/"); 
			System.out.println("컨트롤러: "+ filePath);
			System.out.println("풀파일네임"+saveFullFileName);
			members.setMemPicPath(saveFullFileName);
			System.out.println(saveFullFileName);
					
			//upload
			 try {
		            //썸네일 가로사이즈
		            int thumbnail_width = 100;
		            //썸네일 세로사이즈
		            int thumbnail_height = 100;
		            //원본이미지파일의 경로+파일명
		            saveFullFileName = filePath+"/"+realFileName;
		            File origin_file_name = new File(saveFullFileName);
		            //생성할 썸네일파일의 경로+썸네일파일명
		            
		            String picMiniFilePath = saveFullFileName.replace(ext, "_mini.jpg");
		            File thumb_file_name = new File(picMiniFilePath);
		            
		            BufferedImage buffer_original_image = ImageIO.read(origin_file_name);
		            BufferedImage buffer_thumbnail_image = new BufferedImage(thumbnail_width, thumbnail_height, BufferedImage.TYPE_3BYTE_BGR);
		            Graphics2D graphic = buffer_thumbnail_image.createGraphics();
		            graphic.drawImage(buffer_original_image, 0, 0, thumbnail_width, thumbnail_height, null);
		            ImageIO.write(buffer_thumbnail_image, "jpg", thumb_file_name);
		            System.out.println("썸네일 생성완료");
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			//**
			
			 service.updateMemberPic(members);
			
			
			AjaxResult result = new AjaxResult("ok", "ok");
			
			return result;
		}
		
		// session.getAttribute("loginInfo") 이렇게 했을떄 리턴되는게 뭔지 
		// 아니면 로그인 함수에서 세션 셋팅할떄 어떤타입으로 셋팅 되는지 봐바
		// A타입으로 set 했으면 받을때도 A타입으로  Get해야함음..
		AjaxResult result = new AjaxResult("ok", "no");
		return result;
		
	}
	
	@RequestMapping("Regist.do")
	public AjaxResult register(Regist regist, MultipartHttpServletRequest mRequest) throws Exception{
		Members member = (Members)mRequest.getSession().getAttribute("loginInfo");
		regist.setMemNo(member.getMemNo()+"");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
		String realPath = servletContext.getRealPath("/upload/");
		String sdfPath = sdf.format(new Date());
		String filePath = realPath + sdfPath;
		File file = new File(filePath);
		file.mkdirs();
		Iterator<String> iter = mRequest.getFileNames();
		if(iter.hasNext()){
			MultipartFile mFile =  mRequest.getFile(iter.next());
			String oriFileName = mFile.getOriginalFilename();
			System.out.println(oriFileName);
			if(oriFileName != null && !oriFileName.equals("")){
				String ext = oriFileName.substring(oriFileName.lastIndexOf("."));
				String realFileName = UUID.randomUUID().toString()+ext;
				String saveFullFileName = filePath+"/"+realFileName;
				String srcPath = "../upload/"+ sdfPath;
				mFile.transferTo(new File(saveFullFileName));
				regist.setPicFilePath(srcPath+realFileName);
				try {
		            //썸네일 가로사이즈
		            int thumbnail_width = 100;
		            //썸네일 세로사이즈
		            int thumbnail_height = 100;
		            //원본이미지파일의 경로+파일명
		            saveFullFileName = filePath+"/"+realFileName;
		            File origin_file_name = new File(saveFullFileName);
		            //생성할 썸네일파일의 경로+썸네일파일명
		            
		            String picMiniFilePath = saveFullFileName.replace(ext, "_mini.jpg");
		            File thumb_file_name = new File(picMiniFilePath);
		            
		            BufferedImage buffer_original_image = ImageIO.read(origin_file_name);
		            BufferedImage buffer_thumbnail_image = new BufferedImage(thumbnail_width, thumbnail_height, BufferedImage.TYPE_3BYTE_BGR);
		            Graphics2D graphic = buffer_thumbnail_image.createGraphics();
		            graphic.drawImage(buffer_original_image, 0, 0, thumbnail_width, thumbnail_height, null);
		            ImageIO.write(buffer_thumbnail_image, "jpg", thumb_file_name);
		            System.out.println("썸네일 생성완료");
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
		}
		service.registpic(regist);
		return new AjaxResult("ok", "ok");
	}
	
	@RequestMapping("selectEvent.do")
	public AjaxResult selectEventDay(HttpServletRequest req){	
		Members member = (Members)req.getSession().getAttribute("loginInfo");
		List<EventDay> eList = service.selectEvent(member.getMemNo());
		return new AjaxResult("success", eList);
	}
	
	@RequestMapping("eventRegist.do")
	public EventDay eventRegist (EventDay evDay) {
		System.out.println(evDay.getEvColor());
		System.out.println(evDay.getEvTitle());
		System.out.println(evDay.getEvStart());
		System.out.println(evDay.getMemNo());
		
		if(evDay.getEvEnd() == null || evDay.getEvEnd().equals("")) {
			evDay.setEvEnd("1000-01-01");
		}
		
		return service.registEvent(evDay);
	}
	
	@RequestMapping("deleteEvent.do")
	public AjaxResult deleteEvent (int evNo) {
		service.deleteEvent(evNo);
		
		AjaxResult result = new AjaxResult("success", "OK");
		
		return result;
	}
	
	@RequestMapping("eventList.do")
	public AjaxResult eventList (EventDay evDay) {
		System.out.println("start : " + evDay.getEvStart());
		System.out.println("end : " + evDay.getEvEnd());
		return null;
	}
	
	@RequestMapping("update.do")
	public AjaxResult update (Regist chronicle) {
		
		service.updateEvent(chronicle);
		
		AjaxResult result = new AjaxResult("success", chronicle);
		return result;		
	}
	
	@RequestMapping("delete.do")
	public AjaxResult delete (int no) {
		System.out.println(no);
//		int no1 = (Integer)no;
//		System.out.println("삭제 번호:"+no1);
		
		
		service.deletePic(no);
		AjaxResult result = new AjaxResult("success", "ok");
		return result;		
	}
	
	@RequestMapping("seletePicByEvent.do")
	public AjaxResult seletePicByEvent (HttpServletRequest req, EventDay evDay){
		Members member = (Members)req.getSession().getAttribute("loginInfo");
		evDay.setMemNo(member.getMemNo());
		
		return new AjaxResult("success", service.seletePicByEvent(evDay));
	}
	
	@RequestMapping("registFam.do")
	public AjaxResult registFam (Family fam) {
		System.out.println("controller : " + fam.getFamReqIdNo());
		System.out.println("controller : " + fam.getFamResIdNo());
		
		service.registFam(fam);
		
		return null;
	}
}

	

