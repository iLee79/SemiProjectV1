package semi.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@GetMapping("/")
	public String index(Model m) {
		
		m.addAttribute("sayHello","Hello, World!!");
				
		return "index";
	}
	
}
