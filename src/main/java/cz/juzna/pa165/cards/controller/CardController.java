package cz.juzna.pa165.cards.controller;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.GroupDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

@Controller
public class CardController {
	@Autowired
	private CardDao cards;

	@Autowired
	private GroupDao groups;

	@RequestMapping(value = "/card/add")
	public String addCard(Model mode) {
		return "AddCard";
	}

	@RequestMapping(value = "/card/view/{cardId}", method = RequestMethod.GET)
	public String viewCard(@PathVariable Key cardId, ModelMap model) {
		model.addAttribute("card", cards.findCardByKey(cardId));
		return "ViewCard";
	}

	/*
	 * Budu muset ještě udělat práci s parametry - kde se bude dělat?
	 * tzn. otázka jestli se string z formuláře bude řešit v jsp nebo až tady
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/card/view/{cardId}", method = RequestMethod.POST)
	public String viewCardUpdate(@PathVariable Key cardId, ModelMap model) {
		Card card = new Card();
		if (model.get("img") != null) {
			card.setImg((File) model.get("img"));
		}

		if (model.get("owner") != null) {
			card.setOwner((User) model.get("owner"));
		}

		if (model.get("privacy") != null) {
			card.setPrivacy((Boolean) model.get("privacy"));
		}

		if (model.get("created") != null) {
			card.setCreated((Date) model.get("created"));
		}

		if (model.get("groupKeys") != null) {
			card.setGroupKeys(new HashSet<Key>((Collection<Key>) model.get("groupKeys")));
		}

		if (model.get("tags") != null) {
			card.setTags(new LinkedList<Tag>((Collection<Tag>) model.get("tags")));
		}
		model.addAttribute("card", cards.addCard(card));
		return "ViewCard";
	}
	/*
	/card/view/{id}
	/card/removed
	*/
}
