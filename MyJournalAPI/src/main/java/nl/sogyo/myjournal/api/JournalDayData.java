package nl.sogyo.myjournal.api;

public class JournalDayData {

	private int userID;
	private String date;
	private String notes;
	private int dayRating;
	private String task;
	private int dayID;
	private int taskID;

	public JournalDayData() {

	}

	public int getUserID() {
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

	public int getDayID() {
		return dayID;
	}
	
	public int getTaskID() {
		return taskID;
	}
}
