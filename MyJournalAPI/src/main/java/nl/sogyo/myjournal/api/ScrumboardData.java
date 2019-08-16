package nl.sogyo.myjournal.api;

import java.util.*;

public class ScrumboardData {

	private int userID;
	private String projectName;
	private String[] stories;

	private int scrumboardID;
	private String[][] userStories;
	private String username;

	public ScrumboardData() {

	}

	public String getProjectName() {
		return this.projectName;
	}
	
	public Set<String> getStories() {
		Set<String> stories = new HashSet<String>(Arrays.asList(this.stories));
		return stories;
	}

	public int getScrumboardID() {
		return scrumboardID;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public String[][] getUserStories() {
		return userStories;
	}
	
	public String getUsername() {
		return username;
	}
}
