package cz.juzna.pa165.cards.dao;

import java.util.List;
import java.util.Map;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;
import cz.juzna.pa165.cards.domain.Tag;


public interface CardDao {

	public Card addCard(Card card);

	public void removeCard(Card card);

	public Card updateCard(Card card);

	public List<Card> getAllCards();

	public Card findCardByKey(Key key);

	public List<Card> findCardsByOwner(User owner);

//  bug in design: useless method?
//	public List<Group> getAllGroupsOfCard(Card card);

	public Card addTag(Card card, Tag tag);

	public Card removeTag(Card card, Tag tag);

//  bug in design: useless method? (zmenit tag a zavolat updateCard())
//	public Card updateTag(Card card, Tag tag);

	public Map<String, Tag> getAllTags(Card card);
}
