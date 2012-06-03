package cz.juzna.pa165.cards.dao.jdo;

import javax.jdo.PersistenceManager;

/**
 * Persistence manager factory (PMF) as a bean, so that we can get Persistence manager (PM) as a request-scoped bean
 * There may be a nicer way to do it ;)
 */
public final class PMF2 {
	public PersistenceManager get() {
		return PMF.get().getPersistenceManager();
	}
}
