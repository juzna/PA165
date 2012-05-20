package cz.juzna.pa165.cards.domain;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import java.io.File;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

@Entity
public class Card implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key gaeKey;
	private File img;
	private User owner;
	private boolean privacy;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date created;

	@SuppressWarnings("JpaAttributeTypeInspection")
	private Set<Key> groupKeys;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Tag> tags;


	public Card() {
		this.tags = new ArrayList<Tag>();
		this.created = new Date();
		this.groupKeys = new HashSet<Key>();
	}

	public Card(File img, User owner, boolean privacy) {
		this();
		this.img = img;
		this.owner = owner;
		this.privacy = privacy;
	}

	public Card(String imgPath, User owner, boolean privacy) {
		this(new File(imgPath), owner, privacy);
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

	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public boolean isPrivacy() {
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
