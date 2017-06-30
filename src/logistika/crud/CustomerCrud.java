package logistika.crud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import logistika.DbContext;
import logistika.domain.Customer;
import logistika.finders.CustomerPrinter;
import logistika.manager.EntityService;
import logistika.statistics.CustomerStatistics;

public class CustomerCrud {

	/**
	 * Prints a customer by ID from consol input@throws SQLException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static void showCustomer() throws IOException, SQLException, InstantiationException, IllegalAccessException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter a customer's id: ");
		int customerId = Integer.parseInt(br.readLine());

		Customer customer = Customer.findById(customerId);

		if (customer == null) {
			System.out.println("No such customer exists");
		} else {
			CustomerPrinter.getInstance().print(customer);
		}

	}

	/**
	 * Deletes customer by defined ID from console
	 * @throws SQLException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static void deleteACustomer() throws SQLException, IOException, InstantiationException, IllegalAccessException {
		DbContext.getConnection().setAutoCommit(true);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter a customer's id: ");
		int customerId = Integer.parseInt(br.readLine());

		Customer customer = Customer.findById(customerId);

		if (customer == null) {
			System.out.println("No such customer exists");
		} else {
			customer.delete();
			System.out.println("The customer has been successfully deleted");
		}
	}

	/**
	 * Updates a customer by ID with data from consol input.
	 * @throws SQLException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static void updateCustomer() throws SQLException, IOException, InstantiationException, IllegalAccessException {
		DbContext.getConnection().setAutoCommit(true);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		EntityService em = new EntityService();
		System.out.print("Enter a customer's id: ");
		int customerId = Integer.parseInt(br.readLine());
		Customer customer = em.findById(Customer.SELECT_BY_ID, customerId, Customer.class);

		if (customer == null) {
			System.out.println("No such customer exists");
		} else {
			CustomerPrinter.getInstance().print(customer);

			System.out.print("Enter first name: ");
			customer.setFirstName(br.readLine());
			System.out.print("Enter last name: ");
			customer.setLastName(br.readLine());
			System.out.print("Enter birth number: ");
			customer.setDateOfBirth(Date.valueOf(br.readLine()));
			System.out.print("Enter adress id: ");

			customer.update();

			System.out.println("The customer has been successfully updated");
		}
	}
	
	/**
	 * Prints all customers
	 */
	public static void listAllCustomers() {
		EntityService em = new EntityService();
		try {
			List<Customer> list = em.findAll(Customer.SELECT_ALL_CUSTOMERS, Customer.class);
			for (Customer c : list) {
				c.print();
			}
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			System.out.println("Problem occured" + e.getMessage());
		}
	}



	

}
