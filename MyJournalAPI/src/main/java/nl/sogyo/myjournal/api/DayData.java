package nl.sogyo.myjournal.api;

public class DayData {

	private String userID;
	private String date;
	private String notes;
	private int dayRating;
	private String task;

	public DayData() {

	}



	public String getUserID() {
		return this.userID;
	}

	public String getDate() {
		return this.date;
	}
	
	public String getNotes() {
		return this.notes;
	}
	
	public int getDayRating() {
		return this.dayRating;
	}
	
	public String getTask() {
		return this.task;
	}
}