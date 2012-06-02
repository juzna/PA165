package cz.juzna.pa165.cards.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.GroupDao;
import cz.juzna.pa165.cards.dao.jdo.JdoCardDao;
import cz.juzna.pa165.cards.dao.jdo.JdoGroupDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;
import cz.juzna.pa165.cards.domain.Tag;

@Controller
public class SystemController {
    
    @RequestMapping(value = "/removeAllData")
	public void removeAllData(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	PrintWriter writer = response.getWriter();
    	
    	CardDao cardDao = new JdoCardDao();
    	List<Card> cards = cardDao.getAllCards();
    	writer.printf("I've got %d cards to delete\n", cards.size());
    	
    	for (Card card : cards) {
    		writer.printf("Deleting card %s\n", card.getGaeKey());
    		cardDao.removeCard(card);
    	}
    }
    
    @RequestMapping(value = "/addSampleData")
	public void addSampleData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();

		// Dummy user and smart user
		User juzna = new User("juzna@gmail.com", "gmail.com");
		User strajk = new User("strajk@me.com", "apple.com");


		// Groups
		Group g1 = new Group("kamosi", juzna);
		Group g2 = new Group("spoluzaci", juzna);
		Group g3 = new Group("grafici", strajk);


		// Store groups
		GroupDao groupDao = new JdoGroupDao();
		groupDao.addGroup(g1);
		groupDao.addGroup(g2);
		groupDao.addGroup(g3);
		writer.println("groups stored");


		// Create card
		Card card = new Card(strajk, "juzna.png", "Namecko", false);

		// add tags
		List<Tag> tags = card.getTags();
		tags.add(new Tag("name", "Jan juzna Dolecek", juzna, false));
		tags.add(new Tag("phone", "774 178 544", juzna, false));
		tags.add(new Tag("email", "juzna@gmail.com", strajk, false));


		// Put it into two groups
		card.getGroupKeys().add(g1.getGaeKey());
		card.getGroupKeys().add(g2.getGaeKey());

		// store it
		writer.println("card storing");
		CardDao cardDao = new JdoCardDao();
		cardDao.addCard(card);
		writer.println("OK");


		writer.println("Group keys: " + g1.getGaeKey() + ", " + g2.getGaeKey() + ", " + g3.getGaeKey());
		writer.println("Card key: " + card.getGaeKey());
	}
    
    
    /**
     * Zadej GET parametr cardKey
     */
    @RequestMapping("/readSampleData")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		String cardKey = request.getParameter("cardKey");
		writer.printf("Loading card with id %s\n", cardKey);

		CardDao cardDao = new JdoCardDao();
		Card card = cardDao.findCardByKey(new KeyFactory.Builder("Card", Long.parseLong(cardKey)).getKey());

		if (card == null) {
			writer.println("Card not found\n");
			return;
		}

		writer.printf("Card name: %s, %d groups, %d tags:\n", card.getOwner(), card.getGroupKeys().size(), card.getTags().size());
		for (Tag t : card.getTags()) {
			writer.printf("- %s: %s\n", t.getTagKey(), t.getContent());
		}
	}
}
