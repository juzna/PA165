package cz.riema.pa165.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SytemController {

	@RequestMapping(value="/misplaced")
	public String misplaced(){
		return "Misplaced";
	}
}
