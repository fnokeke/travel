package com.ibm.almaden.bluepages.entity;

import java.util.Vector;

/**
 * 
 * @author simple_user
 *
 */
public class EmployeeJRSS {
		
	
	String cnum;
	
	Vector<String> jobRoles = new Vector<String>();
	Vector<String> skillSets = new Vector<String>();
	
	public EmployeeJRSS(String cnum,Vector<String> jobRoles ,Vector<String> skillSets  )
	{
		this.cnum = cnum;
		this.jobRoles = jobRoles;
		this.skillSets = skillSets;
	}
	
	
	public String getCnum() {
		return cnum;
	}
	public void setCnum(String cnum) {
		this.cnum = cnum.trim();
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

	

	/**
	 * 
	 */
	public String toString()
	{
		StringBuffer strBuff = new StringBuffer();	
		
		
		strBuff.append("cnum = " + cnum + "\n");
		
		
		
		
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
