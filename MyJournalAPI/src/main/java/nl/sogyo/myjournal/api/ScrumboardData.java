package nl.sogyo.myjournal.api;

public class ScrumboardData {

	private String userID;
	private String projectName;
	private String[] stories;
	private String[] backlog;
	private String[] todo;
	private String[] inprogress;
	private String[] done;
	private String scrumboardID;

	public ScrumboardData() {

	}

	public String getUserID() {
		return this.userID;
	}

	public String getProjectName() {
		return this.projectName;
	}
	
	public String[] getStories() {
		return this.stories;
	}

	public String[] getBacklog() {
		return backlog;
	}

	public String[] getTodo() {
		return todo;
	}

	public String[] getInprogress() {
		return inprogress;
	}

	public String[] getDone() {
		return done;
	}

	public String getScrumboardID() {
		return scrumboardID;
	}
}
