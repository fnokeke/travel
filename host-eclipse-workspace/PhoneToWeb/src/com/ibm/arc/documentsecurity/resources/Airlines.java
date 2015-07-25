package com.ibm.arc.documentsecurity.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.ibm.arc.documentsecurity.resources.Utility;

@Path("/airlines")
@Produces("application/json")
public class Airlines {

	@GET
	@Path("/airlineId/{airlineId}")
	public Response getUserByUserName(@PathParam("airlineId") String airlineId) {
		System.out.println("airlineId: " + airlineId);

		return Response.status(Response.Status.OK)
				.entity(Utility.toJSON("airlineId", airlineId)).build();

	}

}
