package cz.juzna.pa165.cards.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SystemController {
        
        /**
         * Sem se budou posilat vsechny nezname pozadavky url
         * @return redirect to Misplaced.jsp
         */
	@RequestMapping(value="*", method = RequestMethod.GET)
	public String misplaced(){
		return "Misplaced";
	}
}
