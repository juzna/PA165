package cz.juzna.pa165.cards.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public final class HelloWorldController {
	@RequestMapping(value = "/hello",  method = RequestMethod.GET)
	public String HelloWorld() {
		return "Hello";
	}
}