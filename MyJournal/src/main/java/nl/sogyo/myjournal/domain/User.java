/**
 * User.java
 *
 * @author Shan Shan Huang
 * @since 08-07-19
 */

package nl.sogyo.myjournal.domain;

import java.util.*;

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
	
	@OneToMany(mappedBy="user",  cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Day> days = new HashSet<Day>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "user_scrumboard", joinColumns = @JoinColumn(name = "userID"), inverseJoinColumns = @JoinColumn(name = "scrumboardID"))
	private Set<Scrumboard> scrumboards = new HashSet<Scrumboard>();

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
	
	public Set<Day> getDays() {
		return days;
	}

	public void setDays(Set<Day> days) {
		this.days = days;
	}
	
	public void addDay(Day day) {
		this.days.add(day);
		day.setUser(this);
	}
	
	public static boolean validateUser(User user, String inputPassword) {
		if (user == null || !user.password.equals(inputPassword)) {
			return false;
		}
		return true;
	}

	public Set<Scrumboard> getScrumboards() {
		return this.scrumboards;
	}
	
	public void setScrumboards(Set<Scrumboard> scrumboards) {
		this.scrumboards = scrumboards;
	}
	
	public void addScrumboard(Scrumboard scrumboard) {
		this.scrumboards.add(scrumboard);
		scrumboard.getUsers().add(this);
	}
	
	public void removeScrumboard(Scrumboard scrumboard) {
		this.scrumboards.remove(scrumboard);
		scrumboard.getUsers().remove(this);
	}
}
