package nl.sogyo.myjournal.domain;

import javax.persistence.*;

@Entity
@Table(name = "myjournalday")
public class MyJournalDay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dayID", updatable = false, nullable = false)
	private int dayID;
	
	private String date;
	private String toDoList;
	private String notes;
	
	@ManyToOne
	@JoinColumn(name="userID", nullable=false)
	private User user;
	
	public MyJournalDay() {

	}
	
	public MyJournalDay(String theDate, User theUser) {
		this.date = theDate;
		this.user = theUser;
	}

	public String getDate() {
		return date;
	}

	public String getToDoList() {
		return toDoList;
	}

	public void setToDoList(String toDoList) {
		this.toDoList = toDoList;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public User getUser() {
		return this.user;
	}
}
