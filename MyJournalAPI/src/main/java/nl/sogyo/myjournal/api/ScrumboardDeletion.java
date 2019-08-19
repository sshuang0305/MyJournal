package nl.sogyo.myjournal.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.sogyo.myjournal.persistance.ScrumboardConnector;

@Path("scrumboard/delete")
public class ScrumboardDeletion {
	/**
	* @param request
	* @return
	*/
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(@Context HttpServletRequest request, ScrumboardData scrumboardData) {
		int scrumboardID = scrumboardData.getScrumboardID();
		ScrumboardConnector.delete(scrumboardID);
		return Response.status(200).entity("").build();
	}
}
