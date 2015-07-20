package com.ibm.almaden.bluepages.data;
import java.io.ByteArrayInputStream;
import java.util.Vector;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ibm.almaden.bluepages.entity.Employee;


public class XMLParserUpdateJobRolesSkillSet {
	
	//http://bluepages.ibm.com/BpHttpApisv3/wsapi?membersOfDept=22+KF7A
	public static void main(String argb[])
	{
		XMLParserUpdateJobRolesSkillSet help = new XMLParserUpdateJobRolesSkillSet();
		Employee empData = new Employee();
		String emailID = "chowdhar@us.ibm.com";
		
		Employee emp = help.getUpdatedEmployeeData(emailID,empData);
		
		System.out.println(emp);
		
	}
	
	
	public Employee getUpdatedEmployeeData(String emailID, Employee updateEmployee)
	{
		EmployeeJRSSExtractionUsingWSAPIXML emp = new EmployeeJRSSExtractionUsingWSAPIXML();
		String xmlData = emp.getBluePageXMLDataGivenEmail(emailID);
		
		//System.out.println(xmlData);
		//Employee empData = new Employee();
		
		parseXMLGivenStringData(xmlData,updateEmployee);
		
		return updateEmployee;
	}

	public void parseXMLGivenStringData(String xmlData,Employee updateEmployee)
	{
		try {
		    DOMParser parser = new DOMParser();
		    parser.parse(new InputSource(new ByteArrayInputStream(xmlData.getBytes("utf-8"))));
		    Document doc = parser.getDocument();
		 
		    // Get the document's root XML node
		    NodeList root = doc.getChildNodes();
		 
		    // Navigate down the hierarchy to get to the CEO node
		    Node comp = getNode("bpml", root);
		    Node exec = getNode("entry", comp.getChildNodes() );
		    String execType = getNodeAttr("uid", exec);
		    //System.out.println(execType);
		    
		    NodeList attrNodeList = exec.getChildNodes();
		    int totalAttrCount = attrNodeList.getLength();
		    
		    //System.out.println(totalAttrCount);
		    
		    for (int i = 0; i < totalAttrCount; i++)
		    {
		    	Node n = attrNodeList.item(i);
		    	String value = getNodeAttr("name", n);
		    	//System.out.println(value);
		    	
		    	if (value.equalsIgnoreCase("uid") ) {
		    		
		    		Node vNode = getNode("value", n.getChildNodes() );
		    		String data = getNodeValue(vNode);
		    		//System.out.println(data );		    		
		    		updateEmployee.setCnum(data);
		    		
		    	}
		    	else if (value.equalsIgnoreCase("mail") ) {
		    		Node vNode = getNode("value", n.getChildNodes() );
		    		String data = getNodeValue(vNode);
		    		//System.out.println(data );		 
		    		updateEmployee.setEmailID(data);
		    		
		    		
		    	}
		    	else if (value.equalsIgnoreCase("region") ) {
		    		Node vNode = getNode("value", n.getChildNodes() );
		    		//System.out.println(getNodeValue(vNode) );
		    	}
		    	else if (value.equalsIgnoreCase("geo") ) {
		    		Node vNode = getNode("value", n.getChildNodes() );
		    		//System.out.println(getNodeValue(vNode) );
		    	}		
		    	
		    	else if (value.equalsIgnoreCase("jobrole") ) {
		    		
		    		Node vNode = getNode("value", n.getChildNodes() );
		    		NodeList jrNodeList = n.getChildNodes();
		 		    int totalValCount = jrNodeList.getLength();
		 		    
		 		    //System.out.println(totalValCount);
		 		    Vector jobRoleV = new Vector();
		 		    for (int j = 0; j < totalValCount; j++)
		 		    {
		 		    	Node nJR = jrNodeList.item(j);
		 		    	String jobRole = getNodeValue(nJR);
		 		    	if(jobRole == null || jobRole.trim().equals(""))
		 		    			continue;
		 		    	jobRoleV.addElement(jobRole);
		 		    	//System.out.println(jobRole);
		 		    }
		 		    
		 		   updateEmployee.setJobRoles(jobRoleV);
		    		
		    	}		
		    	
		    	else if (value.equalsIgnoreCase("jobroleskillsets") ) {		    		
		    		
		    		NodeList jrssNodeList = n.getChildNodes();
		 		    int totalValCount = jrssNodeList.getLength();
		 		    
		 		    System.out.println(totalValCount);
		 		    Vector<String> jobRoleSSV = new Vector<String>();
		 		    for (int j = 0; j < totalValCount; j++)
		 		    {
		 		    	Node nJR = jrssNodeList.item(j);
		 		    	String jobRoleSS = getNodeValue(nJR);
		 		    	if(jobRoleSS == null || jobRoleSS.trim().equals(""))
	 		    			continue;
		 		    	jobRoleSSV.addElement(jobRoleSS);
		 		    	System.out.println(jobRoleSS);
		 		    }
		 		    
		 		   updateEmployee.setSkillSets(jobRoleSSV);
		    	}		
		    	
		    	else if (value.equalsIgnoreCase("geo") ) {
		    		Node vNode = getNode("value", n.getChildNodes() );
		    		System.out.println(getNodeValue(vNode) );
		    	}				    	
		   
		    }

		}
		catch ( Exception e ) {
		    e.printStackTrace();
		}
		
	}
	
