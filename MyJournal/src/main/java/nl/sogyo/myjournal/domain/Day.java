package nl.sogyo.myjournal.domain;

import javax.persistence.*;

@Entity
@Table(name = "day")
public class Day {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dayID", updatable = false, nullable = false)
	private int dayID;
	
	private String date;
	private String toDoList;
	private String notes;
	private int dayRating;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userID")
	private User user;

	public Day() {

	}
	
	public Day(String dateSelected, User theUser) {
		this.date = dateSelected;
		this.toDoList = "";
		this.notes = "";
		this.dayRating = 50;
		this.user = theUser;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDate() {
		return this.date;
	}
	
	public String getNotes() {
		return this.notes;
	}
	
	public void setNotes(String newNotes) {
		this.notes = newNotes;
	}
	
	public int getDayID() {
		return this.dayID;
	}
	
	public int getDayRating() {
		return this.dayRating;
	}
	
	public void setDayRating(int rating) {
		this.dayRating = rating;
	}
	
	public String getToDoList() {
		return this.toDoList;
	}
	
	public void addNewTask(String newTask) {
		this.toDoList += newTask + ";";
	}
	
	public void deleteTask(String Task) {
		this.toDoList = this.toDoList.replace(Task + ";", "");
	}
}
