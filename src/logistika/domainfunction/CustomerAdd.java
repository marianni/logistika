package logistika.domainfunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.SQLException;

import logistika.DbContext;
import logistika.domain.Address;
import logistika.domain.Country;
import logistika.domain.Customer;
import logistika.finders.CountryService;

public class CustomerAdd {

	// =============================================================================================
		public static Customer add() throws IOException, SQLException, InstantiationException, IllegalAccessException {

			try {
				DbContext.getConnection().setAutoCommit(false);

				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

				Customer customer = new Customer();

				System.out.print("Enter first name: ");
				customer.setFirstName(br.readLine());
				System.out.print("Enter last name: ");
				customer.setLastName(br.readLine());
				System.out.print("Enter birth date (example 1992-07-21): ");
				customer.setDateOfBirth(Date.valueOf(br.readLine()));

				System.out.print("Enter address country code: ");
				String countryCode = br.readLine();

				Address address = new Address();

				CountryService cs = new CountryService();
				Country country = cs.selectByCountryCode(countryCode);
				if (country == null) {
					System.out.println("Country with this country code does not exist!");
					return null;
				}
				address.setCountryId(country.getId());

				System.out.print("Enter streeName: ");
				address.setStreetName(br.readLine());

				System.out.print("Enter streetNumber: ");
				address.setStreetNumber(Integer.valueOf(br.readLine()));

				System.out.print("Enter address city: ");
				address.setCity(br.readLine());

				System.out.print("Enter address post code: ");
				address.setPostCode(br.readLine());

				address.insert();

				customer.setAddressId(address.getId());
				customer.insert();

				System.out.println("The customer and his address has been sucessfully added");
				System.out.print("The customer's id is: ");
				System.out.println(customer.getId());
				return customer;
			} catch (SQLException e) {
				DbContext.rollBackWhenException(e);
				return null;

			}
		}
}
