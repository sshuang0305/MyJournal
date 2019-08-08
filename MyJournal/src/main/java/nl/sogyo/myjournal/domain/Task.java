package nl.sogyo.myjournal.domain;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="taskID", updatable = false, nullable = false)
	private int taskID;
	private String taskText;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dayID")
	private Day day;


	public Task() {
		
	}
	
	public Day getDay() {
		return day;
	}


	public void setDay(Day day) {
		this.day = day;
	}

	public String getTaskText() {
		return taskText;
	}

	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
	
	
}
