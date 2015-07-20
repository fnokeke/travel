package com.ibm.almaden.bluepages.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;
import java.util.Vector;

import com.ibm.almaden.bluepages.entity.Employee;

public class EmployeeInfoExtractionUsingWSAPI {
	
	//http://bluepages.ibm.com/BpHttpApisv3/wsapi?membersOfDept=div+dept 
	//http://bluepages.ibm.com/BpHttpApisv3/wsapi?managerChainFor=6A8614897
	//http://bluepages.ibm.com/BpHttpApisv3/wsapi?byCnum=899208897
	//http://bluepages.ibm.com/BpHttpApisv3/wsapi?byInternetAddr=chowdhar@us.ibm.com
	//http://bluepages.ibm.com/BpHttpApisv3/wsapi?directReportsOf=859491897
	
	//859491897
	
	String baseURL = "https://bluepages.ibm.com/BpHttpApisv3/wsapi?";
	
	
	public static void main(String[] argv)
	{
		EmployeeInfoExtractionUsingWSAPI gbp = new EmployeeInfoExtractionUsingWSAPI();
		//gbp.getScreenScrap();
		//gbp.getEmployeeListByDivandOrg("22", "KF7A");
		gbp.getEmployeeListByDept("KF7A");
	}
	
	//http://w3.ibm.com/jct03019wt/bluepages/searchByName.wss?uid=809988818&task=viewrecord

	public String getBluePagesXMLData()
	{
		StringBuffer strBuf = new StringBuffer();
		try
		{
			//String url1 = "http://w3.ibm.com/jct03019wt/bluepages/searchByName.wss?uid=809988818&task=viewrecord";
			String url1="https://w3.ibm.com/bluepages/api/BluePagesAPI.jsp?EMAIL=chowdhar@us.ibm.com";

			
			System.out.println(url1);
			URL bluePageURL = new URL(url1);
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

	String baseURL2_prefix = "https://bluepages.ibm.com/BpHttpApisv3/slaphapi?*/dept=";
	public Vector<Employee> getEmployeeListByDept(String dept)
	{			
		String thisURL = baseURL2_prefix + dept;
		String xml = getBluePagesXMLData(thisURL);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
		System.out.println("Received from URL: "+thisURL);
		System.out.println(xml);
		
		ParseBluePageXMLData parser = new ParseBluePageXMLData();
		return parser.extractEmployees(xml);
	}
	
	
	public Vector<Employee> getEmployeeListByDivandOrg(String divID, String orgID)
	{			
		String anotherParamVal = divID + "+" + orgID;		
		String thisURL = baseURL + "membersOfDept=" + anotherParamVal;
		return getURLData(thisURL);
	}

	public Vector<Employee> getEmployeeListByEmailID(String emailID)
	{			
		String anotherParamVal = emailID;		
		String thisURL = baseURL + "byInternetAddr=" + anotherParamVal;
		return getURLData(thisURL);
	}
	
	public Vector<Employee> getEmployeeListByMgrCNUM(String cnum)
	{			
		String anotherParamVal = cnum;		
		String thisURL = baseURL + "directReportsOf=" + anotherParamVal;
		return getURLData(thisURL);
	}
	
	public Vector<Employee> getEmployeeDataByCNUM(String cnum)
	{			
		String thisURL = "https://bluepages.ibm.com/BpHttpApisv3/slaphapi?*/uid=" + cnum;
		String xml = getBluePagesXMLData(thisURL);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
		System.out.println("Received from URL: "+thisURL);
		System.out.println(xml);
		
		ParseBluePageXMLData parser = new ParseBluePageXMLData();
		return parser.extractEmployees(xml);
	}
	
	
	/**
	 * Rteurn bluepages data as XML
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
	
	
	
	/**
	 * gets bluepages data as text
	 * @param URL
	 * @return
	 */
	public Vector<Employee> getURLData(String URL)
	{
		Vector<Employee> retEmpV = new Vector<Employee> ();
		try
		{
			System.out.println(URL);
			
			URL bluePageURL = new URL(URL);
			URLConnection connection = bluePageURL.openConnection();			
		    BufferedReader in = new BufferedReader(new InputStreamReader( connection.getInputStream()));		    
	        String inputLine;
	        Employee e = null;
	        while ((inputLine = in.readLine()) != null) 
	        {
	        	StringTokenizer strToken = new StringTokenizer(inputLine,":");	        	
	        	String firstToken = strToken.nextToken();
	        	
	        	//System.out.println("First Token= " + firstToken);
	        	// this is the first line for series
	        	if (firstToken.equals("CNUM"))
	        	{
	        		e = new Employee();	        		
	        		String secondToken = null;	        		
	        		if (strToken.hasMoreElements())
	        		{
	        			secondToken = strToken.nextToken();
	        			e.setCnum(secondToken);
	        		}
	        	}        		
    			if (firstToken.equalsIgnoreCase("Name"))
    			{
    				if (strToken.hasMoreElements())
	        		{
	        			String secondToken = strToken.nextToken();
	        			e.setEmployeeName(secondToken);
	        		}
    				//e.setEmployeeName( (strToken.hasMoreElements()) ? strToken.nextToken(): null);
    			}
    			else if (firstToken.equalsIgnoreCase("Dept"))
	        		e.setEmployeeDept((strToken.hasMoreElements()) ? strToken.nextToken(): null);
    			else if (firstToken.equalsIgnoreCase("WORKLOC"))
    				e.setWorkLocation((strToken.hasMoreElements()) ? strToken.nextToken(): null);
    			else if (firstToken.equalsIgnoreCase("COUNTRY"))
    				e.setCountry((strToken.hasMoreElements()) ? strToken.nextToken(): null);
    			else if (firstToken.equalsIgnoreCase("Div"))
	        		e.setEmployeeDiv((strToken.hasMoreElements()) ? strToken.nextToken(): null);
    			
    			else if (firstToken.equalsIgnoreCase("JOBRESPONSIB"))
    			{
    				Vector<String> jobroles = new Vector<String>();
    				if(strToken.hasMoreElements())
    				{
    					//String [] tokens = strToken.nextToken().split(",");
    					
    					
    						jobroles.add(strToken.nextToken());
    					
    				}
    				
    				
    				e.setJobRoles(jobroles);
    			}
	        		
    			
    			
    			else if (firstToken.equalsIgnoreCase("Mgr"))
	        		e.setIsManager((strToken.hasMoreElements()) ? strToken.nextToken(): null);
    			else if (firstToken.equalsIgnoreCase("Internet"))
	        		e.setEmailID((strToken.hasMoreElements()) ? strToken.nextToken(): null);
    			else if (firstToken.equalsIgnoreCase("MNUM")) // this is the last line for the series
    			{
	        		e.setMgrCnum((strToken.hasMoreElements()) ? strToken.nextToken(): null);	
	        		retEmpV.addElement(e);
    			}
	        }
	        in.close();
		}
		catch (Exception ex)
		{
			
			ex.printStackTrace();
		}
	        
		return retEmpV;
	}

}
