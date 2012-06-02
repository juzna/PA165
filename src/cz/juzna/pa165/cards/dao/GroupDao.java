package cz.juzna.pa165.cards.dao;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;

import java.util.List;

public interface GroupDao {

	public Group addGroup(Group group);

	public void removeGroup(Group group);

	public Group changeGroupName(Group group, String newName);

	public List<Group> getAllGroups();

	public Group findGroupByKey(Key key);

	public List<Group> findGroupsByOwner(User owner);

	public Group addCardToGroup(Group group, Card card);

	public Group removeCardFomGroup(Group group, Card card);

	public List<Card> getCardsInGroup(Group group);
		
	public Group refreshGroup(Group group);
}
