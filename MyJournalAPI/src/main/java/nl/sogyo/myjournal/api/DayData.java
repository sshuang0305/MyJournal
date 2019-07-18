package nl.sogyo.myjournal.api;

import nl.sogyo.myjournal.domain.User;

public class DayData {

	private String date;
	private User user;
	
	public DayData () {

	}

	public DayData (String day, User u) {
		this.date = day;
		this.user = u;
	}

	public String getDayDate() {
		return date;
	}

	public User getUser() {
		return this.user;
	}
}
