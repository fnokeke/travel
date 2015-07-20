package com.ibm.almaden.bluepages.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ibm.almaden.bluepages.entity.Employee;

public class ParseBluePageXMLData {
	
	
	public Document parseXML(String xmlData) throws SAXException, IOException, ParserConfigurationException
	{
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setExpandEntityReferences(false);
        dbf.setValidating(false);
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        DocumentBuilder db = dbf.newDocumentBuilder();
       
        Document doc =  db.parse(new InputSource(new ByteArrayInputStream(xmlData.getBytes("utf-8"))));      
        return doc;
	}
	
	public Vector<Employee> extractEmployees(String xml) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nParsong the XML");
		Vector<Employee> retEmpV = new Vector<Employee> ();
		Employee e = null;
		
		Document doc = null;
		try {
			doc = this.parseXML(xml);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		doc.getDocumentElement().normalize(); 
		NodeList nodeList = doc.getElementsByTagName("entry");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {	
				e = new Employee();	   
				
				Element element = (Element) node;
				NodeList attrs = element.getElementsByTagName("attr");
				for(int j = 0; j < attrs.getLength(); j ++) {
					Node attr = attrs.item(j);
					if (attr.getNodeType() == Node.ELEMENT_NODE) {	
						Element attrElem = (Element) attr;
						String attrName = attrElem.getAttribute("name");
						//System.out.println(attrName);
						
						if(attrName.equalsIgnoreCase("uid")) {
							e.setCnum(getValue(attrElem));
							continue;
						}
						
						
						if(attrName.equalsIgnoreCase("jobrole")){
							
							e.setSkillSets(getarrayValue(attrElem));
							continue;
						}
						
						
						if(attrName.equalsIgnoreCase("cn")) {
							e.setEmployeeName(getValue(attrElem));
							continue;
						}
						if(attrName.equalsIgnoreCase("dept")) {
				    		e.setEmployeeDept(getValue(attrElem));
				    		continue;
						}
						if(attrName.equalsIgnoreCase("div")) {
				    		e.setEmployeeDiv(getValue(attrElem));
				    		continue;
						}
						if(attrName.equalsIgnoreCase("ismanager")) {
				    		e.setIsManager(getValue(attrElem));
				    		continue;
						}
						if(attrName.equalsIgnoreCase("mail")) {
				    		e.setEmailID(getValue(attrElem));
				    		continue;
						}
						if(attrName.equalsIgnoreCase("managerserialnumber")) { 
				    		e.setMgrCnum(getValue(attrElem));
				    		continue;
						}
					}
				}
				//System.out.println(e);
	    		retEmpV.addElement(e);
		}
		}
		
	return retEmpV;
	}
	
	/**
	 * <node name="xxx"><value>yyy</value></node>
	 * @param e
	 * @return Value yyy
	 */
	private String getValue(Element e) {
		NodeList children = e.getElementsByTagName("value");
		if(children == null || children.getLength() < 1)
			return "N/A";
		Node child = children.item(0);
		if (child.getNodeType() == Node.ELEMENT_NODE) {	
			Element ce = (Element) child;
			String value = ce.getTextContent();
			return value;
		}
		return "null";
	}
	
	
	private Vector<String> getarrayValue(Element e) {
		
		Vector<String> jobSkills = new  Vector<String>();
		
		NodeList children = e.getElementsByTagName("value");
		if(children == null || children.getLength() < 1)
			return jobSkills;
		for(int i=0; i < children.getLength(); i++){
			Node child = children.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {	
				Element ce = (Element) child;
				String value = ce.getTextContent();
				
				jobSkills.add(value);
				//return value;
			}
			
		}
		
		return jobSkills;
	}
	
	
}
