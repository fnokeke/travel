package com.ibm.arc.documentsecurity.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlRootElement;

import com.ibm.json.java.JSONObject;

@XmlRootElement
public class QueryDB {
	private Connection conn;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	private ResultSetMetaData rsmd;

	public QueryDB() {
		connect();
	}

	public void connect() {
		try {
			Context ctx = new InitialContext();
			DataSource dataSource = (DataSource) ctx.lookup("jdbc/db2profile");
			this.conn = dataSource.getConnection();
			System.out.println("Connected");

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public JSONObject run_query(String query) {
		boolean found = false;
		JSONObject result = new JSONObject();

		if (this.conn != null) {

			// query
			try {
				this.pstmt = this.conn.prepareStatement(query);
				this.rset = this.pstmt.executeQuery();
				this.rsmd = this.rset.getMetaData();

				int columnCount = rsmd.getColumnCount();
				String allColumns[] = new String[columnCount];

				for (int i = 0; i < columnCount; i++) {
					String colname = rsmd.getColumnName(i+1);
					allColumns[i] = colname;
				}

				// result
				if (this.rset != null) {
					found = true;

					while (this.rset.next()) {

						String rowNum = Integer.toString(this.rset.getRow());
						JSONObject colValues = new JSONObject();
						
						for (int i = 0; i < columnCount; i++) {
							colValues.put(allColumns[i], this.rset.getString(allColumns[i]));
						}
						result.put(rowNum, colValues);
					}
				}

				if (found == false) {
					System.out.println("No Information Found");
				}
			} catch (SQLException e) {
				System.out.printf("Error running: '%s'", query);
				e.printStackTrace();
			}
		}
		this.close();

		return result;
	}

	public void close() {
		if (this.conn != null) {
			try {
				this.conn.commit();

				this.pstmt.close();
				this.rset.close();
				this.conn.close();

			} catch (Exception e) {
				System.out.println("Problem in closing DB2 connection: "
						+ e.getMessage());
			}
			this.conn = null;
			System.out.println("Connection closed.");
		}
	}

}
