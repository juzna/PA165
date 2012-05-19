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
@RequestMapping("/browse/*")
public class BrowseController {

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String browse(ModelMap model, HttpServletRequest request) {

        // get offset from request param "offset"
        // get limit from request param "limit"


		// model.addAttribute("user", …);
		// model.addAttribute("cards", …); // get cards to display, consider offset, limit and order
		// model.addAttribute("groups", …); // get all user's groups to display in sidebar

		return "browse";
	}

    @RequestMapping(value="/group/{groupId}", method = RequestMethod.GET)
    public String browseGroup(ModelMap model, HttpServletRequest request,  @PathVariable String groupId) {

        // model.addAttribute("user", …);
        // model.addAttribute("cards", …); get all cards in group
        // model.addAttribute("groups", …);

        return "browseGroup";
    }

    @RequestMapping(value="/card/{cardId}", method = RequestMethod.GET)
    public String browseCard(ModelMap model, HttpServletRequest request, @PathVariable String cardId) {

        // model.addAttribute("user", …);
        // model.addAttribute("card", …); // get all card attributes (id, name, owner, addedAt, private, ...
        // model.addAttribute("relatedCards", …); // get Related cards, for example siblings in database
        // model.addAttribute("relatedGroups", …); // get user's groups which contains this card

        return "browseCard";
    }

}
