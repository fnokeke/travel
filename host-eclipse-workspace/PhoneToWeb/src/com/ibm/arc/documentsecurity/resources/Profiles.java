package com.ibm.arc.documentsecurity.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.ibm.arc.documentsecurity.db2.QueryDB;

@Path("/profiles")
@Produces("application/json")
public class Profiles {

	@GET
	public Response getAllProfiles() {
		if (Utility.isDebugMode) {
			System.out.println("all profiles");
		}

		/* String query = String.format(
				"SELECT lastname, birthdate, gender" + 
				"FROM EMPLOYEE e INNER JOIN EMPLOYEE_DETAIL ed" +
			 	"ON e.TRAVELER_PROFILE = ed.TRAVELER_PROFILE" +
			 	"LIMIT 30");
				*/
		String query = String.format("SELECT firstname,middlename,lastname FROM EMPLOYEE LIMIT 5");
		QueryDB qdb = new QueryDB();

		return Response.status(Response.Status.OK).entity(qdb.run_query(query))
				.build();
	}

	@GET
	@Path("/profileId/{profileId}")
	public Response getProfileId(@PathParam("profileId") String profileId) {
		if (Utility.isDebugMode) {
			System.out.println("profileId: " + profileId);
		}

		String query = String.format("SELECT * FROM EMPLOYEE WHERE EMAIL='%s'", profileId);
		QueryDB qdb = new QueryDB();

		return Response.status(Response.Status.OK).entity(qdb.run_query(query))
				.build();
	}

	@GET
	@Path("/profileId/{profileId}/profileField/{profileField}")
	public Response getProfileField(@PathParam("profileId") String profileId,@PathParam("profileField") String profileField) {
		if (Utility.isDebugMode) {
			System.out.println("profileId: " + profileId);
			System.out.println("profileField: " + profileField);
		}

		String query = String.format("SELECT %s FROM EMPLOYEE WHERE EMAIL='%s'", profileField, profileId);
		QueryDB qdb = new QueryDB();

		return Response.status(Response.Status.OK).entity(qdb.run_query(query))
				.build();
	}
}
