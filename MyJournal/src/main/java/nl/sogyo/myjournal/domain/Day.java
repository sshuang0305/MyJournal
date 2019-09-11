/**
 * Day.java
 *
 * @author Shan Shan Huang
 * @since 08-07-19
 */

package nl.sogyo.myjournal.domain;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "day")
public class Day {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dayID", updatable = false, nullable = false)
	private int dayID;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userID")
	private User user;
	
	@OneToMany(mappedBy="day", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Task> toDoList = new HashSet<Task>();
	
	@OneToMany(mappedBy="day", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Note> notes = new HashSet<Note>();
	
	private int dayRating;
	private String date;

	public Day() {

	}
	
	public Day(String dateSelected, User theUser) {
		this.date = dateSelected;
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
		return this.toDoList;
	}

	public void setToDoList(Set<Task> toDoList) {
		this.toDoList = toDoList;
	}
	
	public void addTask(Task newTask) {
		this.toDoList.add(newTask);
		newTask.setDay(this);
	}

	public Set<Note> getNotes() {
		return this.notes;
	}
	
	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}
	
	public void addNote(Note newNote) {
		this.notes.add(newNote);
		newNote.setDay(this);
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
