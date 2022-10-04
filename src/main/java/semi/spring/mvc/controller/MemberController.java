package semi.spring.mvc.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import semi.spring.mvc.service.MemberService;
import semi.spring.mvc.vo.MemberVO;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService msrv;
	
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());
	// 로그 유형 : trace, debug, info, warn, error
	

	@GetMapping("/join")
	public String join() {
		LOGGER.info("join 호출!");
		
		return "join/join";
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
	
	@GetMapping("/myinfo")
	public String myinfo(Model m) {
		
		m.addAttribute("mbr", msrv.readOneMember());		
		
		return "join/myinfo";
	}
	
}
