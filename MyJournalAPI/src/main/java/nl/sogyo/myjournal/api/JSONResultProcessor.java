/**
 * JSONResultProcessor.java
 *
 * @author Shan Shan Huang
 * @since 08-07-19
 */

package nl.sogyo.myjournal.api;

import java.util.ArrayList;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import nl.sogyo.myjournal.domain.*;

public class JSONResultProcessor {

	public String createUserResponse(User user) {
		JSONObject result = new JSONObject();
        result.put("username", user.getUsername());
	    result.put("password", user.getPassword());
	    result.put("userID", user.getUserID());
		return result.toJSONString();
	}

	public String createDayResponse(Day day) {
	
		JSONObject result = new JSONObject();
		result.put("dayID", day.getDayID());
		result.put("tasks", tasksToJson(day.getToDoList()));
		result.put("date", day.getDate());
		result.put("notes", notesToJson(day.getNotes()));
		result.put("dayRating", day.getDayRating());
		return result.toJSONString();
	}
	
	public JSONArray tasksToJson(Set<Task> tasks) {
		JSONArray jsArray = new JSONArray();
		for (Task task : tasks) {
			JSONObject taskObj = new JSONObject();
			taskObj.put("taskID", task.getTaskID());
			taskObj.put("taskText", task.getTaskText());
			jsArray.add(taskObj);
		};
		return jsArray;
	}
	
	public JSONArray notesToJson(Set<Note> notes) {
		JSONArray jsArray = new JSONArray();
		for (Note note : notes) {
			JSONObject taskObj = new JSONObject();
			taskObj.put("noteID", note.getNoteID());
			taskObj.put("noteText", note.getNoteText());
			jsArray.add(taskObj);
		};
		return jsArray;
	}
	
	public JSONObject createScrumboardResponse(Scrumboard scrumboard) {

		JSONObject result = new JSONObject();
		result.put("scrumboardID", scrumboard.getScrumboardID());
		result.put("projectName", scrumboard.getProjectName());
		result.put("userStories", userStoriesToJson(scrumboard.getUserStories()));
		return result;
	}
	
	public JSONArray userStoriesToJson(Set<UserStory> stories) {
		JSONArray result = new JSONArray();
		JSONArray backlog = new JSONArray();
		JSONArray todo = new JSONArray();
		JSONArray inprogress = new JSONArray();
		JSONArray done = new JSONArray();
		for (UserStory story : stories) {
			JSONObject userStoryObj = new JSONObject();
			userStoryObj.put("storyID", story.getStoryID());
			userStoryObj.put("storyText",story.getStoryText());
			userStoryObj.put("boardState", story.getState());
			if (story.getState().equals(ScrumboardColumn.BACKLOG.toString())) {
				backlog.add(userStoryObj);
			}
			else if (story.getState().equals(ScrumboardColumn.TODO.toString())) {
				todo.add(userStoryObj);
			}
			else if (story.getState().equals(ScrumboardColumn.INPROGRESS.toString())) {
				inprogress.add(userStoryObj);
			}
			else {
				done.add(userStoryObj);
			}
		}
		result.add(backlog);
		result.add(todo);
		result.add(inprogress);
		result.add( done);
		return result;
	}
	
	public String createScrumboardsResponse(ArrayList<Scrumboard> scrumboards) {
		JSONArray JSONscrumboards = new JSONArray();
		for (Scrumboard board : scrumboards) {
			JSONObject scrumboard = createScrumboardResponse(board);
			JSONscrumboards.add(scrumboard);
		}
		return JSONscrumboards.toJSONString();
	}
}

