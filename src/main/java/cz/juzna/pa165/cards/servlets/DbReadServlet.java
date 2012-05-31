/*package cz.juzna.pa165.cards.servlets;

import com.google.appengine.api.datastore.KeyFactory;
import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.jpa.JpaCardDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Tag;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * For testing only ;)
 * /
@WebServlet(name = "DbReadServlet")
public class DbReadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		String cardKey = request.getParameter("cardKey");
		writer.printf("%s", cardKey);

		CardDao cardDao = new JpaCardDao();
		Card card = cardDao.findCardByKey(new KeyFactory.Builder("Card", Long.parseLong(cardKey)).getKey());

		if (card == null) {
			writer.println("Card not found");
			return;
		}

		writer.printf("Card name: %s, %d groups, %d tags:\n", card.getOwner(), card.getGroupKeys().size(), card.getTags().size());
		for (Tag t : card.getTags()) {
			writer.printf("- %s: %s\n", t.getKey(), t.getContent());
		}
	}
}*/
