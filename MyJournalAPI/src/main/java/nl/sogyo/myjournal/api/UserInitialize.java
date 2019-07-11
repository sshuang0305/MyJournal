package nl.sogyo.myjournal.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("users")
public class UserInitialize {
    /**
   * @param request
   * @return
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response initialize(
      @Context HttpServletRequest request, Userdata user) {

      // String username = user.getUsername();
      // String password = user.getPassword();
      // System.out.println(username);
      // System.out.println(password);
      //
      // HttpSession session= request.getSession(true);
      String output = "hoi";
      // System.out.println(request);



      return Response.status(200).entity(output).build();
  }
}
