package com.ibm.arc.documentsecurity.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.ibm.arc.documentsecurity.resources.Utility;

@Path("/hotels")
@Produces("application/json")
public class Hotels {

	@GET
	@Path("/hotelId/{hotelId}")
	public Response getUserByUserName(@PathParam("hotelId") String hotelId) {
		System.out.println("hotelId: " + hotelId);

		return Response.status(Response.Status.OK)
				.entity(Utility.toJSON("hotelId", hotelId)).build();

	}

}
