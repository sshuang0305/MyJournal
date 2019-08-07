package nl.sogyo.myjournal.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.sogyo.myjournal.domain.Day;
import nl.sogyo.myjournal.persistance.DayConnector;


@Path("journal/myday")
public class JournalDayInitialize {
	/**
	* @param request
	* @return
	*/
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response initialize(@Context HttpServletRequest request, JournalDayData day) {
		int userID = day.getUserID();
		String date = day.getDate();
		Day journalDay = new DayConnector(userID, date).getDay();
		String output = new JSONResultProcessor().createDayResponse(journalDay);
		return Response.status(200).entity(output).build();
    }
}
