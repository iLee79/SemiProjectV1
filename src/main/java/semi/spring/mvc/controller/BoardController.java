package semi.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

	@GetMapping("/list")
	public String list() {
		
		return "board/list";
	}
	
	@GetMapping("/view")
	public String view() {
		
		return "board/view";
	}
	
	@GetMapping("/write")
	public String write() {
		
		return "board/write";
	}	
	
}
