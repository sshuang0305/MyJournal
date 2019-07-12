package nl.sogyo.myjournal.api;

import javax.persistence.*;

@Entity
public class Userdata{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id", updatable = false, nullable = false)
	private int user_id;
	private String username;
	private String password;

	public Userdata() {

	}

	public Userdata(String name, String pw) {
		this.username = name;
		this.password = pw;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public void setPassword(String pw) {
		this.password = pw;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}
}
