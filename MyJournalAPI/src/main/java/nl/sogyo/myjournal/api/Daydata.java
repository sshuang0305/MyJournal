package nl.sogyo.myjournal.api;

public class Daydata {

	private String userID;
	private String date;
	private String notes;

	public Daydata() {

	}

	public Daydata(String ID, String day) {
		this.userID = ID;
		this.date = day;
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
}