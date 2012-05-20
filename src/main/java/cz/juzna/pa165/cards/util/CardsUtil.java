package cz.juzna.pa165.cards.util;

import cz.juzna.pa165.cards.domain.Card;
import cz.juzna.pa165.cards.domain.Group;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Riema
 */
public class CardsUtil {
    
    public static List<Card> getPublicCards(List<Card> list){
	List<Card> publicCards = new ArrayList<Card>();
	
	for (Card c: list){
	    if (!c.isPrivacy()){
		publicCards.add(c);
	    }
	}
	
	return publicCards;
    }
    
    public static List<Group> getAllGroupsFromCard(List<Group> list,Card card){
	List<Group> groupCards = new ArrayList<Group>();

	for (Group g: list){
	    if (card.getGroupKeys().contains(g.getGaeKey())){
		groupCards.add(g);
	    }
	}
	
	return groupCards;
    }
    
}
