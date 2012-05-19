package cz.juzna.pa165.cards.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SystemController {

	@RequestMapping(value="/misplaced")
	public String misplaced(){
		return "misplaced";
	}
}
