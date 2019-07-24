package nl.sogyo.myjournal.api;

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
		
		JSONArray jsnTasks = new JSONArray();
		String[] tasks = day.getToDoList().split(";");
		for (String task : tasks) {
			if (!task.equals("")) {
				jsnTasks.add(task);
			}
		}

		JSONObject result = new JSONObject();
		result.put("dayID", day.getDayID());
		result.put("tasks", jsnTasks);
		result.put("date", day.getDate());
		result.put("notes",day.getNotes());
		result.put("dayRating", day.getDayRating());
		result.put("userID",day.getUserID());
		
		return result.toJSONString();
	}
}


