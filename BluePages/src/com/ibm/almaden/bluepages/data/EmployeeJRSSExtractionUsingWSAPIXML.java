package com.ibm.almaden.bluepages.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class EmployeeJRSSExtractionUsingWSAPIXML {
	
	String baseURLwithEmail ="https://w3.ibm.com/bluepages/api/BluePagesAPI.jsp?EMAIL=";
	String baseURLwithCNUM ="https://w3.ibm.com/bluepages/api/BluePagesAPI.jsp?CNUM=";
		
	/**
	 * 
	 * @param emailID
	 * @return
	 */
	public String getBluePageXMLDataGivenEmail(String emailID)
	{
		String url=baseURLwithEmail + emailID;
		return getBluePagesXMLData(url);
	}
	
	/**
	 * 
	 * @param cnum
	 * @return
	 */
	public String getBluePageXMLDataGivenCNUM(String cnum)
	{
		String url=baseURLwithCNUM + cnum;
		return getBluePagesXMLData(url);
	}	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public String getBluePagesXMLData(String url)
	{
		StringBuffer strBuf = new StringBuffer();
		try
		{
			
			System.out.println(url);
			URL bluePageURL = new URL(url);
			URLConnection connection = bluePageURL.openConnection();
			
			
		    BufferedReader in = new BufferedReader(new InputStreamReader( connection.getInputStream()));
		        
		    String inputLine;
		        
	        while ((inputLine = in.readLine()) != null) 
	        {
	        	strBuf.append(inputLine);
	        	//System.out.println(inputLine);
	        }
	        in.close();
		}
		catch (Exception ex)
		{
			
			ex.printStackTrace();
		}
	        
		return strBuf.toString();
	}


}
