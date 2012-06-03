package cz.juzna.pa165.cards.domain;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import java.io.Serializable;
import java.util.*;
import javax.jdo.annotations.*;

@PersistenceCapable(detachable="true")
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private BlobKey img;
	
	@Persistent
	private User owner;
	
	@Persistent
	private String name;
	
	@Persistent
	private boolean privacy;
	
	@Persistent
	private Date created;
	
	@Persistent
	private Set<Key> groupKeys;
	
	@Persistent(mappedBy = "card")
	@Element(dependent = "true")
	private List<Tag> tags;
	

	public Card() {
		this.tags = new ArrayList<Tag>();
		this.created = new Date();
		this.groupKeys = new HashSet<Key>();
	}

	public Card(User owner, BlobKey img, String name, boolean privacy) {
		this();
		this.img = img;
		this.owner = owner;
		this.privacy = privacy;
		this.name = name;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public BlobKey getImg() {
		return img;
	}

	public void setImg(BlobKey img) {
		this.img = img;
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
	
	public boolean getPrivacy() {
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
	if (this.key != other.key && (this.key == null || !this.key.equals(other.key))) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 5;
	hash = 71 * hash + (this.key != null ? this.key.hashCode() : 0);
	return hash;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	
}
