/**
 * JournalDayData.java
 *
 * @author Shan Shan Huang
 * @since 08-07-19
 */

package nl.sogyo.myjournal.api;

public class JournalDayData {

	private int userID;
	private String date;
	private String note;
	private int dayRating;
	private String task;
	private int dayID;
	private int taskID;
	private int noteID;

	public JournalDayData() {

	}

	public int getUserID() {
		return this.userID;
	}

	public String getDate() {
		return this.date;
	}
	
	public String getNote() {
		return this.note;
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
	
	public int getNoteID() {
		return noteID;
	}
}
