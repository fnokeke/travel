package com.ibm.almaden.bluepages.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

//import com.ibm.data.connection.DBConnection;

public class EmployeeDAO {
	
	public String data_view_param = 
			"SELECT * FROM BLUEPAGE.bp_data_daiy";
	public String data_view_param_by_key = null; // No primary key found hence this functionality not supported 
	public String insert_view_param = 
			"INSERT INTO BLUEPAGE.BP_CURRENT(CNUM, FULL_NAME, EMAILID, DEPT, DIV, ISMANAGER, MGRCNUM, JOB_ROLE, SKILL_SET, CREATE_DATE, IS_ACTIVE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, current date,1 )"; 
	public String update_all_param = null; // No primary key found hence update functionality not supported
	public String delete_all_param = null; // No primary key found hence delete functionality not supported
	public String count_all_param = 
			"SELECT COUNT(*) FROM BLUEPAGE.BP_CURRENT bp"; 

	
	/**
	  * To generate the bid_comment object representation of a db record
	  * @param rs The ResultSet returned from a query
	  * @return the bid_comment object
	  * @throws SQLException
	  */
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
	/** 
	  * To get all bid_comment objects 
	  */ 
	public Vector<Employee> getAllData() { 
		Connection conn = null ; 
		PreparedStatement pstmt = null ; 
		ResultSet rs = null ; 
		Vector<Employee> retDataObj = new Vector<Employee>() ; 
		try 
		{ 
			//DBConnection dbConn = new DBConnection(); 
			//conn = dbConn.getConnection(); 
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

	/**
	  * To get a Map of column names and their corresponding data types
	  * @return The Map
	  */
	public Map<String,String> getColumnTypes() 
	{
		Map<String,String> mp = new HashMap<String, String>();
		mp.put("CNUM","VARCHAR");
		mp.put("FULL_NAME","VARCHAR");
		mp.put("EMAILID","VARCHAR");
		mp.put("DEPT","VARCHAR");
		mp.put("DIV","VARCHAR");
		mp.put("ISMANAGER","VARCHAR");
		mp.put("MGRCNUM","VARCHAR");
		mp.put("JOB_ROLE","VARCHAR");
		mp.put("SKILL_SET","VARCHAR");
		return mp;
	}
	
	/** 
	 * To insert one bid_comment object in the database 
	 * @param emp the bid_comment to be inserted 
	 * @return true if the insertion is successful 
	 */ 
	public boolean insertData(Employee emp ) { 
		Connection conn = null ; 
		PreparedStatement pstmt = null ; 
		try {
			//DBConnection dbConn = new DBConnection(); 
			//conn = dbConn.getConnection(); 
			pstmt = conn.prepareStatement(insert_view_param);
			
			pstmt.setString(1, emp.getCnum()); 
			pstmt.setString(2, emp.getEmployeeName());
			pstmt.setString(3, emp.getEmailID());
			pstmt.setString(4, emp.getEmployeeDept());
			pstmt.setString(5, emp.getEmployeeDiv());
			pstmt.setString(6, emp.getIsManager());
			pstmt.setString(7, emp.getMgrCnum());
			
			Vector<String> jobRoles = emp.getJobRoles();			
			if( jobRoles.size() > 0)
				pstmt.setString(8, jobRoles.elementAt(0).toString());
			else
				pstmt.setString(8, new String(""));
			
			Vector<String> ss = emp.getSkillSets();			
			if( ss.size() > 0)
				pstmt.setString(9, ss.elementAt(0).toString());
			else
				pstmt.setString(9, new String(""));
	
			pstmt.execute(); 
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
	  * To insert one or more bid_comment objects in a batch 
	  * @param empV A Collection of bid_comment objects to be inserted 
	  * @return true if it is successful 
	  */ 
	public boolean batchInsertData(Collection<Employee> empV ) { 
		Connection conn = null ; 
		PreparedStatement pstmt = null ; 
		try { 
			//DBConnection dbConn = new DBConnection(); 
			//conn = dbConn.getConnection(); 
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement( insert_view_param); 
			int totalEvenBatch = empV.size() / 5000;
			int remaining = empV.size() - 5000 * totalEvenBatch;
			int k = 1;
			int i = 0;
			for (Employee emp : empV) {
				System.out.println("emp = " + emp.toString());
				pstmt.setString(1, emp.getCnum()); 
				pstmt.setString(2, emp.getEmployeeName());
				pstmt.setString(3, emp.getEmailID());
				pstmt.setString(4, emp.getEmployeeDept());
				pstmt.setString(5, emp.getEmployeeDiv());
				pstmt.setString(6, emp.getIsManager());
				pstmt.setString(7, emp.getMgrCnum());
				
				Vector<String> jobRoles = emp.getJobRoles();			
				if( jobRoles.size() > 0)
					pstmt.setString(8, jobRoles.elementAt(0).toString());
				else
					pstmt.setString(8, new String(""));
				
				Vector<String> ss = emp.getSkillSets();			
				if( ss.size() > 0)
					pstmt.setString(9, ss.elementAt(0).toString());
				else
					pstmt.setString(9, new String(""));
				pstmt.addBatch();
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
			
			e.printStackTrace();
			
		}
		finally {
			try { 
				 if(pstmt != null && !pstmt.isClosed()) 
					 pstmt.close();
			}
			catch (Exception ex) { 
				System.out.println(ex);
				ex.printStackTrace();
			}
			try { 
				 if(conn != null && !conn.isClosed()) conn.close();
			}
			catch (Exception ex) { 
				System.out.println(ex);
				ex.printStackTrace();
			}
		}
		return true;
	}


}
