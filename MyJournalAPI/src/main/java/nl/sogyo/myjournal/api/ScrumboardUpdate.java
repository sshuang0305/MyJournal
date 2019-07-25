package nl.sogyo.myjournal.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.sogyo.myjournal.persistance.ScrumboardConnector;


@Path("savechangesproject")
public class ScrumboardUpdate {
		  /**
		    * @param request
		    * @return
		    */
		    @PUT
		    @Consumes(MediaType.APPLICATION_JSON)
		    @Produces(MediaType.APPLICATION_JSON)
		    public Response initialize(
		        @Context HttpServletRequest request, ScrumboardData scrumboardData) {
		    	
		    	String[] backlog = scrumboardData.getBacklog();
		    	String[] todo = scrumboardData.getTodo();
		    	String[] inprogress = scrumboardData.getInprogress();
		    	String[] done = scrumboardData.getDone();
		    	System.out.println(scrumboardData.getScrumboardID());
		    	int scrumboardID = Integer.parseInt(scrumboardData.getScrumboardID());
	
				ScrumboardConnector.update(backlog, todo, inprogress, done, scrumboardID);
				return Response.status(200).entity("").build();
		  }
}
