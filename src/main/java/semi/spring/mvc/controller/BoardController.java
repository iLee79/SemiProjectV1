package semi.spring.mvc.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import semi.spring.mvc.service.BoardService;
import semi.spring.mvc.vo.BoardVO;

@Controller
public class BoardController {
	

	protected Logger LOGGER = LoggerFactory.getLogger(getClass());
	// 로그 유형 : trace, debug, info, warn, error
		
	// bean클래스로 정의한 경우, @Autowired 어노테이션 생략가능
	@Autowired
	private BoardService bsrv;
	
	/* 페이징 처리
	 * 페이지당 게시물 수 perPage : 25
	 * 총페이지수 : 전체게시물수/페이지당게시물수
	 * 총페이지수 pages = ceil(getTotalPage / perPage)
	 * ex) 2=50/25, 3=51/25
	 * 
	 * 페이지별 읽어올 게시글 범위
	 * 총 게시글이 55건이라 할때
	 * 1page :  1번째~25번째 게시글 읽어옴
	 * 2page : 26번째~50번째 게시글 읽어옴
	 * 3page : 51번째~75번째 게시글 읽어옴
	 * ...
	 * ipage : m번째 ~ n번째 게시글 읽어옴 
	 * m=(i-1)*25+1 
	 * 
	 * 현재페이지에 따라서 보여줄 페이지 블럭 결정
	 * ex) 총페이지가 27일때
	 * cpg = 1 : 1 2 3 4 5 6 7 8 10
	 * cpg = 5 : 1 2 3 4 5 6 7 8 10
	 * cpg = 6 : 1 2 3 4 5 6 7 8 10
	 * cpg = 10 : 1 2 3 4 5 6 7 8 10
	 * cpg = 11 : 11 12 13 14 15 16 17 18 20
	 * cpg = 17 : 11 12 13 14 15 16 17 18 20
	 * cpg = 23 : 21 22 23 24 25 26 27
	 * cpg = 26 : 21 22 23 24 25 26 27
	 * ...
	 * cpg = n  : ?+0 ?+1 ?+2 ... ?+9
	 * stpgn = (int(cpg-1)/10)*10 + 1
	 * 
	 */
	
	@GetMapping("/list")
	public String list(Model m, String cpg, String fkey, String fval) {
		
		int perPage = 25;
		if (cpg == null || cpg.equals("")) cpg = "1";
		if (fkey == null) fkey = "";
		int cpage = Integer.parseInt(cpg);
		int snum = (cpage - 1) * perPage; 
		int stpgn = ((cpage - 1)/10)*10 + 1;		
		
		m.addAttribute("pages", bsrv.readCountBoard(fkey, fval));
		m.addAttribute("bdlist", bsrv.readBoard(fkey, fval, snum));
		m.addAttribute("stpgn", stpgn);
		//m.addAttribute("cpg", Integer.parseInt(cpg));		
				
		return "board/list";
	}
	
	@GetMapping("/view")
	public ModelAndView view(ModelAndView mv, String bno) {
		mv.setViewName("board/view");
		mv.addObject("bd", bsrv.readOneBoard(bno));
		
		return mv;
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
