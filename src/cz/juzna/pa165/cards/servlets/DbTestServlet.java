package cz.juzna.pa165.cards.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.google.appengine.api.users.UserServiceFactory;
import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.jpa.JpaCardDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Tag;


/**
 * Makes some test on the data store
 */
@WebServlet(name = "DbTestServlet")
public class DbTestServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// create card
		Card card = new Card("juzna.png", UserServiceFactory.getUserService().getCurrentUser(), false);

		// add tags
		List<Tag> tags = card.getTags();
		tags.add(new Tag("name", "Jan juzna Dolecek", false));
		tags.add(new Tag("phone", "774 178 544", false));
		tags.add(new Tag("email", "juzna@gmail.com", false));

		// store it
		CardDao cardDao = new JpaCardDao();
		cardDao.addCard(card);
	}
}
