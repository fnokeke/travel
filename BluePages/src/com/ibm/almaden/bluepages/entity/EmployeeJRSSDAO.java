package com.ibm.almaden.bluepages.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

//import com.ibm.data.connection.DBConnection;

public class EmployeeJRSSDAO {
	
	public String data_view_param_jr = "SELECT * FROM BLUEPAGE.bp_data_emp_jr";
	public String data_view_param_ss = "SELECT * FROM BLUEPAGE.bp_data_emp_ss";
	
	
	public String insert_view_param_jr = "INSERT INTO BLUEPAGE.BP_DATA_EMP_JR(CNUM, JOB_ROLE, CREATE_DATE, IS_ACTIVE) VALUES (?, ?,current date,1 )"; 
	public String insert_view_param_ss = "INSERT INTO BLUEPAGE.BP_DATA_EMP_SS(CNUM, SKILL_SET, CREATE_DATE, IS_ACTIVE) VALUES (?, ?,current date,1 )";
	
	public String update_all_param = null; // No primary key found hence update functionality not supported
	public String delete_all_param = null; // No primary key found hence delete functionality not supported
	public String count_all_param_jr = "SELECT COUNT(*) FROM BLUEPAGE.BP_DATA_EMP_JR jr"; 
	public String count_all_param_ss = "SELECT COUNT(*) FROM BLUEPAGE.BP_DATA_EMP_SS ss";

	
	/**
	  * To generate the bid_comment object representation of a db record
	  * @param rs The ResultSet returned from a query
	  * @return the bid_comment object
	  * @throws SQLException
	
	public Employee toBean(ResultSet rs) { 
		Employee dataObj = new Employee();
		try {
			dataObj.setCnum(rs.getString("CNUM"));			
			dataObj.setEmployeeName(rs.getString("FULL_NAME"));
			
			dataObj.setEmailID(rs.getString("EMAILID"));			
			dataObj.setEmployeeDept(rs.getString("DEPT"));

			dataObj.setEmployeeDiv(rs.getString("DIV"));			
			dataObj.setIsManager(rs.getString("ISMANAGER"));

			dataObj.setMgrCnum(rs.getString("MGRCNUM"));			
			String jobRole = rs.getString("JOB_ROLE");
			if (jobRole !=null)
			{
				Vector<String> v = new Vector<String>();
				v.addElement(jobRole);
				dataObj.setJobRoles(v);
				
			}
			
			String skillSet = rs.getString("SKILL_SET");
			if (skillSet !=null)
			{
				Vector<String> v = new Vector<String>();
				v.addElement(skillSet);
				dataObj.setSkillSets(v);
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return dataObj;
	}
	  */
	/** 
	  * To get all bid_comment objects 
	   
	public Vector<Employee> getAllData() { 
		Connection conn = null ; 
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		Vector<Employee> retDataObj = new Vector<Employee>() ; 
		try 
		{ 
			DBConnection dbConn = new DBConnection(); 
			conn = dbConn.getConnection(); 
			pstmt = conn.prepareStatement( data_view_param); 
			rs = pstmt.executeQuery(); 
			while(rs.next()) 
			{ 
				retDataObj.add(toBean(rs));
			} 
		}
		catch (Exception e) { 
			//SQLExceptionPrint(e); 
			System.out.println(e);
		}
		finally {
			try { 
				 if(pstmt != null && !pstmt.isClosed()) pstmt.close();
			}
			catch (Exception ex) { 
				System.out.println(ex);
			}
			try { 
				 if(conn != null && !conn.isClosed()) conn.close();
			}
			catch (Exception ex) { 
				System.out.println(ex);
			}
		}
		return retDataObj;
	}
*/
	/**
	  * To get a Map of column names and their corresponding data types
	  * @return The Map
	  */
	public Map<String,String> getColumnTypes() 
	{
		Map<String,String> mp = new HashMap<String, String>();
		mp.put("CNUM","VARCHAR");
		mp.put("JOB_ROLE","VARCHAR");
		mp.put("SKILL_SET","VARCHAR");
		return mp;
	}
	
	/** 
	 * To insert one bid_comment object in the database 
	 * @param emp the bid_comment to be inserted 
	 * @return true if the insertion is successful 
	 */ 
	public boolean insertDataJR(Employee emp ) { 
		Connection conn = null ; 
		PreparedStatement pstmt = null ; 
		try {
			//DBConnection dbConn = new DBConnection(); 
			//conn = dbConn.getConnection(); 
			pstmt = conn.prepareStatement(insert_view_param_jr);
			
			Vector<String> jobRoles = emp.getJobRoles();
			if( jobRoles != null && jobRoles.size() > 0)
			{
				for (String jr : jobRoles)
				{
					pstmt.setString(1, emp.getCnum());
					if (jr.isEmpty())
						continue;
					
					pstmt.setString(2, jr);
					pstmt.execute();
				}
			}		
			
		}
		catch (Exception e) { 
			//SQLExceptionPrint(e); 
			System.out.println(e);
		}
		finally {
			try { 
				 if(pstmt != null && !pstmt.isClosed()) pstmt.close();
			}
			catch (Exception ex) { 
				System.out.println(ex);
			}
			try { 
				 if(conn != null && !conn.isClosed()) conn.close();
			}
			catch (Exception ex) { 
				System.out.println(ex);
			}
		}
		return true;
	}



