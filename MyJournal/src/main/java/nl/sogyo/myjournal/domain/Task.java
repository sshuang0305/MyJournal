/**
 * Task.java
 *
 * @author Shan Shan Huang
 * @since 08-07-19
 */


package nl.sogyo.myjournal.domain;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="taskID", updatable = false, nullable = false)
	private int taskID;
	
	@Column(name="taskText")
	private String taskText;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dayID")
	private Day day;

	public Task() {
		
	}
	
	public Task(String taskText, Day theDay) {
		this.taskText = taskText;
		this.day = theDay;
	}

	public String getTaskText() {
		return taskText;
	}

	public void setDay(Day day) {
		this.day = day;
	}
	
	public int getTaskID() {
		return taskID;
	}
}
