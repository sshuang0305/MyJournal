package nl.sogyo.myjournal.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.sogyo.myjournal.domain.User;
import nl.sogyo.myjournal.persistance.UserAndBoardConnector;
import nl.sogyo.myjournal.persistance.UserConnector;

@Path("addnewmember")
public class NewMemberInitialize {
	  /**
	    * @param request
	    * @return
	    */
	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response initialize(
	        @Context HttpServletRequest request, UserAndBoardData userAndBoardData) {
	
	    	String username = userAndBoardData.getUsername();
			int scrumboardID = Integer.parseInt(userAndBoardData.getScrumboardID());
			int userID;
			
			User newMember = UserConnector.connect(username);
			if (newMember == null) {
				return Response.status(404).entity("").build();
			}
			else {
				userID = newMember.getUserID();
			}
			
			UserAndBoardConnector.save(userID, scrumboardID);
			return Response.status(200).entity("").build();
	  }
}
