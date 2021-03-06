import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionExample {

	public static void main(String[] args) {
		String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
		String url = "jdbc:db2://localhost:50000/travel";
		String user = "db2inst1";
		String password = "123456";

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		boolean found = false;

		Connection conn = null;
		try {
			// Load class into memory
			Class.forName(jdbcClassName);
			// Establish connection
			conn = DriverManager.getConnection(url, user, password);

			if (conn != null) {
				System.out.println("**Connected successfully.**\n\n");

				// query
				pstmt = conn
						.prepareStatement("Select * from PROFILE.employee limit 5");
				rset = pstmt.executeQuery();

				// result
				if (rset != null) {
					System.out.printf("%-20s %-20s %-20s", 
							"firstname", 
							"lastname", 
							"email\n");
					System.out.println();
					while (rset.next()) {
						found = true;
						System.out.printf("%-20s %-20s %-20s\n",
								rset.getString("firstname"),
								rset.getString("lastname"),
								rset.getString("email"));

					}
				}
				if (found == false) {
					System.out.println("No Information Found");
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("\n\n**Closing connection.**");
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}