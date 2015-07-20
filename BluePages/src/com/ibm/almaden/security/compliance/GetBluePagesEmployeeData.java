package com.ibm.almaden.security.compliance;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import com.ibm.almaden.bluepages.data.EmployeeInfoExtractionUsingWSAPI;
import com.ibm.almaden.bluepages.data.XMLParserUpdateJobRolesSkillSet;
import com.ibm.almaden.bluepages.entity.Employee;
import com.ibm.almaden.bluepages.entity.EmployeeDAO;
import com.ibm.almaden.bluepages.entity.EmployeeJRSSDAO;

public class GetBluePagesEmployeeData {
	
	public static void main(String [] argv)
	{
		GetBluePagesEmployeeData empList = new GetBluePagesEmployeeData();
		Hashtable<String,Employee> data = 
				empList.getAllEmployeeDataWithJRSSGivenEmailID(
						"phil.wheatley@ph.ibm.com");
		
		//updatedHash.values();
		
		printToCSVFile(data, "phil_wheatley.csv");
		
		
		//System.out.println("Final Data returned = \n" + updatedHash);
	}
	
	/**
	 * This method creates three files.
	 * 1) with basic employee information
	 * 2) with employee cnum and job roles
	 * 3) with employee cunu and skill sets
	 * @param emailID
	 * @param fileName
	 * @return
	 */
	public boolean printoToFileBluePagesEmployeeData(String emailID, String fileName)
	{
		
		Hashtable<String,Employee> data = getAllEmployeeDataWithJRSSGivenEmailID(emailID);
		if(data == null || data.size() < 1)
		{
			System.err.println("blue page data is not available for emailID = " + emailID);
			return false;
		}
		printToCSVFile(data, fileName);
		
		String jrFileName = "jr_" + fileName;
		printJobRolesToCSVFile(data, jrFileName);
		
		String ssFileName = "ss_" + fileName;
		printSkillSetsToCSVFile(data, ssFileName);
		
		
		
		return true;
	}
	
	
	/**
	 * 
	 * @param updatedHash
	 */
	public void printJobRolesToCSVFile(Hashtable<String,Employee> updatedHash, String fileName) {
		
		try {
			FileWriter fw = new FileWriter(fileName);
			String header = "cnum, JobRole\n";
			fw.write(header);
			
			for (Enumeration<String> e = updatedHash.keys(); e.hasMoreElements();) 	{
				String cnum = e.nextElement();
				Employee emp = (Employee)updatedHash.get(cnum);
				fw.write(emp.toStringJobRolesCSV());							
			} 
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * 
	 * @param updatedHash
	 */
	public  void printSkillSetsToCSVFile(Hashtable<String,Employee> updatedHash, String fileName) {
		
		try {
			FileWriter fw = new FileWriter(fileName);
			String header = "cnum, SkillSet\n";
			fw.write(header);
			
			for (Enumeration<String> e = updatedHash.keys(); e.hasMoreElements();) 	{
				String cnum = e.nextElement();
				Employee emp = (Employee)updatedHash.get(cnum);
				fw.write(emp.toStringSkillSetCSV());							
			} 
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
	 * 
	 * @param emailID
	 * @return
	 */
	public boolean storeBluePagesDataInDBGivenEmailID(String emailID)
	{
		Hashtable<String,Employee> data = getAllEmployeeDataWithJRSSGivenEmailID(emailID);
		if(data == null || data.size() < 1)
		{
			System.err.println("blue page data is not available for emailID = " + emailID);
			return false;
		}
		
		// first store data in BP_DATA_DAILY table
		EmployeeDAO dao = new EmployeeDAO();
		
		// we need to first update the is_active flag
		//boolean emp_data_stored = dao.batchInsertData(data.values());
		Collection<Employee> empV = data.values();
		boolean emp_data_stored = false;
		for (Employee emp : empV) {
			System.out.println("emp = " + emp.toString());
			emp_data_stored = dao.insertData(emp);
			 
		}
		
		
		if (false == emp_data_stored)
			return false; // not moving ahead
		
		// now store data in BP_DATA_EMP_JR and BP_DATA_EMP_SS
		// in the future we need to write query to update the records rather then duplicate the entries.
		EmployeeJRSSDAO jrssDao = new EmployeeJRSSDAO();
		jrssDao.batchInsertDataJR(data.values());
		jrssDao.batchInsertDataSS(data.values());
		
		return true;
		
	}
	
	
	/**
	 * 
	 * @param cnum
	 * @return
	 */
	public boolean storeBluePagesDataInDBGivenCNUM(String cnum)
	{
		Hashtable<String,Employee> data = getAllEmployeeDataWithJRSSGivenEmailID(cnum);
		if(data == null || data.size() < 1)
		{
			System.err.println("blue page data is not available for emailID = " + cnum);
			return false;
		}
		
		// first store data in BP_DATA_DAILY table
		EmployeeDAO dao = new EmployeeDAO();
		
		// we need to first update the is_active flag
		//boolean emp_data_stored = dao.batchInsertData(data.values());
		Collection<Employee> empV = data.values();
		boolean emp_data_stored = false;
		for (Employee emp : empV) {
			System.out.println("emp = " + emp.toString());
			emp_data_stored = dao.insertData(emp);
			 
		}
		
		
		if (false == emp_data_stored)
			return false; // not moving ahead
		
		// now store data in BP_DATA_EMP_JR and BP_DATA_EMP_SS
		// in the future we need to write query to update the records rather then duplicate the entries.
		EmployeeJRSSDAO jrssDao = new EmployeeJRSSDAO();
		jrssDao.batchInsertDataJR(data.values());
		jrssDao.batchInsertDataSS(data.values());
		
		return true;
		
	}
	
	/**
	 * Store blue page entries in DB2 table 
	 * @param emailID
	 * @return
	 */
	public boolean storeBluePagesDataInDB(Hashtable<String,Employee> data)
	{
		if(data == null || data.size() < 1)
		{
			System.err.println("blue page data is not available");
			return false;
		}
		
		// first store data in BP_DATA_DAILY table
		EmployeeDAO dao = new EmployeeDAO();
		
		// we need to first update the is_active flag
		boolean emp_data_stored = dao.batchInsertData(data.values());
		
		if (false == emp_data_stored)
			return false; // not moving ahead
		
		// now store data in BP_DATA_EMP_JR and BP_DATA_EMP_SS
		// in the future we need to write query to update the records rather then duplicate the entries.
		EmployeeJRSSDAO jrssDao = new EmployeeJRSSDAO();
		jrssDao.batchInsertDataJR(data.values());
		jrssDao.batchInsertDataSS(data.values());
		
		return true;
	}
	
	
	
	/**
	 * 
	 * @param emailID
	 * @return
	 */
	public Hashtable<String,Employee> getAllEmployeeDataWithJRSSGivenEmailID(String emailID)
	{
		Hashtable<String,Employee> dataHash = getAllEmployeeGivenEmailID(emailID);
		Hashtable<String,Employee> updatedHash = getAllEmployeeJRSSData(dataHash);
		return updatedHash;
	}
	
	/**
	 * 
	 * @param cnum
	 * @return
	 */
	public Hashtable<String,Employee> getAllEmployeeDataWithJRSSGivenCNUM(String cnum)
	{
		Hashtable<String,Employee> dataHash = getAllEmployeeGivenEmailID(cnum);
		Hashtable<String,Employee> updatedHash = getAllEmployeeJRSSData(dataHash);
		return updatedHash;
	}
	
	/**
	 * 
	 * @param updatedHash
	 */
	private static void printToCSVFile(Hashtable<String,Employee> updatedHash, String fileName) {
		// TODO Auto-generated method stub
		
		try {
			FileWriter fw = new FileWriter(fileName);
			String header = "cnum, full name, emailID, dept, div, isManager, MgrSerialNum, JobRole, SkillSet\n";
			fw.write(header);
			
			for (Enumeration<String> e = updatedHash.keys(); e.hasMoreElements();)
			{
				String cnum = e.nextElement();
				Employee emp = (Employee)updatedHash.get(cnum);
				fw.write(emp.toStringCSV());
				
				
			} 
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public Hashtable<String,Employee> getAllEmployeeJRSSData(
			Hashtable<String,Employee> dataHash)
	{
		if (dataHash == null || dataHash.size() < 1)
			return null;
		
		XMLParserUpdateJobRolesSkillSet jrss = new XMLParserUpdateJobRolesSkillSet();
		
		
		for (Enumeration<String> e = dataHash.keys(); e.hasMoreElements();)
		{
			String cnum = e.nextElement();
			Employee emp = (Employee)dataHash.get(cnum);
			jrss.getUpdatedEmployeeData(emp.getEmailID(), emp);
			
		}

		return dataHash;
	}

	/**
	 * 
	 * @param emailID
	 * @return
	 */
	public Hashtable<String,Employee> getAllEmployeeGivenEmailID(String emailID)
	{
		Hashtable<String,Employee> allEmpDataHash = 
				new Hashtable<String,Employee>();
		
		if (emailID == null || emailID.equals(""))	{
			System.err.println("Email ID not supplied .. exiting");
			return null;
		}
		
		EmployeeInfoExtractionUsingWSAPI empInfo = 
				new EmployeeInfoExtractionUsingWSAPI();
		Vector<Employee> data = empInfo.getEmployeeListByEmailID(emailID);
		
		if(data == null || data.size() < 1) {
			System.err.println(" Not able to get the basic employee information from blue pages api for " + emailID);
		}
		else {
			Employee thisEmp = (Employee)data.elementAt(0);
			allEmpDataHash.put(thisEmp.getCnum(), thisEmp);
		
			if (thisEmp.getIsManager().trim().equals("Y")) {
				//String div = thisEmp.getEmployeeDiv();
				//String dept = thisEmp.getEmployeeDept();
				String cnum = thisEmp.getCnum();
				Hashtable<String,Employee> interimData = getAllEmployeeByMgrCNUM(cnum);
				allEmpDataHash.putAll(interimData);
			}
		
		}
		
		
		return allEmpDataHash;
	}
	

	/**
	 * 
	 * @param cnum
	 * @return
	 */
	public Hashtable<String,Employee> getAllEmployeeGivenCNUM(String cnum)
	{
		Hashtable<String,Employee> allEmpDataHash = 
				new Hashtable<String,Employee>();
		
		if (cnum == null || cnum.equals(""))	{
			System.err.println("cnum ID not supplied .. exiting");
			return null;
		}
		
		EmployeeInfoExtractionUsingWSAPI empInfo = 
				new EmployeeInfoExtractionUsingWSAPI();
		Vector<Employee> data = empInfo.getEmployeeDataByCNUM(cnum);
		//Vector<Employee> data = empInfo.getEmployeeListByEmailID(emailID);
		
		if(data == null || data.size() < 1) {
			System.err.println(" Not able to get the basic employee information from blue pages api for " + cnum);
		}
		else {
			Employee thisEmp = (Employee)data.elementAt(0);
			System.out.println(thisEmp);
			allEmpDataHash.put(thisEmp.getCnum(), thisEmp);
		}
		
		
		return allEmpDataHash;
	}
	
	/**
	 * 
	 * @param emailID
	 * @return
	 */
	public Employee getEmployeeGivenEmailID(String emailID)
	{
		
		if (emailID == null || emailID.equals(""))	{
			System.err.println("Email ID not supplied .. exiting");
			return null;
		}
		
		EmployeeInfoExtractionUsingWSAPI empInfo = 
				new EmployeeInfoExtractionUsingWSAPI();
		Vector<Employee> data = empInfo.getEmployeeListByEmailID(emailID);
		if(data == null || data.size() < 1) {
			System.err.println(" Not able to get the basic employee information from blue pages api for " + emailID);
			return null;
		}
		else {
			Employee thisEmp = (Employee)data.elementAt(0);
			return thisEmp;
		}
	}

	
	
	/**
	 * Get all entries by department code
	 * @param department code
	 * @return
	 */
	public Hashtable<String,Employee> getAllEmployeesGivenDepartmentCode(
			String deptCode)
	{
		Hashtable<String,Employee> allEmpDataHash = 
				new Hashtable<String,Employee>();
		
		if (deptCode == null || deptCode.equals(""))	{
			System.err.println("Department code not supplied .. exiting");
			return null;
		}

		if (deptCode.equals("*"))	{
			System.err.println("Department code not valid .. exiting");
			return null;
		}

		EmployeeInfoExtractionUsingWSAPI empInfo = 
				new EmployeeInfoExtractionUsingWSAPI();
		Vector<Employee> data = //empInfo.getEmployeeListByDivandOrg("", deptCode);
				empInfo.getEmployeeListByDept(deptCode);
		if(data == null || data.size() < 1) {
			System.err.println(" Not able to get the basic employee information " +
					"from blue pages api for " + deptCode);
		}
		else {	
			for(Iterator<Employee> i = data.iterator(); i.hasNext(); ) {
				Employee empl = i.next();
				if(empl.getCnum() != null)
					allEmpDataHash.put(empl.getCnum(), empl);
			}
		}
		return allEmpDataHash;
	}
	
	
	/**
	 * get all employees reporting to the manager, at any level
	 * @param cnum                                                                                    
	 * @return
	 */
	public Hashtable<String,Employee> getAllEmployeeByMgrCNUM(String cnum)
	{
		Hashtable<String,Employee> retHash = new Hashtable<String,Employee>();
		EmployeeInfoExtractionUsingWSAPI empInfo = new EmployeeInfoExtractionUsingWSAPI();
		Vector<Employee> data = empInfo.getEmployeeListByMgrCNUM(cnum);
		
		if( data != null && data.size() > 0)
		{
			
			for (Object temp: data )
			{
				Employee t = (Employee)temp;
				retHash.put(t.getCnum(), t);
				
				if (t.getIsManager().trim().equals("Y"))
				{
					String cnumT = t.getCnum();
					Hashtable<String,Employee> interimData = getAllEmployeeByMgrCNUM(cnumT);
					System.out.println("Data returned = \n" + interimData.size());
					retHash.putAll(interimData);
					
				}
				
			}
			
			
		}
		return retHash;
		
		
	}
	
	
	
	/**
	 * get directs reports given the manager id
	 * @param cnum
	 * @return
	 */
	public Hashtable<String,Employee> getDirectEmployeeByMgrCNUM(String cnum)
	{
		Hashtable<String,Employee> retHash = new Hashtable<String,Employee>();
		EmployeeInfoExtractionUsingWSAPI empInfo = new EmployeeInfoExtractionUsingWSAPI();
		Vector<Employee> data = empInfo.getEmployeeListByMgrCNUM(cnum);
		
		if( data != null && data.size() > 0)
		{
			
			for (Object temp: data )
			{
				Employee t = (Employee)temp;
				retHash.put(t.getCnum(), t);
				
//				if (t.getIsManager().trim().equals("Y"))
//				{
//					String cnumT = t.getCnum();
//					Hashtable<String,Employee> interimData = getAllEmployeeByMgrCNUM(cnumT);
//					System.out.println("Data returned = \n" + interimData.size());
//					retHash.putAll(interimData);			
//				}	
			}
		}
		return retHash;
		
		
	}

}
