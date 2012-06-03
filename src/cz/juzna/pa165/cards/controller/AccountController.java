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
		if (getUser() == null) {
			//TODO: Forbid access
		}
	}

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String account(Model model, HttpServletRequest request) {
		return "account/default";
	}

	@RequestMapping(value = "/cards", method = RequestMethod.GET)
	public String accountCards(Model model, HttpServletRequest request) {
		model.addAttribute("cards", cards.findCardsByOwner(user));
		return "account/cards";
	}


	@RequestMapping(value = "/card/{cardId}", method = RequestMethod.GET)
	public String accountCard(@PathVariable Long cardId, Model model, HttpServletRequest request) {
		
		Card card = cards.findCardById(cardId);
		if (card.isPrivate() && !card.getOwner().equals(getUser())) {
			return "account/forbidden";
		}

		model.addAttribute("card", card);
		return "account/card";
	}
	
	@RequestMapping(value = "/card/{cardId}", method = RequestMethod.POST, params="do=edit")
	public String accountCardEdit(@PathVariable Long cardId, Model model, HttpServletRequest request) {
		
		Card card = cards.findCardById(cardId);
		if (card.isPrivate() && !card.getOwner().equals(getUser())) {
			return "account/forbidden";
		}
		
		card.setName(request.getParameter("form-editor-name"));
		if(request.getParameter("form-editor-privacy") != null) {
			card.setPrivacy( (request.getParameter("form-editor-privacy").equalsIgnoreCase("private")) ? true : false );
		}
		System.out.print("Byl sem tady");
		cards.changeCard(card);
		
		return "redirect:/account/card/" + cardId +"/";
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
