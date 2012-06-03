package cz.juzna.pa165.cards.domain;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.*;
import javax.persistence.*;

@PersistenceCapable(detachable="true")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key gaeKey;
	
	@Persistent
	private String name;
	
	@Persistent
	private User owner;
	
	@Persistent
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date created;
	
	@Persistent
	private Set<Key> cardKeys;

	public Group() {
		this.created = new Date();
		this.cardKeys = new HashSet<Key>();
	}

	public Group(String name, User owner) {
		this();
		this.name = name;
		this.owner = owner;
	}

	public Key getGaeKey() {
		return gaeKey;
	}

	public void setGaeKey(Key key) {
		this.gaeKey = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

    public Set<Key> getCardKeys() {
		return cardKeys;
	}

	public void setCardKeys(Set<Key> groupKeys) {
		this.cardKeys = groupKeys;
	}

	@Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Group other = (Group) obj;
	if (this.gaeKey != other.gaeKey && (this.gaeKey == null || !this.gaeKey.equals(other.gaeKey))) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 97 * hash + (this.gaeKey != null ? this.gaeKey.hashCode() : 0);
	return hash;
    }
	
}
