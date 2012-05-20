package cz.juzna.pa165.cards.domain;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key gaeKey;

	private String name;
	private User owner;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date created;


	public Group() {
		this.created = new Date();
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
