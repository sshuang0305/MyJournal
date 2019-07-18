package nl.sogyo.myjournal.domain;

import javax.persistence.*;

@Entity
@Table(name = "myjournalday")
public class MyJournalDay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="day_id", updatable = false, nullable = false)
	private int dayID;
	
	private String date;
	private String[] toDoList;
	private String notes;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	public MyJournalDay() {

	}
	
	public MyJournalDay(String theDate, int userID) {
		this.date = theDate;
	}

	public String getDate() {
		return date;
	}

	public String[] getToDoList() {
		return toDoList;
	}

	public void setToDoList(String[] toDoList) {
		this.toDoList = toDoList;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

//	public int getUserID() {
//		return userID;
//	}
}
