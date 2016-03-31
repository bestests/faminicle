package chronicle.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import chronicle.domain.Members;

//세션이 존재하지 않거나 사용자 정보가 세션에 없으면 로그인(메인)화면으로 이동하게하는
//인터셉터
public class LoginCheckInterceptor extends HandlerInterceptorAdapter{
	
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,
			Object handler)throws Exception{
		
		System.out.println("01. 인터셉터 In!");
	
		String servletPath = request.getServletPath();
		System.out.println(servletPath);
		
		System.out.println("02. 현재 페이지 주소 : " +servletPath );
		
		Members check = (Members) request.getSession().getAttribute("loginInfo");		
		System.out.println("03. Session 유무 : " + check );
		
		if(!(("/chronicle/login.do".equals(servletPath))||("/chronicle/checkId.do").equals(servletPath)||("/chronicle/registMember.do").equals(servletPath)) && check == null) {
			System.out.println("04. !(Login & CheckId) || Session null");
			response.setContentType("900");
			return false;
		}else if(check!=null){
			System.out.println("05. Session Name : " + check.getName());
		}
				
		return true;
		
		
		
		//↓  창고
		
//		return true;
//		
//		try{
//			if(request.getSession().getAttribute("loginInfo")==null){
//				System.out.println("세션값없음");
//				return false;
//			}
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		/*
		System.out.println("인터셉터 로그인 여부: "+ check);
		
		if(check==null){
			System.out.println("꽝");
			return false;
		}
		System.out.println("연결");
		return true;
		*/

		
		/*
		HttpSession session = request.getSession();
		
		
		if(session == null){
			response.sendRedirect(request.getContextPath()+"/src/main/views/main2.html");
			return false;
		}
		
		Members member = (Members)session.getAttribute("loginInfo");
		
		if(member == null){
			response.sendRedirect(request.getContextPath()+"/src/main/views/main2.html");
			return false;
		}
		
		return true;
		*/		
	}
	
}
