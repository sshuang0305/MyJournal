package nl.sogyo.myjournal.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.sogyo.myjournal.domain.Scrumboard;
import nl.sogyo.myjournal.domain.User;
import nl.sogyo.myjournal.persistance.ScrumboardConnector;
import nl.sogyo.myjournal.persistance.UserConnector;

@Path("scrumboard/addmember")
public class ScrumboardMemberAddition {
	/**
	* @param request
	* @return
	*/
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response initialize(@Context HttpServletRequest request, ScrumboardData scrumboardData) {

		String username = scrumboardData.getUsername();
		int scrumboardID = scrumboardData.getScrumboardID();
		User newMember = new UserConnector().findUser(username);
		if (newMember == null) {
			return Response.status(404).entity("").build();
		}
		else {
			ScrumboardConnector.addMemberToScrumboard(scrumboardID, newMember.getUserID());
			return Response.status(200).entity("").build();
		}
    }
}
