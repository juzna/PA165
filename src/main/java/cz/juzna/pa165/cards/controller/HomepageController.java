package cz.juzna.pa165.cards.controller;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.util.CardByDateComparator;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.util.CardsUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class HomepageController {

	@Autowired
	private CardDao cards;
	//@Autowired
	//private GroupDao groups;
        
        /**
         * nejnovejsi vizitky "recentCard" a
	 * bez tagu se musite obejit, protoze to je hodne casove narocne (strojove) a 
	 * musela bych nad tim travit moc casu, abych to implementovala efektivne
         * @param model
         * @param request
         * @return 
         */
	@RequestMapping(method = RequestMethod.GET)
	public String menu(ModelMap model, HttpServletRequest request) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		model.addAttribute("user", user);
		model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));
                
		List<Card> publicCards = new ArrayList<Card>(CardsUtil.getPublicCards(cards.getAllCards()));
				
		Collections.sort(publicCards, new CardByDateComparator());
                
                model.addAttribute("recentPublicCards", publicCards); // recent public cards

		return "homepage/default";
	}
}
