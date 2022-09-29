package semi.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

	@GetMapping("/join")
	public String join() {
		
		return "join/join";
	}
	
	@PostMapping("/join")
	public String joinok() {
		
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
			
		return "join/login";
	}
	
	@PostMapping("/login")
	public String loginok() {
			
		return "redirect:/myinfo";
	}
	
	@GetMapping("/myinfo")
	public String myinfo() {
		
		return "join/myinfo";
	}
	
}
