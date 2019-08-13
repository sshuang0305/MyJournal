package nl.sogyo.myjournal.api;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.sogyo.myjournal.domain.Scrumboard;
import nl.sogyo.myjournal.persistance.ScrumboardConnector;

@Path("scrumboard/save")
public class ScrumboardInitialize {
	 /**
	* @param request
	* @return
	*/
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response initialize(@Context HttpServletRequest request, ScrumboardData scrumboardData) {
	
		int userID = scrumboardData.getUserID();
		String projectName = scrumboardData.getProjectName();
		Set<String> userStories = scrumboardData.getStories();
		Scrumboard newScrumboard = ScrumboardConnector.save(projectName, userID, userStories);
		
		if (newScrumboard == null) {
			return Response.status(404).entity("").build();
		}
		String output = new JSONResultProcessor().createScrumboardResponse(newScrumboard).toJSONString();
		return Response.status(200).entity(output).build();
	}
}
