package cz.riema.pa165.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.GroupDao;

@Controller
public class CardsController {
	@Autowired
	private CardDao cards;
	
	@Autowired
	private GroupDao groups;
	
	/*
 	/cards/view/
	/cards/view/group/{id}
	/cards/view/user/
	 */

}
