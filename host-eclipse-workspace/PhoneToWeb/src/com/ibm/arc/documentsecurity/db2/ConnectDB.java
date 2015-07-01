package com.ibm.arc.documentsecurity.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlRootElement;

import com.ibm.json.java.JSONObject;

@XmlRootElement
public class ConnectDB {
	private Connection conn;

	public ConnectDB() {
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
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		boolean found = false;
		JSONObject result = new JSONObject();

		if (this.conn != null) {

			// query
			try {
				pstmt = this.conn.prepareStatement(query);
				rset = pstmt.executeQuery();

				// result
				if (rset != null) {
					found = true;

					while (rset.next()) {
						
						String key = Integer.toString(rset.getRow());
						String value = rset.getString("firstname") + ","
								+ rset.getString("lastname") + ","
								+ rset.getString("email");
						
						result.put(key, value);
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

		return result;
	}

	public void close() throws Exception {
		if (this.conn != null) {
			try {
				this.conn.commit();
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