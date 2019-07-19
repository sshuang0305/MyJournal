package nl.sogyo.myjournal.domain;


import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userID", updatable = false, nullable = false)
	private int userID;
	
	@Column(unique=true)
	private String username;
	private String password;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private Set<MyJournalDay> dayjournal;

	public User() {

	}
	
	public User(String name, String pw) {
		this.username = name;
		this.password = pw;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public int getUserID() {
		return this.userID;
	}
	
	public static boolean legitUser(User loginUser, String inputPassword) {
		if (loginUser == null || !loginUser.password.equals(inputPassword)) {
			return false;
		}
		return true;
	}
	
	public Set<MyJournalDay> getDayJournal() {
		return this.dayjournal;
	}
	
	public void setDayJournal(Set<MyJournalDay> journal) {
		this.dayjournal = journal;
	}
	
}
