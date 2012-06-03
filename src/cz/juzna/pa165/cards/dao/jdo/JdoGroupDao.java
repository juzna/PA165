package cz.juzna.pa165.cards.dao.jdo;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;

import cz.juzna.pa165.cards.dao.*;
import cz.juzna.pa165.cards.domain.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.FetchGroup;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

public class JdoGroupDao implements GroupDao {
	PersistenceManager pm;
	CardDao cardDao = new JdoCardDao();

	@Override
	public Group addGroup(Group group) throws IllegalArgumentException {
		if (group == null) {
			throw new IllegalArgumentException("Argument group is null");
		}
		pm = PMF.get().getPersistenceManager();
		try {
			group = pm.makePersistent(group);
		} finally {
			pm.close();
		}

		return group;
	}

	@Override
	public void removeGroup(Group group) throws IllegalArgumentException {
		try {
			group = this.refreshGroup(group);
		} catch (IllegalArgumentException e) {
			// group is null
			throw e;
		} catch (JDOObjectNotFoundException e) {
			// group not in DB (nothing to remove)
			return;
		}
		pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		HashSet<Key> cardKeys = (HashSet<Key>) group.getCardKeys();
		Iterator<Key> iterator = cardKeys.iterator();
		Card card = new Card();

		try {
			tx.begin();
			group = pm.getObjectById(Group.class, group.getGaeKey());
			for (int i = 0; i < cardKeys.size(); i++) {
				card = pm.getObjectById(Card.class, iterator.next());
				card.getGroupKeys().remove(group.getGaeKey());
				pm.makePersistent(card);
			}
			pm.deletePersistent(group);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	@Override
	public Group changeGroupName(Group group, String newName) throws IllegalArgumentException, JDOObjectNotFoundException {
		try {
			group = this.refreshGroup(group);
		} catch (IllegalArgumentException e) {
			// group is null
			throw e;
		} catch (JDOObjectNotFoundException e) {
			// group not in DB
			group = this.addGroup(group);
			return group;
		}
		pm = PMF.get().getPersistenceManager();
		try {
			group = pm.getObjectById(Group.class, group.getGaeKey());
			group.setName(newName);
		} finally {
			pm.close();
		}

		return group;
	}

	@Override
	public List<Group> getAllGroups() {
		pm = PMF.get().getPersistenceManager();
		Extent<Group> extent = null;
		List<Group> groupList = new ArrayList<Group>();
		try {
			extent = pm.getExtent(Group.class);
			for (Group g : extent) {
				groupList.add(g);			
			}

		} finally {
			extent.closeAll();
			pm.close();
		}
		return groupList;
	}

	@Override
	public Group findGroupByKey(Key key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("Parameter key is null");
		}
		pm = PMF.get().getPersistenceManager();
		pm.getFetchPlan().setGroup(FetchGroup.ALL);
		Group group = null;
		try {
			group = pm.getObjectById(Group.class, key);
		} finally {
			pm.close();
		}

		return group;
	}
	
	@Override
	public Group findGroupById(Long id) throws IllegalArgumentException {
		return this.findGroupByKey(KeyFactory.createKey("Group", id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> findGroupsByOwner(User owner)
			throws IllegalArgumentException {
		if (owner == null) {
			throw new IllegalArgumentException("Argument owner is null");
		}

		pm = PMF.get().getPersistenceManager();
		pm.getFetchPlan().setGroup(FetchGroup.ALL);
		List<Group> groupList = new ArrayList<Group>();
		List<Group> result = null;
		Query query = null;
		try {
			query = pm.newQuery(Group.class);
			query.setFilter("owner == ownerParam");
			query.declareImports("import com.google.appengine.api.users.User");
			query.declareParameters("User ownerParam");
			result = (List<Group>) query.execute(owner);
			for (Group c : result) {
				groupList.add(c);
			}
		} finally {
			query.closeAll();
			pm.close();
		}

		return groupList;
	}

	@Override
	// meni i card, ale nevraci ji!
	public Group addCardToGroup(Group group, Card card) throws IllegalArgumentException, JDOObjectNotFoundException {
		/*
		try {
			card = cardDao.refreshCard(card);
			group = this.refreshGroup(group);
		} catch (IllegalArgumentException e) {
			// card or group is null
			throw e;
		} catch (JDOObjectNotFoundException e) {
			// card or group not in DB
			throw e;
		}
		*/

		pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			card = pm.getObjectById(Card.class, card.getKey());
			group = pm.getObjectById(Group.class, group.getGaeKey());
			card.getGroupKeys().add(group.getGaeKey());
			group.getCardKeys().add(card.getKey());
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return group;
	}

	@Override
	// meni i card, ale nevraci ji!
	public Group removeCardFomGroup(Group group, Card card)
			throws IllegalArgumentException, JDOObjectNotFoundException {
		try {
			card = cardDao.refreshCard(card);
			group = this.refreshGroup(group);
		} catch (IllegalArgumentException e) {
			// card or group is null
			throw e;
		} catch (JDOObjectNotFoundException e) {
			// card or group not in DB
			throw e;
		}
		pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			card = pm.getObjectById(Card.class, card.getKey());
			group = pm.getObjectById(Group.class, group.getGaeKey());
			card.getGroupKeys().remove(group.getGaeKey());
			group.getCardKeys().remove(card.getKey());
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return group;
	}

	@Override
	public List<Card> getCardsInGroup(Group group)
			throws IllegalArgumentException, JDOObjectNotFoundException {
		try {
			group = this.refreshGroup(group);
		} catch (IllegalArgumentException e) {
			// group is null
			throw e;
		} catch (JDOObjectNotFoundException e) {
			// group not in DB
			throw e;
		}

		pm = PMF.get().getPersistenceManager();
		ArrayList<Card> cardList = new ArrayList<Card>();
		try {
			for (Key cardKey : group.getCardKeys()) {
				cardList.add(pm.getObjectById(Card.class, cardKey));
			}
		} finally {
			pm.close();
		}
		return cardList;
	}

	@Override
	public Group refreshGroup(Group group) throws IllegalArgumentException,
			JDOObjectNotFoundException {
		if (group == null) {
			throw new IllegalArgumentException("Argument group is null");
		}
		if (group.getGaeKey() == null) {
			group = this.addGroup(group);
		} else {
			pm = PMF.get().getPersistenceManager();
			pm.getFetchPlan().setGroup(FetchGroup.ALL);
			try {
				group = pm.getObjectById(Group.class, group.getGaeKey());
			} catch (JDOObjectNotFoundException e) {
				throw new JDOObjectNotFoundException("Card is already deleted");
			} finally {
				pm.close();
			}
		}
		return group;
	}

}
