package nl.sogyo.myjournal.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nl.sogyo.myjournal.domain.MyJournalDay;
import nl.sogyo.myjournal.domain.User;
import nl.sogyo.myjournal.persistance.MyDayJournalConnector;


@Path("myday")
public class DayJournalInitialize {

	 /**
	    * @param request
	    * @return
	    */
	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response initialize(
	        @Context HttpServletRequest request, DayData dayData) {

	    	String date = dayData.getDayDate();
			User user = dayData.getUser();

			MyJournalDay journalDay = MyDayJournalConnector.connect(date, user);

//			String output = "hoi";
			String output = new JSONResultProcessor().createJournalDayResponse(journalDay);
			return Response.status(200).entity(output).build();
	  }
}
