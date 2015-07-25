package com.ibm.arc.documentsecurity.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.ibm.arc.documentsecurity.resources.Utility;


@Path("/users")
@Produces("application/json")
public class DataBeanService {

	@GET
	@Path("/{username}/{password}")
	public Response getUserByUserName(@PathParam("username") String username) {
		System.out.println("Username: " + username);

		return Response.status(Response.Status.OK)
				.entity(Utility.toJSON("username", username)).build();

	}


}
