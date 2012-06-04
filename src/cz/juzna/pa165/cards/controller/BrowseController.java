package cz.juzna.pa165.cards.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.GroupDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;
import cz.juzna.pa165.cards.domain.Tag;

@Controller
@RequestMapping("/browse")
public class BrowseController extends BaseController {

	public static final int CARDS_PER_PAGE = 24;

	@Autowired
    private CardDao cards;
    @Autowired
    private GroupDao groups;


	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String browse(ModelMap model, HttpServletRequest request) {
		
		// Pagination
		Integer offset, limit;
		if (request.getParameter("page") != null) {
			offset = ( Integer.parseInt(request.getParameter("page")) - 1 ) * CARDS_PER_PAGE;
			limit = CARDS_PER_PAGE;
			model.addAttribute("page", Integer.parseInt(request.getParameter("page")));
		} else {
			offset = 0;
			limit = CARDS_PER_PAGE;
			model.addAttribute("page", 1);
			
		}
		
		if (request.getParameter("group") != null) {
			Group group = groups.findGroupById(Long.valueOf(request.getParameter("group")));
			if (group != null && group.getOwner().equals(getUser())) {
				model.addAttribute("cards", groups.getCardsInGroup(group));
				model.addAttribute("activeGroup", group);
			}
		} else {
			model.addAttribute("cards", getUser() == null ? cards.getCards(offset, limit) : cards.getCards(offset, limit, getUser()));
		}
				
		model.addAttribute("recentPublicCards", cards.getCards(0, 12)); // side panel
		if (getUser() != null) {model.addAttribute("groups", new ArrayList<Group>(groups.findGroupsByOwner(getUser())));}
		
		return "browse/default";
	}
	
	@RequestMapping(value = "/card/{cardId}", method = RequestMethod.GET)
	public String browseCard(@PathVariable Long cardId, ModelMap model) {
		Card card = cards.findCardById(cardId);
	
		if (card != null && (!card.isPrivate() || card.getOwner().equals(getUser()))) {
			model.addAttribute("card", card);
			if (getUser() != null) {
				model.addAttribute("groupsOfCard", cards.getGroupsOfCard(card, getUser()));
				model.addAttribute("allUsersGroups", groups.findGroupsByOwner(getUser()));
			}
		}
		
		// model.addAttribute("relatedCards", …); // get Related cards, for example siblings in database
	
		return "browse/card";
	}
	

	@RequestMapping(value = "/card/{cardId}", method = RequestMethod.POST, params="do=addTag")
	public String browseCardAddTag(@PathVariable Long cardId, ModelMap model, HttpServletRequest request) {
		
		Card card = cards.findCardById(cardId);
		Tag tag = new Tag(request.getParameter("tagger-key"), request.getParameter("tagger-value"), getUser(), false);
		cards.addTag(card, tag);
		
		return "redirect:/browse/card/" + cardId +"/";
    }
    
	@RequestMapping(value = "/card/{cardId}", method = RequestMethod.POST, params="do=addToGroup")
	public String browseCardAddtoGroup(@PathVariable Long cardId, ModelMap model, HttpServletRequest request) {
		
		Card card = cards.findCardById(cardId);
		
		Group group = null;
		if (request.getParameter("grouper-name") != null) { // add to new group
			String groupName = request.getParameter("grouper-name");
			group = groups.addGroup(new Group(groupName, getUser()));
		} else if (request.getParameter("grouper-id") != null) { // add to existing group
			Long groupId = Long.valueOf(request.getParameter("grouper-id"));
			group = groups.findGroupById(groupId);
		}
		
		groups.addCardToGroup(group, card);
		
		return "redirect:/browse/card/" + cardId +"/";
		
	}
	
	@RequestMapping(value = "/card/{cardId}", method = RequestMethod.POST, params="do=removeFromGroup")
	public String browseCardRemoveFromGroup(@PathVariable Long cardId, ModelMap model, HttpServletRequest request) {
		
		Card card = cards.findCardById(cardId);
		
		Group group = null;
		if (request.getParameter("groupId") != null) {
			Long groupId = Long.valueOf(request.getParameter("groupId"));
			group = groups.findGroupById(groupId);
		}
		
		groups.removeCardFomGroup(group, card);
		
		return "redirect:/browse/card/" + cardId +"/";
		
	}

}
