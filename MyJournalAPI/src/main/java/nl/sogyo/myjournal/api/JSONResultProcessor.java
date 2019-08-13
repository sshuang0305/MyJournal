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
	
	public JSONObject userStoriesToJson(Set<UserStory> stories) {
		JSONObject result = new JSONObject();
		JSONArray backlog = new JSONArray();
		JSONArray todo = new JSONArray();
		JSONArray inprogress = new JSONArray();
		JSONArray done = new JSONArray();
		for (UserStory story : stories) {
			if (story.getState().equals(BoardState.BACKLOG.toString())) {
				backlog.add(story.getStoryText());
			}
			else if (story.getState().equals(BoardState.TODO.toString())) {
				todo.add(story.getStoryText());
			}
			else if (story.getState().equals(BoardState.INPROGRESS.toString())) {
				inprogress.add(story.getStoryText());
			}
			else {
				done.add(story.getStoryText());
			}
		}
		result.put("backlog", backlog);
		result.put("todo", todo);
		result.put("inprogress", inprogress);
		result.put("done", done);
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