	/** 
	  * To insert one or more employee jobroles objects in a batch 
	  * @param empV A Collection of employee jobroles objects to be inserted 
	  * @return true if it is successful 
	  */ 
	public boolean batchInsertDataJR(Collection<Employee> empV ) { 
		Connection conn = null ; 
		PreparedStatement pstmt = null ; 
		try { 
			//DBConnection dbConn = new DBConnection(); 
			//conn = dbConn.getConnection(); 
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement( insert_view_param_jr); 
			int totalEvenBatch = empV.size() / 5000;
			
			int remaining = empV.size() - 5000 * totalEvenBatch;
			
			int k = 1;
			int i = 0;
			for (Employee emp : empV) 
			{
				
				
				Vector<String> jobRoles = emp.getJobRoles();
				if( jobRoles != null && jobRoles.size() > 0)
				{
					for (String jr : jobRoles)
					{
						pstmt.setString(1, emp.getCnum());
						if (jr.isEmpty())
							continue;
						
						pstmt.setString(2, jr);
						pstmt.addBatch();
					}
				}						
				i++;
				if (i == 5000 * k) {
					pstmt.executeBatch();
					k++;
				}
			}
			if (remaining > 0) {
				pstmt.executeBatch();
			}
			conn.commit();
		}
		catch (Exception e) { 
			//SQLExceptionPrint(e); 
			System.out.println(e);
		}
		finally {
			try { 
				 if(pstmt != null) 
					 //if(pstmt != null && !pstmt.isClosed())
					 pstmt.close();
			}
			catch (Exception ex) { 
				System.out.println(ex);
			}
			try { 
				 if(conn != null && !conn.isClosed()) conn.close();
			}
			catch (Exception ex) { 
				System.out.println(ex);
			}
		}
		return true;
	}

	/** 
	 * To insert one bid_comment object in the database 
	 * @param emp the bid_comment to be inserted 
	 * @return true if the insertion is successful 
	 */ 
	public boolean insertDataSS(Employee emp ) { 
		Connection conn = null ; 
		PreparedStatement pstmt = null ; 
		try {
			//DBConnection dbConn = new DBConnection(); 
			//conn = dbConn.getConnection(); 
			pstmt = conn.prepareStatement(insert_view_param_ss);
			
			Vector<String> skillSets = emp.getSkillSets();
			if( skillSets != null && skillSets.size() > 0)
			{
				for (String ss : skillSets)
				{
					pstmt.setString(1, emp.getCnum());
					if (ss.isEmpty())
						continue;
					
					pstmt.setString(2, ss);
					pstmt.execute();
				}
			}		
			
		}
		catch (Exception e) { 
			//SQLExceptionPrint(e); 
			System.out.println(e);
		}
		finally {
			try { 
				 if(pstmt != null && !pstmt.isClosed()) pstmt.close();
			}
			catch (Exception ex) { 
				System.out.println(ex);
			}
			try { 
				 if(conn != null && !conn.isClosed()) conn.close();
			}
			catch (Exception ex) { 
				System.out.println(ex);
			}
		}
		return true;
	}



	/** 
	  * To insert one or more employee jobroles objects in a batch 
	  * @param empV A Collection of employee jobroles objects to be inserted 
	  * @return true if it is successful 
	  */ 
	public boolean batchInsertDataSS(Collection<Employee> empV ) { 
		Connection conn = null ; 
		PreparedStatement pstmt = null ; 
		try { 
			//DBConnection dbConn = new DBConnection(); 
			//conn = dbConn.getConnection(); 
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement( insert_view_param_ss); 
			int totalEvenBatch = empV.size() / 5000;
			
			int remaining = empV.size() - 5000 * totalEvenBatch;
			
			int k = 1;
			int i = 0;
			for (Employee emp : empV) 
			{
				
				
				Vector<String> skillSets = emp.getSkillSets();
				if( skillSets != null && skillSets.size() > 0)
				{
					for (String ss : skillSets)
					{
						pstmt.setString(1, emp.getCnum());
						if (ss.isEmpty())
							continue;
						
						pstmt.setString(2, ss);
						pstmt.execute();
					}
				}			
				i++;
				if (i == 5000 * k) {
					pstmt.executeBatch();
					k++;
				}
			}
			if (remaining > 0) {
				pstmt.executeBatch();
			}
			conn.commit();
		}
		catch (Exception e) { 
			//SQLExceptionPrint(e); 
			System.out.println(e);
		}
		finally {
			try { 
				 if(pstmt != null) 
					 //if(pstmt != null && !pstmt.isClosed())
					 pstmt.close();
			}
			catch (Exception ex) { 
				System.out.println(ex);
			}
			try { 
				 if(conn != null && !conn.isClosed()) conn.close();
			}
			catch (Exception ex) { 
				System.out.println(ex);
			}
		}
		return true;
	}

}
