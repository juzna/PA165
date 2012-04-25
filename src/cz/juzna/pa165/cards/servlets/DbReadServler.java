package cz.juzna.pa165.cards.servlets;

import com.google.appengine.api.datastore.KeyFactory;
import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.dao.jpa.JpaCardDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Tag;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DbReadServler")
public class DbReadServler extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cardKey = request.getParameter("cardKey");

		CardDao cardDao = new JpaCardDao();
		Card card = cardDao.findCardByKey(new KeyFactory.Builder("Card", Long.parseLong(cardKey)).getKey());

		PrintWriter writer = response.getWriter();
		writer.printf("Card name: %s, %d groups, %d tags:\n", card.getOwner(), card.getGroupKeys().size(), card.getTags().size());
		for (Tag t : card.getTags()) {
			writer.printf("- %s: %s\n", t.getKey(), t.getContent());
		}
	}
}
