package cz.juzna.pa165.cards.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public abstract class BaseController {

	/** Always assign user attribute to model */
	@ModelAttribute("user")
	public User getUser() {
		return UserServiceFactory.getUserService().getCurrentUser();
	}
	
	/** Always assign loginUrl attribute to model */
	@ModelAttribute("loginUrl")
	public String getLoginUrl(HttpServletRequest request) {
		return UserServiceFactory.getUserService().createLoginURL(request.getRequestURI());
	}
}
