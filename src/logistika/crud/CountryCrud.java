package logistika.crud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import logistika.DbContext;
import logistika.domain.Country;
import logistika.finders.CountryPrinter;
import logistika.manager.EntityService;

public class CountryCrud {

	public static Country addCountry() throws IOException, SQLException, InstantiationException, IllegalAccessException {
		DbContext.getConnection().setAutoCommit(true);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		Country country = new Country();

		System.out.print("Country creation: ");
		System.out.print("Entername: ");
		country.setName(br.readLine());
		System.out.print("Enter shortcut of country: ");
		country.setShortcutOfCountry(br.readLine());
		System.out.print("Enter continent: ");
		country.setContinent(br.readLine());

		country.insert();

		System.out.println("The country has been succesfully added");
		System.out.print("The country id is: ");
		System.out.println(country.getId());
		return country;
	}
	
	public static void deleteACountry() throws SQLException, IOException, InstantiationException, IllegalAccessException {
		DbContext.getConnection().setAutoCommit(true);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter a country's id: ");
		int countryId = Integer.parseInt(br.readLine());

		Country country = Country.findById(countryId);

		if (country == null) {
			System.out.println("No such country exists");
		} else {
			country.delete();
			System.out.println("The country has been successfully deleted");
		}
	}

	
	
	
	public void updateCountry() throws SQLException, IOException, InstantiationException, IllegalAccessException {
		DbContext.getConnection().setAutoCommit(true);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		EntityService em = new EntityService();
		System.out.print("Enter a country's id: ");
		int countryId = Integer.parseInt(br.readLine());
		Country country = em.findById(Country.SELECT_BY_ID, countryId, Country.class);

		if (country == null) {
			System.out.println("No such customer exists");
		} else {
			CountryPrinter.getInstance().print(country);

			System.out.print("Enter name: ");
			country.setName(br.readLine());
			System.out.print("Enter shortcut of country: ");
			country.setShortcutOfCountry(br.readLine());
			System.out.print("Enter continent: ");
			country.setContinent(br.readLine());

			country.update();

			System.out.println("The country has been successfully updated");
		}
	}
	
	/**
	 * prints all countries from db to console
	 */
	public static void listAllCountries() {
		EntityService em = new EntityService();
		try {
			List<Country> list = em.findAll(Country.SELECT_ALL_COUNTRIES, Country.class);
			for (Country c : list) {
				c.print();
			}
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			System.out.println("Problem occured" + e.getMessage());
		}
	}

	/**
	 * prints a Country by ID from consol input
	 * @throws IOException
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static void showACountry() throws IOException, SQLException, InstantiationException, IllegalAccessException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter a country id: ");
		int countryId = Integer.valueOf(br.readLine());

		Country country = Country.findById(countryId);
		if (country == null) {
			System.out.println("No such country exists");
		} else {
			CountryPrinter.getInstance().print(country);
		}

	}

	
}
