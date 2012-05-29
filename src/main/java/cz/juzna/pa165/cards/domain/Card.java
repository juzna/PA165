package cz.juzna.pa165.cards.domain;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import java.io.Serializable;
import java.util.*;
import javax.jdo.annotations.*;

@PersistenceCapable(detachable="true")
public class Card implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key gaeKey;
	
	@Persistent
	private String imgPath;
	
	@Persistent
	private User owner;
	
	@Persistent
	private boolean privacy;
	
	@Persistent
	private Date created;
	
	@Persistent
	private Set<Key> groupKeys;
	
	@Persistent
	@Element(dependent = "true")
	private List<Tag> tags;
	

	public Card() {
		this.tags = new ArrayList<Tag>();
		this.created = new Date();
		this.groupKeys = new HashSet<Key>();
	}

	public Card(String imgPath, User owner, boolean privacy) {
		this();
		this.imgPath = imgPath;
		this.owner = owner;
		this.privacy = privacy;
	}

	public Key getGaeKey() {
		return gaeKey;
	}

	public void setGaeKey(Key key) {
		this.gaeKey = key;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public boolean isPrivate() {
		return privacy;
	}

	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Set<Key> getGroupKeys() {
		return groupKeys;
	}

	public void setGroupKeys(Set<Key> groups) {
		this.groupKeys = groups;
	}

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Card other = (Card) obj;
	if (this.gaeKey != other.gaeKey && (this.gaeKey == null || !this.gaeKey.equals(other.gaeKey))) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 71 * hash + (this.gaeKey != null ? this.gaeKey.hashCode() : 0);
	return hash;
    }


	
}
