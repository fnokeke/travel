package com.ibm.arc.documentsecurity.resources;

//import javax.enterprise.inject.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.ibm.arc.documentsecurity.bean.DataBean;
import com.ibm.arc.documentsecurity.bean.DataEmbedMe;

//import com.ibm.arc.documentsecurity.bean.DataEmbedMe;

@javax.ws.rs.Path("/dataservice")
public class DataBeanService {

	//
	// username, passwd, dbname
	//
	@GET
	@Produces("application/json")
	@Path("/{username_String}/{passwd_String}/{dbname_String}")
	public DataBean getDataEcho(
			@PathParam(value = "username_String") String username,
			@PathParam(value = "passwd_String") String passwd,
			@PathParam(value = "dbname_String") String dbname) {

		System.out.println("Received:\nusername = " + username + "\npasswd = "
				+ passwd + "\ndbname = " + dbname);

		DataBean db = new DataBean();
		db.setUsername(username);
		db.setPasswd(passwd);
		db.setDB(dbname);

		// add dictionary
		DataEmbedMe data = new DataEmbedMe();
		data.setTest("Hello");
		db.setData_embed(data);
		return db;

	}

	@GET
	@Produces("application/json")
	@Path("{id : \\d+}")
	// support digit only
	public Response getUserById(@PathParam("id") String id) {
		return Response.status(200).entity("getUserById is called, id : " + id)
				.build();

	}

	@GET
	@Produces("application/json")
	@Path("/username/{username : [a-zA-Z][a-zA-Z_0-9]}")
	public Response getUserByUserName(@PathParam("username") String username) {

		return Response.status(200)
				.entity("getUserByUserName is called, username : " + username)
				.build();

	}
}
