package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAO {
	private static Connection conn = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	
	public static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521/xe";
			conn = DriverManager.getConnection(url, "feedboard", "test1234");
		} catch (Exception e) {
			System.out.println("===> DAO.getConnection()");
			e.printStackTrace();
		}
		
		return conn;
	}
}
