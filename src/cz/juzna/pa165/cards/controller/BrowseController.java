package cz.juzna.pa165.cards.controller;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.GroupDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/browse")
public class BrowseController extends BaseController {

    @Autowired
    private CardDao cards;
    @Autowired
    private GroupDao groups;

    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String browse(Model model, HttpServletRequest request) {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		List<Card> publicCards = cards.getPublicCards();
		model.addAttribute("cards", publicCards);
	
		Integer offset = Integer.getInteger(request.getParameter("offset")); //offset jako kolikata stranka
		Integer limit = Integer.getInteger(request.getParameter("limit")); //pocet prvku na stranku
		
		// model.addAttribute("cards", new ArrayList<Card>(cards.getAllCards())); // browse panel
		model.addAttribute("recentPublicCards", cards.getPublicCards()); // side panel
		if (user != null) {
		    model.addAttribute("myGroups", new ArrayList<Group>(groups.findGroupsByOwner(user))); // side panel
		}
		
		return "browse/default";
    }
    
    @RequestMapping(value = "/card/{cardId}", method = RequestMethod.GET)
    public String browseCard(@PathVariable Long cardId, Model model) {
    	
    	UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
	
		Card card = cards.findCardById(cardId);
	
		if (card != null && (!card.isPrivate() || card.getOwner().equals(user))) {
		    model.addAttribute("card", card);
		    model.addAttribute("relatedGroups", cards.getGroupsOfCard(card));
		}
		
		// model.addAttribute("relatedCards", …); // get Related cards, for example siblings in database
		// model.addAttribute("relatedGroups", …); // get user's groups which contains this card
	
		
		return "browse/card";
    }

    /*
    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET)
    public String browseGroup(ModelMap model, HttpServletRequest request, @PathVariable Key groupId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	Group group = groups.findGroupByKey(groupId);
	List<Card> groupCards = new ArrayList<Card>();
	if (group != null && group.getOwner().equals(user)) {
	    groupCards = groups.getCardsInGroup(group);
	} else {
	    group = null;
	}
	model.addAttribute("cards", groupCards); //get all cards in group
	model.addAttribute("group", group);

	return "browseGroup";
    }

    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.POST)
    public String updateGroup(@ModelAttribute Group group, ModelMap model, HttpServletRequest request, @PathVariable Key groupId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	Group groupOld = groups.findGroupByKey(groupId);
	List<Card> groupCards = new ArrayList<Card>();
	if (groupOld != null && groupOld.getOwner().equals(user)) {
	    groups.refreshGroup(group);
	    groupCards = groups.getCardsInGroup(group);
	} else {
	    group = null;
	}
	model.addAttribute("cards", groupCards); //get all cards in group
	model.addAttribute("group", group);

	return "browseGroup";
    }
    
    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.DELETE)
    public String deleteGroup(ModelMap model, HttpServletRequest request, @PathVariable Key groupId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	
	Group group = groups.findGroupByKey(groupId);

	if (group != null && group.getOwner().equals(user)) {
	    groups.removeGroup(group); // je u databaze kaskadove vymazavani nebo tady musim projit vsechny karty?
	}

	return "redirect:/browse/";
    }

    
    @RequestMapping(value = "/card/{cardId}", method = RequestMethod.POST)
    public String updateCard(@ModelAttribute Card card, ModelMap model, HttpServletRequest request, @PathVariable Key cardId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	Card cardOld = cards.findCardByKey(cardId);

	if (cardOld != null && (!cardOld.isPrivate() || cardOld.getOwner().equals(user))) {
	    cards.refreshCard(card);
	    model.addAttribute("card", card); // get all card attributes (id, name, owner, addedAt, private, ...
	    model.addAttribute("relatedGroups", cards.getGroupsOfCard(card));//
	} else {
	    model.addAttribute("card", null);
	    model.addAttribute("relatedGroups", new ArrayList<Group>());
	}
	return "browseCard";
    }

    
    @RequestMapping(value = "/card/{cardId}", method = RequestMethod.DELETE)
    public String deleteCard(ModelMap model, HttpServletRequest request, @PathVariable Key cardId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	Card card = cards.findCardByKey(cardId);

	if (card != null && card.getOwner().equals(user)) {
	    cards.removeCard(card);
	}

	return "redirect:/browse/";
    }
    */
}
