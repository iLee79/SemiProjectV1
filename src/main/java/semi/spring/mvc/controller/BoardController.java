package semi.spring.mvc.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import semi.spring.mvc.service.BoardService;
import semi.spring.mvc.vo.BoardVO;

@Controller
public class BoardController {
	

	protected Logger LOGGER = LoggerFactory.getLogger(getClass());
	// 로그 유형 : trace, debug, info, warn, error
		
	// bean클래스로 정의한 경우, @Autowired 어노테이션 생략가능
	@Autowired
	private BoardService bsrv;
	
	@GetMapping("/list")
	public String list(Model m) {
		m.addAttribute("bdlist", bsrv.readBoard());
				
		return "board/list";
	}
	
	@GetMapping("/view")
	public String view() {
		
		return "board/view";
	}
	
	@GetMapping("/write")
	public String write(HttpSession sess) {
		String returnPage = "redirect:/login";
		
		if (sess.getAttribute("m") != null) {
			returnPage = "board/write";
		}
		
		return returnPage;
	}
	
	@PostMapping("/write")
	public String writeok(BoardVO bvo) {
		bsrv.newBoard(bvo);
		
		return "redirect:/list";
	}		
	
}
