package nl.sogyo.myjournal.domain;


import javax.persistence.*;



@Entity
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id", updatable = false, nullable = false)
	private int user_id;
	private String username;
	private String password;
	
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
	
	public static boolean legitUser(User loginUser, String inputPassword) {
		if (loginUser == null || !loginUser.password.equals(inputPassword)) {
			return false;
		}
		return true;
	}
	
}