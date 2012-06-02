package cz.juzna.pa165.cards.controller;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.GroupDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;
import java.io.IOException;
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
@RequestMapping("account/*")
public class AccountController {

    @Autowired
    private CardDao cards;
    @Autowired
    private GroupDao groups;

    /**
     * Pripravuje informace na stranku o uzivateli "user" - uzivatel
     *
     * @param model
     * @param request
     * @return predani rizeni do /views/account.ftl
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String account(ModelMap model, HttpServletRequest request) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
	
		model.addAttribute("user", user);
		model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));
	
		return "account/default";
    }

    /**
     * Pripravuje vsechny karty daneho uzivatele, seznamy mohou byt prazdne
     * "publicCard" - seznam verejnych karet "userCards" - seznam uzivatelovych
     * karet
     *
     * @param model
     * @param request
     * @return predani rizeni do /views/accountManage.ftl
     */
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String accountManage(ModelMap model, HttpServletRequest request) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));

	if (user != null) {
	    model.addAttribute("userCards", cards.findCardsByOwner(user)); // all user's cards
	} else {
	    model.addAttribute("userCards", new ArrayList<Card>());
	}

	model.addAttribute("publicCards", cards.getPublicCards());

	return "accountManage";
    }

    /**
     * Pripravuje vizitku na zobrazeni "card" - null nebo vizitka (null -
     * nenalezena nebo nema p≈ô√≠stup)
     *
     * @param model
     * @param request
     * @param cardId - id zobrazovane vizitky
     * @return predani rizeni do /views/accountCard.ftl
     */
    @RequestMapping(value = "/card/{cardId}", method = RequestMethod.GET)
    public String accountCard(ModelMap model, HttpServletRequest request, @PathVariable Key cardId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));

	Card card = cards.findCardByKey(cardId);
	if (card != null) {
	    if (card.isPrivate() && !card.getOwner().equals(user)) {
		card = null;
	    }
	}

	model.addAttribute("card", card); // card info

	return "accountCard";
    }

    /**
     * Pripravuje vsechny skupiny daneho uzivatele "groups" - skupiny uzivatele,
     * prazdna pro neprihlaseneho
     *
     * @param model
     * @param request
     * @return predani rizeni do /views/accountGroups.ftl
     */
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String accountGroups(ModelMap model, HttpServletRequest request) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));

	List<Group> userGroups = new ArrayList<Group>();

	if (user != null) {
	    userGroups = groups.findGroupsByOwner(user);
	}

	model.addAttribute("groups", userGroups); // all users groups

	return "accountGroups";
    }

    /**
     * Pripravuje novou cistou kartu na nahrani tezko vam tam muzu dat ralated
     * vizitky kdyz nevim co bude nahrano :)
     *
     * @param model
     * @param request
     * @return predani rizeni do /views/accountUpload.ftl
     */
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String accountUpload(ModelMap model, HttpServletRequest request) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
	
		model.addAttribute("user", user);
		model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));
	
		Card card = new Card();
		model.addAttribute("card", card); // cista karta pro prazdny formular(get all card attributes (id, name, owner, addedAt, private, ...)
		// model.addAttribute("relatedCards", ‚Ä¶); // get Related cards, for example siblings in database
		// model.addAttribute("relatedGroups", ‚Ä¶); // get user's groups which contains this card
	
		return "account/upload";
    }

    /**
     * Zpracovani karty po vyplneni formulare zatim to nejjednodussi - zadna
     * kontrola
     *
     * @param card
     * @param model
     * @param request
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String accountUploadProcess(Model model, HttpServletRequest request) throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
	
		model.addAttribute("user", user);
		model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));
	
		model.addAttribute("name", request.getParameter("form-upload-name"));
		model.addAttribute("privacy", request.getParameter("form-upload-privacy"));
		
		return "account/upload-completed";
		// return "redirect:/account/manager/" + card.getGaeKey();// redirect to /account/manager/{cardId}
    }
}
