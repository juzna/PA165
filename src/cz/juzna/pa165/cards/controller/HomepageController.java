package cz.juzna.pa165.cards.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.juzna.pa165.cards.dao.CardDao;

@Controller
@RequestMapping(value = "/")
public class HomepageController extends BaseController {

	@Autowired
	private CardDao cards;

	@RequestMapping(method = RequestMethod.GET)
	public String menu(ModelMap model, HttpServletRequest request) {
		model.addAttribute("recentPublicCards", cards.getCards(0, 12));
		return "homepage/default";
    }
}
