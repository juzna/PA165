package cz.juzna.pa165.cards.dao.jpa;

import com.google.appengine.api.users.User;
import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.domain.Card;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;

public class JpaCardDaoTest {
	private CardDao cardDao;
	private EntityManager em;

	@Before
	public void setUp() throws Exception {
		cardDao = new JpaCardDao();

		// Get inner entity manager ;)
		Field emr = JpaCardDao.class.getDeclaredField("em");
		emr.setAccessible(true);
		em = (EntityManager) emr.get(cardDao);
	}

	@After
	public void tearDown() throws Exception {

	}


	@Test
	public void testAddCard() throws Exception {
		// create card
		User user = new User("agata@hanychova.cz", "super.cz");
		Card card = new Card("juzna.png", user, false);

		// save it
		cardDao.addCard(card);

		// check if it's there
		assert card == em.find(Card.class, card.getGaeKey());
	}

	@Test
	public void testRemoveCard() throws Exception {

	}

	@Test
	public void testUpdateCard() throws Exception {

	}

	@Test
	public void testGetAllCards() throws Exception {

	}

	@Test
	public void testFindCardByKey() throws Exception {

	}

	@Test
	public void testFindCardsByOwner() throws Exception {

	}

	@Test
	public void testAddTag() throws Exception {

	}

	@Test
	public void testRemoveTag() throws Exception {

	}

	@Test
	public void testGetAllTags() throws Exception {

	}
}
