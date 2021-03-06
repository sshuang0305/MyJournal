/**
 * UserRegister.java
 *
 * @author Shan Shan Huang
 * @since 08-07-19
 */

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
import nl.sogyo.myjournal.persistance.UserConnector;


@Path("users/register")
public class UserRegistration {

    /**
    * @param request
    * @return
    */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@Context HttpServletRequest request, UserData user) {

		String username = user.getUsername();
		String password = user.getPassword();
		User registerUser = new UserConnector().register(username, password);
		
		if (registerUser == null) {
			return Response.status(404).entity("").build();
		}
		String output = new JSONResultProcessor().createUserResponse(registerUser);
		return Response.status(200).entity(output).build();
    }
}
