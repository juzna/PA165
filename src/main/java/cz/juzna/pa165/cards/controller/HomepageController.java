package cz.juzna.pa165.cards.controller;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/")
public class HomepageController {

	@Autowired
	private CardDao cards;
	@Autowired
	private GroupDao groups;

	@RequestMapping(method = RequestMethod.GET)
	public String menu(ModelMap model, HttpServletRequest request) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		model.addAttribute("user", user);
		model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));
		model.addAttribute("cards", cards.getAllCards());

		return "Homepage";
	}
}
