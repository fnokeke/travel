import java.util.Vector;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;
import org.w3c.dom.ElementTraversal;

import com.ibm.almaden.bluepages.data.EmployeeInfoExtractionUsingWSAPI;
import com.ibm.almaden.security.compliance.GetBluePagesEmployeeData;


public class Test {

	
	public static void main(String [] argv){
		
		GetBluePagesEmployeeData emp = new GetBluePagesEmployeeData();
		System.out.println(emp.getAllEmployeeGivenCNUM("899208897"));
		
		  String LastName = "NWAFOR";
		  System.out.println(LastName = LastName.substring(0,1).toUpperCase()+ LastName.substring(1));
	
			
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
