package logistika;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import logistika.ui.UI;

public class Main {

	public static void main(String[] args) throws SQLException, IOException, InstantiationException, IllegalAccessException {

		String url = "jdbc:postgresql://localhost:5432/logistika";
		String username = "postgres";
		String password = "134tomas";

		Properties props = new Properties();
		props.setProperty("user", username);
		props.setProperty("password", password);

		try (Connection connection = DriverManager.getConnection(url, props)) {
			DbContext.setConnection(connection);

			 UI ui = new UI();
			 ui.run();
//			test();

		} finally {
			DbContext.clear();
		}
	}
	
	
	

	

//	public static void test() throws InstantiationException,
//			IllegalAccessException, SQLException {
//		CountryService cs = new CountryService();
//		Country country = cs.selectByCountryCode("SK", new EntityService());
//		int x = 10;
//	}
}
