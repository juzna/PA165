package cz.riema.pa165.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import cz.juzna.pa165.cards.dao.CardDao;
//import cz.juzna.pa165.cards.dao.GroupDao;

@Controller
@RequestMapping(value = "/")
public class MenuController {
	
	/*
	 * Pro p��pad, kdyby bylo pot�eba n�co na hlavn� str�nce
	 * @Autowired
	 * private CardDao cards;
	 *
	 * @Autowired
	 * private GroupDao groups;
	 */
	
	@RequestMapping(method = RequestMethod.GET)
	public String menu() {
		return "Menu";
	}
}
