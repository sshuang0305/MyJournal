package nl.sogyo.myjournal.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "day")
public class Day {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dayID", updatable = false, nullable = false)
	private int dayID;
	
	private String date;
	private String notes;
	private int dayRating;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userID")
	private User user;
	
	@OneToMany(mappedBy="day",  cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Task> toDoList = new HashSet<Task>();

	public Day() {

	}
	
	public Day(String dateSelected, User theUser) {
		this.date = dateSelected;
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
	
	public Set<Task> getToDoList() {
		return toDoList;
	}

	public void setToDoList(Set<Task> toDoList) {
		this.toDoList = toDoList;
	}
	
	public void addTask(Task newTask) {
		this.toDoList.add(newTask);
		newTask.setDay(this);
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
	
}
