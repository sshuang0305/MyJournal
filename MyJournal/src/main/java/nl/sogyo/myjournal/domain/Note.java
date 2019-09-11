/**
 * Note.java
 *
 * @author Shan Shan Huang
 * @since 08-07-19
 */


package nl.sogyo.myjournal.domain;

import javax.persistence.*;

@Entity
@Table(name = "notes")
public class Note {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="noteID", updatable = false, nullable = false)
	private int noteID;
	
	@Column(name="noteText")
	private String noteText;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dayID")
	private Day day;

	public Note() {
		
	}
	
	public Note(String noteText, Day theDay) {
		this.noteText = noteText;
		this.day = theDay;
	}

	public String getNoteText() {
		return noteText;
	}

	public void setDay(Day day) {
		this.day = day;
	}
	
	public int getNoteID() {
		return noteID;
	}
}