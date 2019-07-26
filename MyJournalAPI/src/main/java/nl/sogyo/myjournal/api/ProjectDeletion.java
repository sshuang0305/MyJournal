package nl.sogyo.myjournal.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.sogyo.myjournal.domain.Scrumboard;
import nl.sogyo.myjournal.domain.User;
import nl.sogyo.myjournal.domain.UserAndBoardLinker;
import nl.sogyo.myjournal.persistance.ScrumboardConnector;
import nl.sogyo.myjournal.persistance.UserAndBoardConnector;
import nl.sogyo.myjournal.persistance.UserConnector;

@Path("deleteprojectforeveryone")
public class ProjectDeletion {
	  /**
	    * @param request
	    * @return
	    */
	    @DELETE
	    @Consumes(MediaType.APPLICATION_JSON)
	    public Response delete(
	        @Context HttpServletRequest request, UserAndBoardData userAndBoardData) {
	
	    	int userID = Integer.parseInt(userAndBoardData.getUserID());
			int scrumboardID = Integer.parseInt(userAndBoardData.getScrumboardID());
			
			UserAndBoardConnector.delete(userID, scrumboardID);
			
			Scrumboard scrumboard = ScrumboardConnector.delete(userID, scrumboardID);
			if (scrumboard == null) {
				return Response.status(404).entity("").build();
			}
			else {
				UserAndBoardConnector.delete(scrumboardID);
				return Response.status(200).entity("").build();
			}
	  }
}
