import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ElementTraversal;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ibm.almaden.bluepages.data.EmployeeInfoExtractionUsingWSAPI;
import com.ibm.almaden.bluepages.data.EmployeeJRSSExtractionUsingWSAPIXML;
import com.ibm.almaden.bluepages.data.ParseBluePageXMLData;
import com.ibm.almaden.bluepages.entity.Employee;
import com.ibm.almaden.security.compliance.GetBluePagesEmployeeData;


public class TestAnother {

	
	public static void main(String [] argv){
		
		EmployeeJRSSExtractionUsingWSAPIXML emp = new EmployeeJRSSExtractionUsingWSAPIXML();
		String xmlData = emp.getBluePageXMLDataGivenEmail("chowdhar@us.ibm.com");
		
		EmployeeInfoExtractionUsingWSAPI emp1 = new EmployeeInfoExtractionUsingWSAPI();
		Vector<Employee> employeeVector = new Vector<Employee>();
		
		
		employeeVector = emp1.getEmployeeListByEmailID("chowdhar@us.ibm.com");   //. getAllEmployeeGivenEmailID("chowdhar@us.ibm.com");
		
		System.out.println(employeeVector.get(0).toString());
		
		
		
		//InputSource in = new InputSource();
		//in.setCharacterStream(new StringReader(xmlData));
		System.out.println(xmlData);
		String username = null;
		//String jobRole = null;
		ArrayList<String> jobRoleList = new ArrayList<String>();
		
		
		
		ParseBluePageXMLData parse = new ParseBluePageXMLData();
		
		employeeVector = parse.extractEmployees(xmlData);
		
		System.out.println("************next Employeee*********************");
		System.out.println(employeeVector.get(0).toString());
		
		/*
		try {
			
			Document doc = parse.parseXML(xmlData);
			
			NodeList nodeList = doc.getElementsByTagName("attr");
			
			
			
			if(nodeList!=null && nodeList.getLength() >0){
				for(int j =0; j< nodeList.getLength(); j++)
				{
					Element elemName = (Element) nodeList.item(j);
					
					if(elemName.getAttribute("name").equals("callupname"))
					{
						username = elemName.getTextContent();
					}
					if(elemName.getAttribute("name").equals("jobrole"))
					{
						NodeList childNodes = elemName.getChildNodes();  //.getTextContent();
						
						for(int k=0; k < childNodes.getLength(); k++)
						{
							Node jobRoleElement =  childNodes.item(k);
							
							jobRoleList.add(jobRoleElement.getTextContent());
							
						}
					}
					
					
				}
			}
			System.out.println(username);
			//System.out.println(jobRole);
			
			for(String s:jobRoleList)
			{
				System.out.println(s);
			}
            DOMImplementation domImpl = doc.getImplementation();
            //domImpl.
            
//            Node firstNode = doc.getFirstChild();
//            System.out.println("First node = " + firstNode.toString());
            
            NodeList root = doc.getChildNodes();
            System.out.println(root.getLength());
            
            System.out.println( root.item(0));
            
            if (domImpl.hasFeature("ElementTraversal", "1.0")) {
                print(doc.getDocumentElement(), 0);
            }
            else {
                System.err.println("The DOM implementation does not claim support for ElementTraversal.");
            }
            
			NodeList nd = doc.getElementsByTagName("899208897");
			System.out.println(nd.toString());
			
			
			int length = nd.getLength();
			
			for (int i = 0; i < length-1 ; i++)
			{
				Node node = nd.item(i);
				
				
				System.out.println( node.getNodeName() + " = " + node.getNodeValue());
				
			}
			//System.out.println( doc.getElementsByTagName("jobroleskillsets"));
			
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
	}
	
	private static void print(Element e, int depth) {
        do {
            ElementTraversal et = (ElementTraversal) e;
            for (int i = 0; i < depth; ++i) {
                System.out.print("--");
            }
            System.out.print("--> [");
            System.out.print(new QName(e.getNamespaceURI(), e.getLocalName()));
            System.out.println("], Child Element Count = " + et.getChildElementCount());
            Element firstElementChild = et.getFirstElementChild();
            if (firstElementChild != null) {
                print(firstElementChild, depth + 1);
            }
            e = et.getNextElementSibling();
        }
        while (e != null);
    }
	
}
