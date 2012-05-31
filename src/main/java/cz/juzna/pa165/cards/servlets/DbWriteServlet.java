/*package cz.juzna.pa165.cards.servlets;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.GroupDao;
import cz.juzna.pa165.cards.dao.jpa.JpaCardDao;
import cz.juzna.pa165.cards.dao.jpa.JpaGroupDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;
import cz.juzna.pa165.cards.domain.Tag;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * For testing only!
 * Makes some test on the data store
 * /
@WebServlet(name = "DbWriteServlet")
public class DbWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();

		// Dummy user
		User juzna = new User("juzna@gmail.com", "gmail.com");
		User strajk = new User("strajk@me.com", "apple.com");


		// Groups
		Group g1 = new Group("kamosi", juzna);
		Group g2 = new Group("spoluzaci", juzna);
		Group g3 = new Group("grafici", strajk);


		// Store groups
		GroupDao groupDao = new JpaGroupDao();
		groupDao.addGroup(g1);
		groupDao.addGroup(g2);
		groupDao.addGroup(g3);
		writer.println("groups stored");


		// Create card
		Card card = new Card("juzna.png", UserServiceFactory.getUserService().getCurrentUser(), false);

		// add tags
		List<Tag> tags = card.getTags();
		tags.add(new Tag("name", "Jan juzna Dolecek", false));
		tags.add(new Tag("phone", "774 178 544", false));
		tags.add(new Tag("email", "juzna@gmail.com", false));


		// Put it into two groups
		card.getGroupKeys().add(g1.getGaeKey());
		card.getGroupKeys().add(g2.getGaeKey());


		// store it
		writer.println("card storing");
		CardDao cardDao = new JpaCardDao();
		cardDao.addCard(card);
		writer.println("OK");


		writer.println("Group keys: " + g1.getGaeKey() + ", " + g2.getGaeKey() + ", " + g3.getGaeKey());
		writer.println("Card key: " + card.getGaeKey());
	}
}*/
