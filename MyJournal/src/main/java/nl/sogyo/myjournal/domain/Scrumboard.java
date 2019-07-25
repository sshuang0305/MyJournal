package nl.sogyo.myjournal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scrumboard")
public class Scrumboard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="scrumboardID", updatable = false, nullable = false)
	private int scrumboardID;
	@Column(unique=true)
	private String projectName;
	private int userID;
	private String backlog = "";
	private String todo = "";
	private String inprogress = "";
	private String done = "";
	
	public Scrumboard() {

	}
	
	public Scrumboard(String name, int user, String[] userStories) {
		this.projectName = name;
		this.userID = user;
		this.backlog = String.join(";", userStories);
	}
	
	public int getScrumboardID() {
		return scrumboardID;
	}
	
	public String getProjectName() {
		return this.projectName;
	}

	public int getUserID() {
		return this.userID;
	}

	public String getBacklog() {
		return this.backlog;
	}
	
	public void setBacklog(String[] newBacklog) {
		this.backlog = String.join(";", newBacklog);
	}
	
	public String getTodo() {
		return this.todo;
	}
	
	public void setTodo(String[] newTodo) {
		this.todo = String.join(";", newTodo);
	}
	
	public String getInProgress() {
		return this.inprogress;
	}
	
	public void setInProgress(String[] newInProgress) {
		this.inprogress = String.join(";", newInProgress);
	}
	
	public String getDone() {
		return this.done;
	}

	public void setDone(String[] newDone) {
		this.done = String.join(";", newDone);
	}
}
