package com.ibm.arc.documentsecurity.resources;

//import javax.enterprise.inject.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.ibm.arc.documentsecurity.bean.DataBean;
//import com.ibm.arc.documentsecurity.bean.DataEmbedMe;

@javax.ws.rs.Path("/dataservice")
public class DataBeanService {
	
	@GET
    @Produces("application/json")
	@Path(value="/{data1_String}/{data2_double}")
	
	public DataBean getDataEcho( 	@PathParam(value="data1_String") String data1, 
			   @PathParam(value="data2_double") double data2) {
		
		System.out.println("Data 1 = " + data1 + "  and  Data2 = " + data2);
		
		DataBean db = new DataBean();
		db.setData1(data1);
		db.setData2(data2);

		//DataEmbedMe data = new DataEmbedMe();
		//data.setTest("Hello");
		//db.setData_embed(data);
		//db.setDem(data);
		return db;
		
	}
	
	@GET
    @Produces("application/json")
	@Path(value="/{data1_String}/{data2_double}/{data3_int}")
	
	public DataBean getDataEcho( 	@PathParam(value="data1_String") String data1, 
			   @PathParam(value="data2_double") double data2,
			   @PathParam(value="data3_int") int data3 ) {
		
		System.out.println("Data 1 = " + data1 + "  and  Data2 = " + data2 + "  Data 3 = " + data3);
		
		DataBean db = new DataBean();
		db.setData1(data1);
		db.setData2(data2);
		
		return db;
		
	}
	
	/*
	
	
	public FileBean getListOfFiles(String user,String location) 	{
	
			//Business logic
		
		// what is the tpe of the user
		// Is user same location as the home location
		// REturning the files 
	}
	
	public FileBean getListOfAllFilesForInformation() {
		
		
	}
	
	*/
	
}