package nl.sogyo.myjournal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "userstories")
public class UserStory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="storyID", updatable = false, nullable = false)
	private int storyID;
	
	private String storyText;
	
	private String state;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scrumboardID")
	private Scrumboard scrumboard;
	
	public UserStory() {
		
	}
	
	public UserStory(String storyText, BoardState state) {
		this.storyText = storyText;
		this.state = state.toString();
	}

	public String getStoryText() {
		return storyText;
	}

	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public void setScrumboard(Scrumboard scrumboard) {
		this.scrumboard = scrumboard;
	}
	
	public int getStoryID() {
		return storyID;
	}
}
