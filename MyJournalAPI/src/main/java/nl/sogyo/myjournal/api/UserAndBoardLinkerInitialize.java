package nl.sogyo.myjournal.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.sogyo.myjournal.persistance.UserAndBoardConnector;



@Path("userandboardlinker/save")
public class UserAndBoardLinkerInitialize {
	  /**
	    * @param request
	    * @return
	    */
	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response initialize(
	        @Context HttpServletRequest request, UserAndBoardData userAndBoardData) {
	
			int userID = Integer.parseInt(userAndBoardData.getUserID());
			int scrumboardID = Integer.parseInt(userAndBoardData.getScrumboardID());
			
			UserAndBoardConnector.save(userID, scrumboardID);
			return Response.status(200).entity("").build();
	  }
}