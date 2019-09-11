/**
 * JournalTaskDeletion.java
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

import nl.sogyo.myjournal.persistance.TaskConnector;


@Path("journal/deletetask")
public class JournalTaskDeletion {
	/**
    * @param request
    * @return
    */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response delete(@Context HttpServletRequest request, JournalDayData day) {
    	int taskID = day.getTaskID();
    	TaskConnector.deleteTask(taskID);
		return Response.status(200).entity("").build();
	}
}
