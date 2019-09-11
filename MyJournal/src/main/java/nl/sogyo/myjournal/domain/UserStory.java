/**
 * UserStory.java
 *
 * @author Shan Shan Huang
 * @since 08-07-19
 */


package nl.sogyo.myjournal.domain;

import javax.persistence.*;

@Entity
@Table(name = "userstories")
public class UserStory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="storyID", updatable = false, nullable = false)
	private int storyID;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scrumboardID")
	private Scrumboard scrumboard;
	
	private String storyText;
	private String state;
	
	public UserStory() {
		
	}
	
	public UserStory(String storyText, ScrumboardColumn state) {
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
