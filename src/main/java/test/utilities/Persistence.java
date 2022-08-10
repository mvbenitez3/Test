package test.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Persistence {
	private String url = "jdbc:mysql://localhost:3306/test_hitch";
	private String user = "root";
	private String password = "12345";

	/**
	 * Metodo que consiste en hacer la conexion a la base de datos 
	 * @return una conexion 
	 */
	public Connection getConnection() {
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(url, user, password);
			
			System.out.println("Connection succesfully");
			
		} catch (SQLException e) {
			System.out.println("Error connection database" + e);
		}

		return connection;
	}
}
