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

@Path("users/login")
public class UserLogin {

    /**
    * @param request
    * @return
    */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context HttpServletRequest request, UserData user) {

    	String username = user.getUsername();
		String password = user.getPassword();
		User loginUser = UserConnector.login(username, password);
		
		if (loginUser == null) {
			return Response.status(404).entity("").build();
		}
		String output = new JSONResultProcessor().createUserResponse(loginUser);
		return Response.status(200).entity(output).build();
    }
}
