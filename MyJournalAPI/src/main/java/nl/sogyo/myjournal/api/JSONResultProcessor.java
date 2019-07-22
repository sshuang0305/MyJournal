package nl.sogyo.myjournal.api;

import org.json.simple.JSONObject;

import nl.sogyo.myjournal.domain.*;

public class JSONResultProcessor {

	public String createUserResponse(User user) {

		JSONObject result = new JSONObject();

        result.put("username", user.getUsername());
        result.put("password", user.getPassword());

		return result.toJSONString();
	}
}


