package cz.juzna.pa165.cards.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.users.User;

import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.GroupDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;
import cz.juzna.pa165.cards.util.BlobHelper;

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
		
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String account(ModelMap model, HttpServletRequest request) {
		if (getUser() == null) {model = null; return "account/forbidden";}
		
		return "account/default";
	}

	@RequestMapping(value = "/cards", method = RequestMethod.GET)
	public String accountCards(ModelMap model, HttpServletRequest request) {
		if (getUser() == null) {model = null; return "account/forbidden";}
		
		model.addAttribute("cards", cards.findCardsByOwner(user));
		
		return "account/cards";
	}


	@RequestMapping(value = "/card/{cardId}", method = RequestMethod.GET)
	public String accountCard(@PathVariable Long cardId, ModelMap model, HttpServletRequest request) {
		if (getUser() == null) {model = null; return "account/forbidden";}
		
		Card card = cards.findCardById(cardId);
		if (card.isPrivate() && !card.getOwner().equals(getUser())) {
			return "account/forbidden";
		}

		model.addAttribute("card", card);
		
		return "account/card";
	}
	
	@RequestMapping(value = "/card/{cardId}", method = RequestMethod.POST, params="do=edit")
	public String accountCardEdit(@PathVariable Long cardId, ModelMap model, HttpServletRequest request) {
		if (getUser() == null) {model = null; return "account/forbidden";}
		
		Card card = cards.findCardById(cardId);
		if (!card.getOwner().equals(getUser())) {
			return "account/forbidden";
		}
		
		if(request.getParameter("form-editor-name") != null) {
			cards.changeCardName(card, request.getParameter("form-editor-name"));
		}
		
		if(request.getParameter("form-editor-privacy") != null) {
			cards.changeCardPrivacy(card, ( request.getParameter("form-editor-privacy").equalsIgnoreCase("private")) ? true : false );
		}
		
		return "redirect:/account/card/" + cardId +"/";
	}
	
	@RequestMapping(value = "/card/{cardId}", method = RequestMethod.POST, params="do=delete")
	public String accountCardDelete(@PathVariable Long cardId, ModelMap model, HttpServletRequest request) {
		if (getUser() == null) {model = null; return "account/forbidden";}
		
		Card card = cards.findCardById(cardId);
		if (!card.getOwner().equals(getUser())) {
			return "account/forbidden";
		}
		
		cards.removeCard(card);
		
		return "redirect:/account/cards/";
	}
	
	
	@RequestMapping(value = "/groups", method = RequestMethod.GET)
	public String accountGroups(ModelMap model, HttpServletRequest request) {
		if (getUser() == null) {model = null; return "account/forbidden";}
		
		model.addAttribute("groups", groups.findGroupsByOwner(getUser()));
		
		return "account/groups";
	}
	
	@RequestMapping(value = "/groups", method = RequestMethod.POST, params="do=edit")
	public String accountGroupsEdit(ModelMap model, HttpServletRequest request) {
		if (getUser() == null) {model = null; return "account/forbidden";}
		
		Group group = groups.findGroupById(Long.valueOf(request.getParameter("form-groups-id")));
		groups.changeGroupName(group, request.getParameter("form-groups-name"));
		
		return "redirect:/account/groups";
	}
	
	@RequestMapping(value = "/groups", method = RequestMethod.POST, params="do=delete")
	public String accountGroupsDelete(ModelMap model, HttpServletRequest request) {
		if (getUser() == null) {model = null; return "account/forbidden";}
		
		Group group = groups.findGroupById(Long.valueOf(request.getParameter("form-groups-id")));
		groups.removeGroup(group);
		
		return "redirect:/account/groups";
	}


	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String accountUpload(ModelMap model, HttpServletRequest request) {
		if (getUser() == null) {model = null; return "account/forbidden";}
		
		return "account/upload";
	}


	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String accountUploadProcess(@RequestParam("form-upload-image") MultipartFile file, MultipartHttpServletRequest request, ModelMap model) throws IOException {
		if (getUser() == null) {model = null; return "account/forbidden";}
		
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
