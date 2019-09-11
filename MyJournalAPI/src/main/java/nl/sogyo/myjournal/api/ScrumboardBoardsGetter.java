/**
 * ScrumboardBoardsGetter.java
 *
 * @author Shan Shan Huang
 * @since 08-07-19
 */

package nl.sogyo.myjournal.api;

import java.util.ArrayList;

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

@Path("scrumboard/myscrumboards")
public class ScrumboardBoardsGetter {
	/**
	* @param request
	* @return
	*/
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response initialize(@Context HttpServletRequest request, ScrumboardData scrumboardData) {
		int userID = scrumboardData.getUserID();
		ArrayList<Scrumboard> scrumboards = ScrumboardConnector.getBoards(userID);
		String output = new JSONResultProcessor().createScrumboardsResponse(scrumboards);
		return Response.status(200).entity(output).build();
	 }
}
