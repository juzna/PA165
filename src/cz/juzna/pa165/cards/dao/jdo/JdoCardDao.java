package cz.juzna.pa165.cards.dao.jdo;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import cz.juzna.pa165.cards.dao.CardDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;
import cz.juzna.pa165.cards.domain.Tag;

import javax.jdo.*;
import java.util.*;

public class JdoCardDao implements CardDao {
	/* final */ PersistenceManager pm = PMF.get().getPersistenceManager(); // should be final I think but there's lots of Jenyk's code which changes it

	@Override
	public Card addCard(Card card) throws IllegalArgumentException {
		if (card == null) {
			throw new IllegalArgumentException("Argument card is null");
		}
		try {
			card = pm.makePersistent(card);
		} finally {
			// pm.close();
		}
		return card;
	}

	@Override
	public void removeCard(Card card) {
		try {
			//card = this.refreshCard(card);
		} catch (IllegalArgumentException e) {
			// card is null
			throw e;
		} catch (JDOObjectNotFoundException e) {
			// card not in DB (nothing to remove)
			return;
		}

		//pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		HashSet<Key> groupKeys = (HashSet<Key>) card.getGroupKeys();
		Iterator<Key> iterator = groupKeys.iterator();
		Group group = new Group();
		try {
			tx.begin();
//			card = pm.getObjectById(Card.class, card.getGaeKey()); //TODO: Wierd
//			for (int i = 0; i < groupKeys.size(); i++) {
//				group = pm.getObjectById(Group.class, iterator.next());
//				group.getCardKeys().remove(card.getGaeKey());
//				pm.makePersistent(group);
//			}
			pm.deletePersistent(card);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			// pm.close();
		}
	}
	
