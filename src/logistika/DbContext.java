package logistika;

import java.sql.Connection;
import java.sql.SQLException;

public class DbContext {

	private static Connection connection;

	public static void setConnection(Connection connection) {
		if (connection == null) {
			throw new IllegalArgumentException("connection cannot be null");
		}
		DbContext.connection = connection;
	}

	public static Connection getConnection() {
		if (connection == null) {
			throw new IllegalStateException("connection must be set before calling this method");
		}
		return connection;
	}

	public static void clear() {
		connection = null;
	}

	public static void rollBackWhenException(Exception e) throws SQLException {
		String exceptionText = String.format("ROLLBACK - EXCEPTION OCCURED : %s", e.getMessage());
		System.out.println(exceptionText);
		DbContext.getConnection().rollback();
	}

}
