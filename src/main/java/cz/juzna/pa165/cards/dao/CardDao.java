package cz.juzna.pa165.cards.dao;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;
import cz.juzna.pa165.cards.domain.Tag;

import java.util.List;
import java.util.Map;


public interface CardDao {

	public Card addCard(Card card);

	public void removeCard(Card card);

	public Card changeCardImgPath(Card card, String newImgPath);
	
	public Card changeCardPrivacy(Card card);

	public List<Card> getAllCards();

	public Card findCardByKey(Key key);

	public List<Card> findCardsByOwner(User owner);

	public Card addTag(Card card, Tag tag);

	public Card removeTag(Card card, String tagKey);

	public Map<String, Tag> getCardTags(Card card);
	
	public Map<String, Tag> getCardPublicTags(Card card);
	
	public Map<String, Tag> getUserCardTags(Card card, User owner);
	
	public List<Group> getGroupsOfCard(Card card);
	
	public List<Card> getPublicCards();
	
	public Card refreshCard(Card card);

}
