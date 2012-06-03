package cz.juzna.pa165.cards.util;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.http.HttpServletRequest;

public class UserFactory {

	public User getUser() {
		return UserServiceFactory.getUserService().getCurrentUser();
	}
}
