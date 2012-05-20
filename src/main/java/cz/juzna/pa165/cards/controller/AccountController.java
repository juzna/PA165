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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/account/*")
public class AccountController {

    @Autowired
    private CardDao cards;
    @Autowired
    private GroupDao groups;
    
    /**
     * Pripravuje informace o uzivateli
     * "user" - uzivatel
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String account(ModelMap model, HttpServletRequest request) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));

	return "account";
    }
    
    /**
     * Pripravuje vsechny karty daneho uzivatele
     * "publicCard" - mnozina verejnych karet
     * "userCards" - mnozina uzivatelovych karet
     * - pokud chcete prunik tak si mnoziny soupnete do jedne
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String accountManage(ModelMap model, HttpServletRequest request) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));
	
	if (user != null){
	    Set<Card> userCards = new HashSet<Card>(cards.findCardsByOwner(user));
	    model.addAttribute("userCards", userCards); // all user cards
	}
	Set<Card> publicCards = new HashSet<Card>(CardsUtil.getPublicCards(cards.getAllCards()));	
	
	model.addAttribute("publicCards", publicCards);

	return "accountManage";
    }

    /**
     * Pripravuje vizitku
     * "card" - null nebo vizitka (null - nenalezena nebo nemá přístup)
     * @param model
     * @param request
     * @param cardId
     * @return 
     */
    @RequestMapping(value = "/card/{cardId}", method = RequestMethod.GET)
    public String accountCard(ModelMap model, HttpServletRequest request, @PathVariable Key cardId) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));
	
	Card card = cards.findCardByKey(cardId);
	if (card != null){
	    if (card.isPrivacy() && !card.getOwner().equals(user)){
		card = null;
	    }
	}
	
	model.addAttribute("card", card); // card info

	return "accountCard";
    }
    
    /**
     * Pripravuje vsechny skupiny daneho uzivatele
     * "groups" - prazdna pro neprihlaseneho uzivatele
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String accountGroups(ModelMap model, HttpServletRequest request) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));
	
	List<Group> userGroups = new ArrayList<Group>();
	
	if (user != null){
	    userGroups = groups.findGroupsByOwner(user);
	}
	
	model.addAttribute("groups", userGroups); // all users groups

	return "accountGroups";
    }

    
    /**
     * Pripravuje novou cistou kartu na nahrani
     * tezko vam tam muzu dat ralated vizitky kdyz nevim co bude nahrano
     * @param model
     * @param request
     * @return 
     */
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String accountUpload(ModelMap model, HttpServletRequest request) {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));
	
	Card card = new Card();
	model.addAttribute("card", card); // cista karta pro prazdny formular(get all card attributes (id, name, owner, addedAt, private, ...)
	// model.addAttribute("relatedCards", …); // get Related cards, for example siblings in database
	// model.addAttribute("relatedGroups", …); // get user's groups which contains this card

	return "accountUpload";
    }
    
    /**
     * Zpracovani karty po vyplneni formulare
     * javax.validation.Valid nemuzu najit takze zatim preskakuji
     * @param model
     * @param request
     * @param form
     * @param file
     * @throws IOException 
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void accountUploadProcess(Model model, HttpServletRequest request, @Valid FormBean form, @RequestParam MultipartFile file) throws IOException {
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();

	model.addAttribute("user", user);
	model.addAttribute("loginUrl", userService.createLoginURL(request.getRequestURI()));
	// Save card information
	// Save card image
	// model.addAttribute("flashes", …); // Success/fail
	// return "redirect:/..."; // redirect to /account/manager/{cardId}
    }
}
