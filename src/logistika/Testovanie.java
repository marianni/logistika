package logistika;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import logistika.domain.Customer;
import logistika.domain.Item;
import logistika.domain.Order;
import logistika.ui.UI;

public class Testovanie {

	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ParseException {
		String url = "jdbc:postgresql://localhost:5432/logistika";
		String username = "postgres";
		String password = "134tomas";

		Properties props = new Properties();
		props.setProperty("user", username);
		props.setProperty("password", password);

		try (Connection connection = DriverManager.getConnection(url, props)) {
			DbContext.setConnection(connection);

			test();

		} finally {
			DbContext.clear();
		}
	}

	public static void test() throws InstantiationException, IllegalAccessException, SQLException, ParseException {
		
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d = f.parse("2017-09-05");
		new Timestamp(d.toInstant().toEpochMilli());
		
		java.sql.Date issueDate = java.sql.Date.valueOf("2017-09-05");		
		
	}
	
	public static String dateAddition(String date) throws ParseException {
		Random rnd = new Random();
		SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateTimeF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(dateF.parse(date));
		c.add(Calendar.DATE, rnd.nextInt(30));
		date = dateTimeF.format(c.getTime());
		return date;
	}
}
