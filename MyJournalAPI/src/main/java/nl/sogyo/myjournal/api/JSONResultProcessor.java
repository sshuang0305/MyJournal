package nl.sogyo.myjournal.api;

import java.util.ArrayList;
import java.util.Arrays;

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
		result.put("toDoList", day.getToDoList());
		result.put("date", day.getDate());
		result.put("notes",day.getNotes());
		result.put("dayrating", day.getDayRating());
		result.put("userID",day.getUserID());
		
		return result.toJSONString();
	}
}


