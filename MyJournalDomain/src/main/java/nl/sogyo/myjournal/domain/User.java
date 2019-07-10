package nl.sogyo.myjournal.domain;

import javax.persistence.*;

@Entity
class User{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id", updatable = false, nullable = false)
	private int user_id;
	private String username;
	private String password;

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
}
