package cz.juzna.pa165.cards.dao.jpa;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import cz.juzna.pa165.cards.dao.GroupDao;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


public class JpaGroupDao implements GroupDao {
	EntityManager em = EMF.get().createEntityManager();

	@Override
	public Group addGroup(Group group) {
		if (group.getGaeKey() == null) {
			em.persist(group);
			em.refresh(group);
			return group;
		}
		em.merge(group);
		return group;
	}

	@Override
	public void removeGroup(Group group) {
		em.getTransaction().begin();
		em.remove(group);
		em.getTransaction().commit();
	}

	@Override
	public Group updateGroup(Group group) {
		if (group == null) {
			em.persist(group);
		} else {
			em.merge(group);
		}
		return group;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getAllGroups() {
		Query query = em.createQuery("SELECT g FROM Group g");
		return (List<Group>) query.getResultList();
	}

	@Override
	public Group findGroupByKey(Key key) {
		return em.find(Group.class, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> findGroupsByOwner(User owner) {
		Query query = em.createQuery("SELECT g FROM Group g WHERE g.owner = ?1").setParameter(1, owner);
		return (List<Group>) query.getResultList();
	}

	@Override
	public Group addCardToGroup(Group group, Card card) {
		if (card.getGaeKey() == null) {
			em.persist(card);
		}
		if (group.getGaeKey() == null) {
			em.persist(group);
		}
		card.getGroupKeys().add(group.getGaeKey());
		em.merge(group);
		return group;
	}

	@Override
	public Group removeCardFomGroup(Group group, Card card) {
		if (card.getGaeKey() == null) {
			em.persist(card);
		}
		if (group.getGaeKey() == null) {
			em.persist(group);
		}
		card.getGroupKeys().remove(group.getGaeKey());
		em.merge(group);
		em.merge(card);
		return group;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Card> getCardsInGroup(Group group) {
		Query q = em.createQuery("SELECT c FROM Card c WHERE c.groupKeys CONTAINS ?1").setParameter(1, group.getGaeKey());
		return (List<Card>) q.getResultList();
	}

}