	@Override
	public void changeCard(Card card) throws IllegalArgumentException, JDOObjectNotFoundException {
		pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(card);
		} finally {
			pm.close();
		}
	}

	@Override
	public List<Card> getAllCards() {
//		pm = PMF.get().getPersistenceManager();
		
		try {
			Query query = pm.newQuery(Card.class);
			List<Card> results = (List<Card>) query.execute();
			return results;
		} finally {
			//pm.close();
		}
		
	}

	@Override
	public Card findCardByKey(Key key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("Parameter key is null");
		}
		
		pm = PMF.get().getPersistenceManager();
		return pm.detachCopy(pm.getObjectById(Card.class, key));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Card> findCardsByOwner(User owner)
			throws IllegalArgumentException {
		if (owner == null) {
			throw new IllegalArgumentException("Argument owner is null");
		}
		pm = PMF.get().getPersistenceManager();
		pm.getFetchPlan().setGroup(FetchGroup.ALL);
		List<Card> cardList = new ArrayList<Card>();
		List<Card> result = null;
		Query query = null;
		try {
			query = pm.newQuery(Card.class);
			query.setFilter("owner == ownerParam");
			query.declareImports("import com.google.appengine.api.users.User");
			query.declareParameters("User ownerParam");
			result = (List<Card>) query.execute(owner);
			for (Card c : result) {
				cardList.add(c);
			}
		} finally {
			query.closeAll();
			pm.close();
		}

		return cardList;
	}

	@Override
	public Card addTag(Card card, Tag tag) throws IllegalArgumentException, JDOObjectNotFoundException {
		// neresi pridavani tagu k cizim private kartam
		try {
			card = this.refreshCard(card);
		} catch (IllegalArgumentException e) {
			// card is null
			throw e;
		} catch (JDOObjectNotFoundException e) {
			// card not in DB
			throw e;
		}
		if (tag == null) {
			throw new IllegalArgumentException("Argument tag is null");
		}

		/* Tags can be duplicate
		Map<String, Tag> tagMap = this.getCardTags(card);
		if (tagMap.containsKey(tag.getTagKey())) {
			throw new IllegalArgumentException("Duplicite tag key "
					+ tag.getTagKey());
		}
		*/

		pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		// get a copy
		tag = new Tag(tag);
		try {
			tx.begin();
			card = pm.getObjectById(Card.class, card.getKey());
			card.getTags().add(tag);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();

		}

		return card;
	}

	@Override
	public Card removeTag(Card card, String tagKey)
			throws IllegalArgumentException, JDOObjectNotFoundException {
		try {
			card = this.refreshCard(card);
		} catch (IllegalArgumentException e) {
			// card is null
			throw e;
		} catch (JDOObjectNotFoundException e) {
			// card not in DB
			throw e;
		}

		Map<String, Tag> tagMap = this.getCardTags(card);
		if (!tagMap.containsKey(tagKey)) {
			return card;
		}
		Tag tag = tagMap.get(tagKey);
		pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			tag = pm.getObjectById(Tag.class, tag.getGaeKey());
			card = pm.getObjectById(Card.class, card.getKey());
			card.getTags().remove(tag);
			pm.deletePersistent(tag);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return card;
	}

	@Override
	public Map<String, Tag> getCardTags(Card card)
			throws IllegalArgumentException, JDOObjectNotFoundException {
		try {
			card = this.refreshCard(card);
		} catch (IllegalArgumentException e) {
			// card is null
			throw e;
		} catch (JDOObjectNotFoundException e) {
			// card not in DB
			throw e;
		}

		HashMap<String, Tag> map = new HashMap<String, Tag>();
		List<Tag> cardList = card.getTags();
		String key = null;

		for (Tag tag : cardList) {
			key = tag.getTagKey();
			if (!map.containsKey(key)) {
				map.put(key, tag);
			} else {
				throw new JDODataStoreException("Duplicit tag key " + key
						+ " in DB!");
			}
		}

		return map;
	}

	@Override
	public Map<String, Tag> getUserCardTags(Card card, User user)
			throws IllegalArgumentException, JDOObjectNotFoundException {
		try {
			card = this.refreshCard(card);
		} catch (IllegalArgumentException e) {
			// card is null
			throw e;
		} catch (JDOObjectNotFoundException e) {
			// card not in DB
			throw e;
		}

		if ((!card.getOwner().getEmail().equals(user.getEmail()))
				&& card.isPrivate()) {
			throw new IllegalArgumentException("Different user's private card");
		}

		HashMap<String, Tag> map = new HashMap<String, Tag>();
		List<Tag> cardList = card.getTags();
		String key = null;

		for (Tag tag : cardList) {
			key = tag.getTagKey();
			if ((!map.containsKey(key))
					&& tag.getOwner().getEmail().equals(user.getEmail())) {
				map.put(key, tag);
			} else if (map.containsKey(key)) {
				throw new JDODataStoreException("Duplicit tag key " + key
						+ " in DB!");
			}
		}

		return map;
	}

	@Override
	public Map<String, Tag> getCardPublicTags(Card card)
			throws IllegalArgumentException, JDOObjectNotFoundException {
		try {
			card = this.refreshCard(card);
		} catch (IllegalArgumentException e) {
			// card is null
			throw e;
		} catch (JDOObjectNotFoundException e) {
			// card not in DB
			throw e;
		}

		HashMap<String, Tag> map = new HashMap<String, Tag>();
		List<Tag> cardList = card.getTags();
		String key = null;

		for (Tag tag : cardList) {
			key = tag.getTagKey();
			if (!map.containsKey(key) && !tag.isPrivate()) {
				map.put(key, tag);
			} else if (map.containsKey(key)) {
				throw new JDODataStoreException("Duplicit tag key " + key
						+ " in DB!");
			}
		}

		return map;
	}

	@Override
	public List<Group> getGroupsOfCard(Card card, User user) throws IllegalArgumentException, JDOObjectNotFoundException {
		try {
			card = this.refreshCard(card);
		} catch (IllegalArgumentException e) {
			// card is null
			throw e;
		} catch (JDOObjectNotFoundException e) {
			// card not in DB
			throw e;
		}
		
		pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		ArrayList<Group> groupList = new ArrayList<Group>();
		try {
			tx.begin();
			Set<Key> groupKeys = card.getGroupKeys();
			for (Key cardKey : groupKeys) {
				groupList.add(pm.getObjectById(Group.class, cardKey));
			}
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return groupList;
	}

	@SuppressWarnings("unchecked")
	protected List<Card> _getCards(Integer offset, Integer limit, User owner) {
		// TODO: process offset, limit
		pm = PMF.get().getPersistenceManager();
		ArrayList<Card> cardList = new ArrayList<Card>();
		List<Card> result = null;
		Query query = null;
		try {
			query = pm.newQuery(Card.class);

			if (owner == null) {
				query.setFilter("privacy == false");
				query.setRange(offset, offset + limit);
				result = (List<Card>) query.execute();

			} else {
				// Load mine TODO: should load mine + public, but appengine can't do that
				query.setFilter("owner == ownerParam");
				query.declareImports("import com.google.appengine.api.users.User");
				query.declareParameters("User ownerParam");
				query.setRange(offset, offset + limit);
				result = (List<Card>) query.execute(owner);
			}


		} finally {
			query.closeAll();
			pm.close();
		}
		return result;
	}

	@Override
	public List<Card> getCards(Integer offset, Integer limit) {
		return _getCards(offset,limit, null);
	}

	@Override
	public List<Card> getCards(Integer offset, Integer limit, User owner) {
		if (owner == null) throw new IllegalArgumentException("owner");
		return _getCards(offset,limit, owner);
	}

	@Override
	public Card refreshCard(Card card) throws IllegalArgumentException,
			JDOObjectNotFoundException {
		if (card == null) {
			throw new IllegalArgumentException("Argument card is null");
		}
		if (card.getKey() == null) {
			card = this.addCard(card);
		} else {
			pm = PMF.get().getPersistenceManager();
			pm.getFetchPlan().setGroup(FetchGroup.ALL);
			try {
				card = pm.getObjectById(Card.class, card.getKey());
			} catch (JDOObjectNotFoundException e) {
				throw new JDOObjectNotFoundException(card.getKey()
						+ " is not in DB");
			} finally {
				pm.close();
			}
		}
		return card;
	}

	@Override
	public Card findCardById(Long cardId) {
		return this.findCardByKey(KeyFactory.createKey("Card", cardId));
	}

}
