package logistika.statistics;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logistika.DbContext;
import logistika.domain.Customer;
import logistika.manager.Entity;
import logistika.manager.EntityService;

public class CustomerStatistics {
	
	// @formatter:off
	public static final String QUERY = "select count(*) AS count," 
								+ "(select type from items where id=item_id) AS item "
								+ ","
								+ "("
								+ "SELECT cust.first_name "
								+ "FROM customers AS cust "
								+ "WHERE cust.id IN " 
								+ "	(SELECT it.customer_id " 
								+ "	 FROM items it "
								+ "	 WHERE itsh.item_id = it.id" 
								+ "	) " 
								+ ") AS first_name "
								+ ", "
								+ "( "
								+ "SELECT cust.last_name "
								+ "FROM customers AS cust "
								+ "WHERE cust.id IN " 
								+ "	(SELECT it.customer_id " 
								+ "	 FROM items it "
								+ "	 WHERE itsh.item_id = it.id "
								+ "	) " 
								+ ") AS last_name "
								+ "from item_shipments itsh "
								+ "GROUP BY item_id LIMIT 3 ";
	// @formatter:on
	
	private Integer count;
	private String item;
	private String firstName;
	private String lastName;
	
	public static void listCustomersWithTopShippedItems() {
		try {
			List<CustomerStatistics> list = CustomerStatistics.getStatistics();
			for (CustomerStatistics cs : list) {
				String format = String.format("Pocet itemov v objednavke: %s, %s, %s, %s",
						String.valueOf(cs.getCount()), cs.getItem(), cs.getFirstName(), cs.getLastName());
				System.out.println(format);
			}
		} catch (SQLException e) {
			System.out.println("Problem occured" + e.getMessage());
		}
	}

	
	
	private static CustomerStatistics createFromResult(ResultSet r) throws SQLException {
		CustomerStatistics cs = new CustomerStatistics();

		cs.setCount(r.getInt("count"));
		cs.setItem(r.getString("item"));
		cs.setFirstName(r.getString("first_name"));
		cs.setLastName(r.getString("last_name"));
		return cs;
	}
	
	private static List<CustomerStatistics> getStatistics() throws SQLException{
		try (PreparedStatement s = DbContext.getConnection().prepareStatement(
				CustomerStatistics.QUERY)) {
			ResultSet r = s.executeQuery();
			List<CustomerStatistics> list = new ArrayList<>();
			while (r.next()) {
				CustomerStatistics cs = CustomerStatistics.createFromResult(r);					
				list.add(cs);
			}
			return list;
		}
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	

}
