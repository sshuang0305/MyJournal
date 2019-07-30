package nl.sogyo.myjournal.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.sogyo.myjournal.domain.Day;
import nl.sogyo.myjournal.persistance.DayConnector;

@Path("journal/addtask")
public class JournalTaskAddition {
	/**
	* @param request
	* @return
	*/
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response initialize(@Context HttpServletRequest request, JournalDayData day) {

		int userID = Integer.parseInt(day.getUserID());
		String date = day.getDate();
		String task = day.getTask();
		Day selectedDay = new DayConnector(userID, date).addTask(task);
		String output = new JSONResultProcessor().createDayResponse(selectedDay);
		return Response.status(200).entity(output).build();
    }
}