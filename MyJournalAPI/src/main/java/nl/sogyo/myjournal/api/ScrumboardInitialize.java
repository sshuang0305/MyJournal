package nl.sogyo.myjournal.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;

import nl.sogyo.myjournal.domain.Scrumboard;
import nl.sogyo.myjournal.persistance.ScrumboardConnector;

@Path("savescrumboard")
public class ScrumboardInitialize {
	  /**
	    * @param request
	    * @return
	    */
	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response initialize(
	        @Context HttpServletRequest request, ScrumboardData scrumboardData) {
	
			int userID = Integer.parseInt(scrumboardData.getUserID());
			String projectName = scrumboardData.getProjectName();
			String userStories = scrumboardData.getStories();
			String[] userStoriesList = new JSONResultProcessor().createListFromString(userStories);

			Scrumboard newScrumboard = ScrumboardConnector.save(projectName, userID, userStoriesList);
			String output = new JSONResultProcessor().createScrumboardResponse(newScrumboard);
			
			return Response.status(200).entity(output).build();
	  }

}
