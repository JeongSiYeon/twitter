package testForGuiui;

import java.sql.*;

public class JDBC { //for profile
	
	static Connection conn = null;
	 
	public static Connection connection() {	
		try {
			final String url = "jdbc:mysql://localhost/twittwe_db";
		    final String user = "root";
			final String passwd = "anselmochung24"; 
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, user, passwd);
		
		} catch (ClassNotFoundException cvfe) {
			System.out.println("DB Driver Loading Fail..");
		} catch (SQLException sqle) {
			System.out.println("DB ���ӽ���: " + sqle.toString());
		} catch (Exception e) {
			System.out.println("Unkown error");
			e.printStackTrace();
		}
		
		return conn; 
	}
	
	
}