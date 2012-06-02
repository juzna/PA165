package cz.juzna.pa165.cards.domain;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import java.io.Serializable;
import java.util.Date;
import javax.jdo.annotations.*;

@PersistenceCapable(detachable="true")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Persistent
    private Card card;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key gaeKey;
	
	@Persistent
	private String tagKey;
	
	@Persistent
	private String content;
	
	@Persistent
	private String note;
	
	@Persistent
	private User owner;
	
	@Persistent
	private boolean privacy;
	
	@Persistent
	private Date created;


	public Tag() {
		this.created = new Date();
	}

	public Tag(String key, String content, User owner, boolean privacy) {
		this();
		this.tagKey = key;
		this.content = content;
		this.owner = owner;
		this.privacy = privacy;
	}
	
	public Tag(Tag tag) {
		this.created = tag.getCreated();
		this.tagKey = tag.getTagKey();
		this.content = tag.getContent();
		this.privacy = tag.isPrivate();
		this.note = tag.getNote();
		this.owner = tag.getOwner();
	}

	public Key getGaeKey() {
		return gaeKey;
	}

	public void setGaeKey(Key gaeKey) {
		this.gaeKey = gaeKey;
	}

	public String getName() {
		return tagKey;
	}

	public void setName(String name) {
		this.tagKey = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTagKey() {
		return tagKey;
	}

	public void setTagKey(String key) {
		this.tagKey = key;
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

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
}
