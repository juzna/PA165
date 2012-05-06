package cz.riema.pa165.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.GroupDao;

@Controller
public class GroupController {
	@Autowired
	private CardDao cards;
	
	@Autowired
	private GroupDao groups;
	
	/*
	 /group/view/ 
/group/view/{id}
/group/removed/ 
/group/removed/{id} 
/group/add/ 
/group/add/{id}
	 */

}
