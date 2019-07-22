package nl.sogyo.myjournal.domain;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "day")
public class Day {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dayID", updatable = false, nullable = false)
	private int dayID;
	
	private String date;
	private ArrayList<String> toDoList;
	private String notes;
	private int dayRating;
	private int userID;
	
	public Day() {
		
	}
	
	public Day(String dateSelected, ArrayList<String> tasks, String newNotes, int rating, int user) {
		this.date = dateSelected;
		this.toDoList = tasks;
		this.notes = newNotes;
		this.dayRating = rating;
		this.userID = user;
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
	
	public int getUserID() {
		return this.userID;
	}
	
	public int getDayID() {
		return this.dayID;
	}
	
	public int getDayRating() {
		return this.dayRating;
	}
	
	public ArrayList<String> getToDoList() {
		return this.toDoList;
	}
}
