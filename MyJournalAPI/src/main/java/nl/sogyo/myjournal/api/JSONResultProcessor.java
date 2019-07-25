package nl.sogyo.myjournal.api;

import java.util.ArrayList;

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
		result.put("tasks", fromStringToJsonArray(day.getToDoList()));
		result.put("date", day.getDate());
		result.put("notes",day.getNotes());
		result.put("dayRating", day.getDayRating());
		result.put("userID",day.getUserID());
		
		return result.toJSONString();
	}
	
	public String createScrumboardsResponse(ArrayList<Scrumboard> scrumboards) {
		JSONObject result = new JSONObject();
		JSONArray JSONscrumboards = new JSONArray();
		
		for (Scrumboard board : scrumboards) {
			
			JSONObject scrumboard = new JSONObject();
			scrumboard.put("scrumboardID", board.getScrumboardID());
			scrumboard.put("projectName", board.getProjectName());
			scrumboard.put("userID", board.getUserID());
			scrumboard.put("backlog", fromStringToJsonArray(board.getBacklog()));
			scrumboard.put("todo", fromStringToJsonArray(board.getTodo()));
			scrumboard.put("inprogress", fromStringToJsonArray(board.getInProgress()));
			scrumboard.put("done", fromStringToJsonArray(board.getDone()));
			JSONscrumboards.add(scrumboard);
		}
		
		result.put("scrumboards", JSONscrumboards);
		return result.toJSONString();
	}
	
	public String createScrumboardResponse(Scrumboard scrumboard) {

		JSONObject result = new JSONObject();
		result.put("scrumboardID", scrumboard.getScrumboardID());
		result.put("projectName", scrumboard.getProjectName());
		result.put("userID", scrumboard.getUserID());
		result.put("backlog", fromStringToJsonArray(scrumboard.getBacklog()));
		result.put("todo", fromStringToJsonArray(scrumboard.getTodo()));
		result.put("inprogress", fromStringToJsonArray(scrumboard.getInProgress()));
		result.put("done", fromStringToJsonArray(scrumboard.getDone()));
		return result.toJSONString();
	}
	
	public JSONArray fromStringToJsonArray(String stringToSplit) {
		
		JSONArray jsnArray = new JSONArray();
		String[] stringArray = stringToSplit.split(";");
		for (String string : stringArray) {
			if (!string.equals("")) {
				jsnArray.add(string);
			}
		}
		return jsnArray;
	}
}


