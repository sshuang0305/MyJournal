package nl.sogyo.myjournal.api;

public class Userdata{

	private String username;
	private String password;

	public Userdata() {

	}

	public Userdata(String name, String pw) {
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
