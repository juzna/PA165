package cz.juzna.pa165.cards.controller;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.GroupDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;
import cz.juzna.pa165.cards.util.CardsUtil;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/browse/*")
public class BrowseController {
    
    	@Autowired
	private CardDao cards;

	@Autowired
	private GroupDao groups;

	/**
	 * Zadne order nikde nemam, napiste seznam at si na to muzu udelat 
	 * comparatory nebo se na to vykaslete - to bude nejjednodussi
	 * @param model
	 * @param request
	 * @return 
	 */
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String browse(ModelMap model, HttpServletRequest request) {

	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();

	    model.addAttribute("user", user);
	    model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));
	    
	    Integer offset = Integer.getInteger(request.getParameter("offset"));
	    Integer limit = Integer.getInteger(request.getParameter("limit"));
	    
	    model.addAttribute("user", user);
	    //model.addAttribute("cards", …); // get cards to display, consider offset, limit and order
	    if (user != null){
		model.addAttribute("groups", new ArrayList<Group>(groups.findGroupsByOwner(user))); // get all user's groups to display in sidebar
	    }
	    else {
		model.addAttribute("groups", new ArrayList<Group>());
	    }		

		return "browse";
	}
	
	/**
	 * Skupina vizitek a vsechny jeji clenove
	 * jak je to s vlastnostmi a viditelnosti karet v skupinach? 
	 * Jsou skupiny verejne nebo jak to je navrzene?
	 * @param model
	 * @param request
	 * @param groupId
	 * @return 
	 */
    @RequestMapping(value="/group/{groupId}", method = RequestMethod.GET)
    public String browseGroup(ModelMap model, HttpServletRequest request,  @PathVariable Key groupId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));
	
	Group group = groups.findGroupByKey(groupId);
	List<Card> groupCards = new ArrayList<Card>();
	if (group != null && group.getOwner().equals(user)){
	    groupCards = groups.getCardsInGroup(group);
	}
        model.addAttribute("cards", groupCards); //get all cards in group
        model.addAttribute("groups", group);

        return "browseGroup";
    }

       @RequestMapping(value="/group/{groupId}", method = RequestMethod.DELETE)
    public String deleteGroup(ModelMap model, HttpServletRequest request, @PathVariable Key groupId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));
	
	Group group = groups.findGroupByKey(groupId);

	if (group != null && group.getOwner().equals(user)){
	    groups.removeGroup(group); // je u databaze kaskadove vymazavani nebo tady musim projit vsechny karty?
	}

        return "redirect:/browse/"; //
    }
    
       /**
	* 
	* @param model
	* @param request
	* @param cardId
	* @return 
	*/
    @RequestMapping(value="/card/{cardId}", method = RequestMethod.GET)
    public String browseCard(ModelMap model, HttpServletRequest request, @PathVariable Key cardId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));

	Card card = cards.findCardByKey(cardId);
	
	if (card != null && (!card.isPrivacy() || card.getOwner().equals(user))){
	    model.addAttribute("card", card); // get all card attributes (id, name, owner, addedAt, private, ...
	    model.addAttribute("relatedGroups", CardsUtil.getAllGroupsFromCard(groups.getAllGroups(), card));
	} else {
	    model.addAttribute("card", null);
	    model.addAttribute("relatedGroups", new ArrayList<Group>());
	}
	// model.addAttribute("relatedCards", …); // get Related cards, for example siblings in database
        // model.addAttribute("relatedGroups", …); // get user's groups which contains this card

        return "browseCard";
    }

}
