/**
 * ScrumboardBoardUpdate.java
 *
 * @author Shan Shan Huang
 * @since 08-07-19
 */

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


@Path("scrumboard/savechanges")
public class ScrumboardUpdate {
	/**
    * @param request
    * @return
    */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response initialize(@Context HttpServletRequest request, ScrumboardData scrumboardData) {
    	String[][] userStories = scrumboardData.getUserStories();
    	for (String[] userStory : userStories) {
	    	int storyID = Integer.parseInt(userStory[0]);
	    	String boardState = userStory[1];
			ScrumboardConnector.update(storyID, boardState);
    	}
		return Response.status(200).entity("").build();
  }
}
