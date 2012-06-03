package cz.juzna.pa165.cards.controller;

import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.GroupDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;
import cz.juzna.pa165.cards.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping("/browse")
public class BrowseController extends BaseController {

	public static final int CARDS_PER_PAGE = 24;

	@Autowired
    private CardDao cards;
    @Autowired
    private GroupDao groups;


	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String browse(Model model, HttpServletRequest request) {
		// Paging
		Integer offset, limit;
		if (request.getParameter("page") != null) {
			Integer page = Integer.parseInt(request.getParameter("page"));
			offset = page * CARDS_PER_PAGE;
			limit = CARDS_PER_PAGE;
		} else {
			offset = 0;
			limit = CARDS_PER_PAGE;
		}

		model.addAttribute("cards", getUser() == null ? cards.getCards(offset, limit) : cards.getCards(offset, limit, getUser())); // browse panel
		model.addAttribute("recentPublicCards", cards.getCards(0, 12)); // side panel
		if (getUser() != null) {
		    model.addAttribute("myGroups", new ArrayList<Group>(groups.findGroupsByOwner(getUser()))); // side panel
		}
		
		return "browse/default";
    }
    
    @RequestMapping(value = "/card/{cardId}", method = RequestMethod.GET)
    public String browseCard(@PathVariable Long cardId, Model model) {
		Card card = cards.findCardById(cardId);
	
		if (card != null && (!card.isPrivate() || card.getOwner().equals(getUser()))) {
		    model.addAttribute("card", card);
		    if (getUser() != null) {
		    	model.addAttribute("groupsOfCard", cards.getGroupsOfCard(card, getUser()));
			    model.addAttribute("allUsersGroups", groups.findGroupsByOwner(getUser()));
		    }
		}
		
		// model.addAttribute("relatedCards", …); // get Related cards, for example siblings in database
		// model.addAttribute("relatedGroups", …); // get user's groups which contains this card
	
		return "browse/card";
    }
    
    
    @RequestMapping(value = "/card/{cardId}", method = RequestMethod.POST, params="do=addTag")
    public String browseCardAddTag(@PathVariable Long cardId, Model model, HttpServletRequest request) {
    	
		Card card = cards.findCardById(cardId);
		Tag tag = new Tag(request.getParameter("tagger-key"), request.getParameter("tagger-value"), getUser(), false);
		cards.addTag(card, tag);
		
		return "redirect:/browse/card/" + cardId +"/";
    }
    
    @RequestMapping(value = "/card/{cardId}", method = RequestMethod.POST, params="do=addToGroup")
    public String browseCardAddtoGroup(@PathVariable Long cardId, Model model, HttpServletRequest request) {
    	
    	Card card = cards.findCardById(cardId);
    	
    	Group group = null;
    	if (request.getParameter("grouper-name") != null) { // create new group
    		String groupName = request.getParameter("grouper-name");
    		group = groups.addGroup(new Group(groupName, getUser()));
    	} else if (request.getParameter("grouper-id") != null) { // add to existing
    		Long groupId = Long.parseLong(request.getParameter("groupId"));
    		group = groups.findGroupById(groupId);
		}
    	
    	groups.addCardToGroup(group, card);
    	
    	return "redirect:/browse/card/" + cardId +"/";
    	
    }

    
    /*
    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET)
    public String browseGroup(ModelMap model, HttpServletRequest request, @PathVariable Key groupId) {
	Group group = groups.findGroupByKey(groupId);
	List<Card> groupCards = new ArrayList<Card>();
	if (group != null && group.getOwner().equals(getUser())) {
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
	Group groupOld = groups.findGroupByKey(groupId);
	List<Card> groupCards = new ArrayList<Card>();
	if (groupOld != null && groupOld.getOwner().equals(getUser())) {
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
	Group group = groups.findGroupByKey(groupId);

	if (group != null && group.getOwner().equals(getUser())) {
	    groups.removeGroup(group); // je u databaze kaskadove vymazavani nebo tady musim projit vsechny karty?
	}

	return "redirect:/browse/";
    }
    
    @RequestMapping(value = "/card/{cardId}", method = RequestMethod.POST)
    public String updateCard(@ModelAttribute Card card, ModelMap model, HttpServletRequest request, @PathVariable Key cardId) {
	Card cardOld = cards.findCardByKey(cardId);

	if (cardOld != null && (!cardOld.isPrivate() || cardOld.getOwner().equals(getUser()))) {
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
	Card card = cards.findCardByKey(cardId);

	if (card != null && card.getOwner().equals(getUser())) {
	    cards.removeCard(card);
	}

	return "redirect:/browse/";
    }
    */

}
