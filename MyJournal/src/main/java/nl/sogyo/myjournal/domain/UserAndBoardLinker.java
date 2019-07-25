package nl.sogyo.myjournal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userandboardlinker")
public class UserAndBoardLinker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="linkerID", updatable = false, nullable = false)
	private int linkerID;
	private int userID;
	private int scrumboardID;
	
	public UserAndBoardLinker() {
		
	}
	
	public UserAndBoardLinker(int user, int scrumboard) {
		this.userID = user;
		this.scrumboardID = scrumboard;
	}
	
	public int getUserID() {
		return this.userID;
	}
	
	public int getScrumboardID() {
		return this.scrumboardID;
	}
	
	public int linkerID() {
		return this.linkerID;
	}
}
