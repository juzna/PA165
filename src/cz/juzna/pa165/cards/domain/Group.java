package cz.juzna.pa165.cards.domain;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key gaeKey;

	private String name;
	private User owner;
	private Date created;


	public Group() {
		this.setCreated(new Date());
	}

	public Group(String name, User owner) {
		this();
		this.setName(name);
		this.setOwner(owner);
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
}