	public void parseXMLGivenFile(String fileName)
	{
		try {
		    DOMParser parser = new DOMParser();
		    parser.parse(fileName);
		    Document doc = parser.getDocument();
		 
		    // Get the document's root XML node
		    NodeList root = doc.getChildNodes();
		 
		    // Navigate down the hierarchy to get to the CEO node
		    Node comp = getNode("bpml", root);
		    Node exec = getNode("entry", comp.getChildNodes() );
		    String execType = getNodeAttr("uid", exec);
		    //System.out.println(execType);
		 
		    // Load the executive's data from the XML
//		    NodeList nodes = exec.getChildNodes();
//		    String lastName = getNodeValue("LastName", nodes);
//		    String firstName = getNodeValue("FirstName", nodes);
//		    String street = getNodeValue("street", nodes);
//		    String city = getNodeValue("city", nodes);
//		    String state = getNodeValue("state", nodes);
//		    String zip = getNodeValue("zip", nodes);
//		 
//		    System.out.println("Executive Information:");
//		    System.out.println("Type: " + execType);
//		    System.out.println(lastName + ", " + firstName);
//		    System.out.println(street);
//		    System.out.println(city + ", " + state + " " + zip);
		}
		catch ( Exception e ) {
		    e.printStackTrace();
		}
		
	}
	
	protected Node getNode(String tagName, NodeList nodes) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            return node;
	        }
	    }
	 
	    return null;
	}
	 
	protected String getNodeValue( Node node ) {
	    NodeList childNodes = node.getChildNodes();
	    for (int x = 0; x < childNodes.getLength(); x++ ) {
	        Node data = childNodes.item(x);
	        if ( data.getNodeType() == Node.TEXT_NODE )
	            return data.getNodeValue();
	    }
	    return "";
	}
	 
	protected String getNodeValue(String tagName, NodeList nodes ) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            NodeList childNodes = node.getChildNodes();
	            for (int y = 0; y < childNodes.getLength(); y++ ) {
	                Node data = childNodes.item(y);
	                if ( data.getNodeType() == Node.TEXT_NODE )
	                    return data.getNodeValue();
	            }
	        }
	    }
	    return "";
	}
	 
	protected String getNodeAttr(String attrName, Node node ) {
	    NamedNodeMap attrs = node.getAttributes();
	    for (int y = 0; y < attrs.getLength(); y++ ) {
	        Node attr = attrs.item(y);
	        if (attr.getNodeName().equalsIgnoreCase(attrName)) {
	            return attr.getNodeValue();
	        }
	    }
	    return "";
	}
	 
	protected String getNodeAttr(String tagName, String attrName, NodeList nodes ) {
	    for ( int x = 0; x < nodes.getLength(); x++ ) {
	        Node node = nodes.item(x);
	        if (node.getNodeName().equalsIgnoreCase(tagName)) {
	            NodeList childNodes = node.getChildNodes();
	            for (int y = 0; y < childNodes.getLength(); y++ ) {
	                Node data = childNodes.item(y);
	                if ( data.getNodeType() == Node.ATTRIBUTE_NODE ) {
	                    if ( data.getNodeName().equalsIgnoreCase(attrName) )
	                        return data.getNodeValue();
	                }
	            }
	        }
	    }
	 
	    return "";
	}

}
