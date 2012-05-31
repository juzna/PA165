package cz.juzna.pa165.cards.controller;

import com.google.appengine.api.datastore.Key;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
     * comparatory nebo se na to vykaslete - to bude nejjednodussi "groups" list
     * vizitek citajici "limit" a na "offset" strance, muze byt prazdny zatim
     * neosetruju vyjimky takze nezkousejte dat prilis velky offset a limit
     *
     * @param model
     * @param request
     * @return predani rizeni do /views/browse.ftl
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String browse(ModelMap model, HttpServletRequest request) {

	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));

	Integer offset = Integer.getInteger(request.getParameter("offset")); //offset jako kolikata stranka
	Integer limit = Integer.getInteger(request.getParameter("limit")); //pocet prvku na stranku

	model.addAttribute("user", user);
	//model.addAttribute("cards", …); // get cards to display, consider offset, limit and order
	if (user != null) {
	    model.addAttribute("groups", new ArrayList<Group>(groups.findGroupsByOwner(user)).subList((offset * limit - 1), (offset * limit + limit - 1))); // get all user's groups to display in sidebar
	} else {
	    model.addAttribute("groups", new ArrayList<Group>());
	}

	return "browse";
    }

    /**
     * Skupina vizitek a vsechny obsazene vizitky "cards" - vizitky ve skupine -
     * muze byt prazdna "group" - skupina vizitek, muze byt null - neni majitel
     * nebo skupina neexistuje
     *
     * @param model
     * @param request
     * @param groupId - id skupiny
     * @return predani rizeni do /views/browseGroup.ftl
     */
    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET)
    public String browseGroup(ModelMap model, HttpServletRequest request, @PathVariable Key groupId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));

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

    /**
     * Nejjednodussi uprava skupiny - zadne kontroly
     *
     * @param group - zmenena skupina
     * @param model
     * @param request
     * @param groupId - id zmenene skupiny
     * @return predani rizeni do /views/browseGroup.ftl
     */
    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.POST)
    public String updateGroup(@ModelAttribute Group group, ModelMap model, HttpServletRequest request, @PathVariable Key groupId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));

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

    /**
     * Prikaz na vymazani skupiny predpokladam ze o konzistenci dat se stara
     * databaze
     *
     * @param model
     * @param request
     * @param groupId id skupiny na vymazani
     * @return predani rizeni do /views/browse.ftl
     */
    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.DELETE)
    public String deleteGroup(ModelMap model, HttpServletRequest request, @PathVariable Key groupId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));

	Group group = groups.findGroupByKey(groupId);

	if (group != null && group.getOwner().equals(user)) {
	    groups.removeGroup(group); // je u databaze kaskadove vymazavani nebo tady musim projit vsechny karty?
	}

	return "redirect:/browse/";
    }

    /**
     * "card" - hledana vizitka - muze byt null "relatedGroups" - skupiny ktere
     * obsahuji vizitku (teoreticky k nim nemusi mit dany uzivatel pristup)
     *
     * @param model
     * @param request
     * @param cardId
     * @return predani rizeni do /views/browseCard.ftl
     */
    @RequestMapping(value = "/card/{cardId}", method = RequestMethod.GET)
    public String browseCard(ModelMap model, HttpServletRequest request, @PathVariable Key cardId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));

	Card card = cards.findCardByKey(cardId);

	if (card != null && (!card.isPrivate() || card.getOwner().equals(user))) {
	    model.addAttribute("card", card); // get all card attributes (id, name, owner, addedAt, private, ...
	    model.addAttribute("relatedGroups", cards.getGroupsOfCard(card));//
	} else {
	    model.addAttribute("card", null);
	    model.addAttribute("relatedGroups", new ArrayList<Group>());
	}
	// model.addAttribute("relatedCards", …); // get Related cards, for example siblings in database
	// model.addAttribute("relatedGroups", …); // get user's groups which contains this card

	return "browseCard";
    }

    /**
     * Nejjednodussi editace - zadna kontrola
     * "card" - hledana vizitka - muze byt null "relatedGroups" - skupiny ktere
     * obsahuji vizitku (teoreticky k nim nemusi mit dany uzivatel pristup)
     *
     * @param model
     * @param request
     * @param cardId
     * @return predani rizeni do /views/browseCard.ftl
     */
    @RequestMapping(value = "/card/{cardId}", method = RequestMethod.POST)
    public String updateCard(@ModelAttribute Card card, ModelMap model, HttpServletRequest request, @PathVariable Key cardId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));

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

    /**
     * Smazani karty
     *
     * @param model
     * @param request
     * @param cardId id karty na smazani
     * @return redirect rizeni do /views/browse.ftl
     */
    @RequestMapping(value = "/card/{cardId}", method = RequestMethod.DELETE)
    public String deleteCard(ModelMap model, HttpServletRequest request, @PathVariable Key cardId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));

	Card card = cards.findCardByKey(cardId);

	if (card != null && card.getOwner().equals(user)) {
	    cards.removeCard(card);
	}

	return "redirect:/browse/";
    }
}
