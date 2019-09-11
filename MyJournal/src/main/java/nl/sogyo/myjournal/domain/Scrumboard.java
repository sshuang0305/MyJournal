package nl.sogyo.myjournal.domain;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "scrumboard")
public class Scrumboard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="scrumboardID", updatable = false, nullable = false)
	private int scrumboardID;
	@Column(unique=true)
	private String projectName;

	@OneToMany(mappedBy="scrumboard",  cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<UserStory> userStories = new HashSet<UserStory>();
	
	@ManyToMany(mappedBy = "scrumboards", fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<User>();
	
	public Scrumboard() {

	}
	
	public Scrumboard(String name, Set<String> userStories) {
		this.projectName = name;
		for (String text : userStories) {
			UserStory userStory = new UserStory(text, BoardState.BACKLOG);
			this.addUserStory(userStory);
		}
	}
	
	public int getScrumboardID() {
		return scrumboardID;
	}
	
	public String getProjectName() {
		return this.projectName;
	}

	public Set<UserStory> getUserStories() {
		return this.userStories;
	}
	
	public void setBacklog(Set<UserStory> userStories) {
		this.userStories = userStories;
	}
	
	public void addUserStory(UserStory userStory) {
		this.userStories.add(userStory);
		userStory.setScrumboard(this);
	}
	
	public Set<User> getUsers() {
		return this.users;
	}
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
