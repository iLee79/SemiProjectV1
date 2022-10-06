package semi.spring.mvc.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import semi.spring.mvc.service.MemberService;
import semi.spring.mvc.vo.MemberVO;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService msrv;
	
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());
	// 로그 유형 : trace, debug, info, warn, error
	

	// 비로그인 -> join/join
	// 로그인 -> join/myiinfo
	@GetMapping("/join")
	public String join(HttpSession sess) {
		String returnPage = "join/join";
		//LOGGER.info("join 호출!");
		if (sess.getAttribute("m") != null) {
			returnPage = "redirect:/myinfo";
		}
		
		return returnPage;
	}
	
	@PostMapping("/join")
	public String joinok(MemberVO mvo) {		
		LOGGER.info("joinok 호출! {}", mvo);
		
		// 회원정보 저장
		if (msrv.newMember(mvo)) {
			LOGGER.info("회원가입 성공~!!");
		}
		
		return "redirect:/login"; 
	}
	
	@GetMapping("/login")
	public String login() {
			
		return "join/login";
	}
	
	@PostMapping("/login")
	public String loginok(MemberVO mvo, HttpSession sess) {
		String returnPage = "join/lgnfail";
		
		if (msrv.checkLogin(mvo)) {
			sess.setAttribute("m",mvo); // 회원정보를 세션에 저장
			returnPage = "redirect:/myinfo";
		}
		
		return returnPage;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession sess) {
		
		sess.invalidate();
		
		return "redirect:/";
	}
	
	// 로그아웃 상태 -> redirect:/login
	// 로그인  상태 -> join/myinfo
	@GetMapping("/myinfo")
	public String myinfo(Model m, HttpSession sess) {
		String returnPage = "redirect:/login";
		
		if (sess.getAttribute("m") != null) {
			MemberVO mvo = (MemberVO) sess.getAttribute("m");
			m.addAttribute("mbr", msrv.readOneMember(mvo.getUserid()));
			returnPage = "join/myinfo";			
		}
		
		return returnPage;
	}
	
	// 아이디 중복검사 - REST api 이용
	@ResponseBody
	@GetMapping("/checkuid")
	public String checkuid(String uid) {
		String result = "잘못된 방식으로 호출하였습니다!!";
		if (uid != null || !uid.equals("")) {
			result = msrv.checkUid(uid);
		}
		
		return result;
	}
	
}











