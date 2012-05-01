package cz.juzna.pa165.cards.domain;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
public class Tag implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key gaeKey;

	private String key;
	private String content;
	private String note;
	private User owner;
	private boolean privacy;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date created;


	public Tag() {
		this.created = new Date();
	}

	public Tag(String key, String content, boolean privacy) {
		this();
		this.key = key;
		this.content = content;
		this.privacy = privacy;
	}

	public Key getGaeKey() {
		return gaeKey;
	}

	public void setGaeKey(Key gaeKey) {
		this.gaeKey = gaeKey;
	}

	public String getName() {
		return key;
	}

	public void setName(String name) {
		this.key = name;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
}
