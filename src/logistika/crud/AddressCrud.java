package logistika.crud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import logistika.DbContext;
import logistika.domain.Address;
import logistika.finders.AddressPrinter;
import logistika.manager.EntityService;

public class AddressCrud {


	/**
	 * Finds address by id from console input
	 */
	public static void showAddress() throws IOException, SQLException, InstantiationException, IllegalAccessException {
		DbContext.getConnection().setAutoCommit(true);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter a address id: ");
		int addressId = Integer.valueOf(br.readLine());

		Address address = Address.findById(addressId);
		if (address == null) {
			System.out.println("No such country exists");
		} else {
			AddressPrinter.getInstance().print(address);
		}

	}

	
	/**
//	 * Deletes address by ID from console input
	 */
	public static void deleteAddress() throws SQLException, IOException, InstantiationException, IllegalAccessException {
		DbContext.getConnection().setAutoCommit(true);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter a address's id: ");
		int addressId = Integer.parseInt(br.readLine());

		Address address = Address.findById(addressId);

		if (address == null) {
			System.out.println("No such address exists");
		} else {
			address.delete();
			System.out.println("The address has been successfully deleted");
		}
	}

	/**
	 * Updates address by ID with data from consol input
	 */
	public static void updateAdress() throws SQLException, IOException, InstantiationException, IllegalAccessException {
		DbContext.getConnection().setAutoCommit(true);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		EntityService em = new EntityService();
		System.out.print("Enter a address's id: ");
		int adressId = Integer.parseInt(br.readLine());
		Address address = em.findById(Address.SELECT_BY_ID, adressId, Address.class);

		if (address == null) {
			System.out.println("No such address exists");
		} else {
			AddressPrinter.getInstance().print(address);

			System.out.print("Enter street name: ");
			address.setStreetName(br.readLine());
			System.out.print("Enter street number: ");
			address.setStreetNumber(Integer.valueOf(br.readLine()));
			System.out.print("Enter city: ");
			address.setCity(br.readLine());
			System.out.print("Enter postcode: ");
			address.setPostCode(br.readLine());
			System.out.println("Enter country id: ");
			address.setCountryId(Integer.valueOf(br.readLine()));

			address.update();

			System.out.println("The country has been successfully updated");
		}
	}

	
	/**
	 * Adds a new Address with data from consol input
	 */
	public static Address addAddress() throws IOException, SQLException, InstantiationException, IllegalAccessException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		DbContext.getConnection().setAutoCommit(true);
		Address address = new Address();

		System.out.print("Enter street name: ");
		address.setStreetName(br.readLine());
		System.out.println("Enter street number: ");
		address.setStreetNumber(Integer.valueOf(br.readLine()));
		System.out.println("Enter city: ");
		address.setCity(br.readLine());
		System.out.println("Enter post code: ");
		address.setPostCode(br.readLine());

		address.setCountryId(CountryCrud.addCountry().getId());

		address.insert();
		System.out.println("The address has been succesfully added");
		System.out.print("The address id is: ");
		System.out.println(address.getId());
		return address;
	}
}
