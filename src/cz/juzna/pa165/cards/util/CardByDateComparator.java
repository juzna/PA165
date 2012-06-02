package cz.juzna.pa165.cards.util;

import cz.juzna.pa165.cards.domain.Card;
import java.util.Comparator;

/**
 * Setrizeni vizitek podle casu vytvoreni.
 * @author Riema
 */
public class CardByDateComparator implements Comparator<Card> {
    
    /**
     * Pokud to tridi spatne (vzestupne misto sestupne), prohodte v return o1 za o2
     * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
     */
    @Override
    public int compare(Card o1, Card o2) {
	return o1.getCreated().compareTo(o2.getCreated());
    }
    
}
