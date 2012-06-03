package cz.juzna.pa165.cards.controller;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.GroupDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;
import cz.juzna.pa165.cards.util.BlobHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("account/*")
@Scope("request") /* per request, i.e. user can be injected properly ;) */
public class AccountController extends BaseController {

    @Autowired
    private CardDao cards;
    @Autowired
    private GroupDao groups;

	@Autowired
	private User user;


	public AccountController() {
		// Debug
		System.out.println("Creating account controller");
	}


	/**
     * Pripravuje informace na stranku o uzivateli "user" - uzivatel
     *
     * @param model
     * @param request
     * @return predani rizeni do /views/account.ftl
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String account(ModelMap model, HttpServletRequest request) {
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
		if (user != null) {
		    model.addAttribute("userCards", cards.findCardsByOwner(user)); // all user's cards
		} else {
		    model.addAttribute("userCards", new ArrayList<Card>());
		}
	
		model.addAttribute("publicCards", cards.getCards(0, 999, getUser()));
	
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
	List<Group> userGroups = new ArrayList<Group>();

	if (user != null) {
	    userGroups = groups.findGroupsByOwner(user);
	}

	model.addAttribute("groups", userGroups); // all users groups

	return "accountGroups";
    }


	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String accountUpload(ModelMap model, HttpServletRequest request) {
		return "account/upload";
	}



    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String accountUploadProcess(@RequestParam("form-upload-image") MultipartFile file, MultipartHttpServletRequest request, Model model) throws IOException {
		model.addAttribute("name", request.getParameter("form-upload-name"));
		model.addAttribute("privacy", request.getParameter("form-upload-privacy"));
		model.addAttribute("file", file.getSize());
		
		// Save image to blob
		BlobKey blobKey = BlobHelper.addImage(file.getBytes());
		
		Card card = new Card(getUser(), blobKey, request.getParameter("form-upload-name"), ((request.getParameter("form-upload-privacy").equalsIgnoreCase("private")) ? true : false));
		cards.addCard(card);
		
		return "redirect:/browse/card/" + card.getKey().getId() + "/";
    }
}
