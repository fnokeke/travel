package com.ibm.almaden.bluepages.entity;

import java.util.HashMap;
import java.util.Vector;

/**
 * 
 * @author simple_user
 *
 */
public class Employee {
		
	String employeeID;
	String cnum;
	String emailID;
	String isManager;
	String employeeName;

	String employeeDept;
	String employeeDiv;
	String workLocation;
	String country;
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getWorkLocation() {
		return workLocation;
	}

	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}


	String mgrCnum;
	String mgrEmail;
	Vector<String> jobRoles = new Vector<String>();
	Vector<String> skillSets = new Vector<String>();
	HashMap<String, Employee> empMap = new HashMap<String, Employee>();
	
	
	public String getEmployeeDept() {
		return employeeDept;
	}

	public void setEmployeeDept(String employeeDept) {
		if(employeeDept != null)
			this.employeeDept = employeeDept.trim();
	}

	public String getEmployeeDiv() {
		return employeeDiv;
	}

	public void setEmployeeDiv(String employeeDiv) {
		
		if (employeeDiv != null)
			this.employeeDiv = employeeDiv.trim();
	}

	public String getEmployeeName() {
		return employeeName;
	}
	
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName.trim();
	}	
	
	public String getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID.trim();
	}
	
	public String getCnum() {
		return cnum;
	}
	public void setCnum(String cnum) {
		this.cnum = cnum.trim();
	}
	
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		if (emailID != null)
			this.emailID = emailID.trim();
	}
	
	public String getIsManager() {
		return isManager;
	}
	public void setIsManager(String isManager) {
		this.isManager = isManager.trim();
	}
	public String getMgrCnum() {
		return mgrCnum;
	}
	public void setMgrCnum(String mgrCnum) {
		this.mgrCnum = mgrCnum.trim();
	}
	public String getMgrEmail() {
		return mgrEmail;
	}
	public void setMgrEmail(String mgrEmail) {
		this.mgrEmail = mgrEmail.trim();
	}
	public Vector<String> getJobRoles() {
		return jobRoles;
	}
	public void setJobRoles(Vector<String> jobRoles) {
		this.jobRoles = jobRoles;
	}
	public Vector<String> getSkillSets() {
		return skillSets;
	}
	
	public void setSkillSets(Vector<String> skillSets) {
		this.skillSets = skillSets;
	}

	
	
	public HashMap<String, Employee> getEmpMap() {
		return empMap;
	}
	public void setEmpMap(HashMap<String, Employee> empMap) {
		this.empMap = empMap;
	}
	/**
	 * 
	 */
	public String toString()
	{
		StringBuffer strBuff = new StringBuffer();	
		
		strBuff.append("employeeID = " + employeeID + "\n");
		strBuff.append("cnum = " + cnum + "\n");
		strBuff.append("Employee Name = " + employeeName + "\n");
		strBuff.append("Employee Dept = " + employeeDept + "\n");
		strBuff.append("Employee Div = " + employeeDiv + "\n");
		strBuff.append("Work  Location = " + workLocation + "\n");
		strBuff.append("COUNTRY = " + country + "\n");
		
		
		
		strBuff.append("emailID = " + emailID + "\n");
		strBuff.append("isManager = " + isManager + "\n");
		strBuff.append("mgrCnum = " + mgrCnum + "\n");
		strBuff.append("mgrEmail = " + mgrEmail + "\n");
		
		for (Object job : jobRoles) {
			strBuff.append("jobRoles = " + job + "\n");
		}

		for (Object job : skillSets) {
			strBuff.append("skillSets = " + job + "\n");
		}
		
		
		return strBuff.toString();
	}

	/**
	 * 
	 * @return
	 */
	public String toStringCSV()
	{
		StringBuffer strBuff = new StringBuffer();
		
		//strBuff.append("employeeID = " + employeeID + ",");
		strBuff.append(cnum + ",");
		strBuff.append("\"" + employeeName + "\"" + ",");
		strBuff.append(emailID + ",");
		strBuff.append(employeeDept + ",");
		strBuff.append(employeeDiv + ",");		
		strBuff.append(isManager + ",");
		strBuff.append(mgrCnum + ",");
		//strBuff.append("mgrEmail = " + mgrEmail + ",");
		
		if( jobRoles.size() > 0)
			strBuff.append(jobRoles.elementAt(0).toString() + ",");
		else
			strBuff.append(",");
		
		if( skillSets.size() > 0)
			strBuff.append(skillSets.elementAt(0).toString());
		
		strBuff.append("\n");
		
		return strBuff.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public String toStringJobRolesCSV()
	{
		StringBuffer strBuff = new StringBuffer();
		
		//strBuff.append("employeeID = " + employeeID + ",");
		strBuff.append(cnum + ",");

		//strBuff.append("mgrEmail = " + mgrEmail + ",");
		
		if( jobRoles.size() > 0)
		{
			for (String jr : jobRoles)
			{
				strBuff.append(cnum + ","+jr + "\n");
			}
		}		
		return strBuff.toString();
	}
	

	/**
	 * 
	 * @return
	 */
	public String toStringSkillSetCSV()
	{
		StringBuffer strBuff = new StringBuffer();
		
		//strBuff.append("employeeID = " + employeeID + ",");
		strBuff.append(cnum + ",");

		//strBuff.append("mgrEmail = " + mgrEmail + ",");
		
		if( skillSets.size() > 0)
		{
			for (String ss : skillSets)
			{
				strBuff.append(cnum + ","+ss + "\n");
			}
		}		
		return strBuff.toString();
	}	
}
