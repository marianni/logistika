package logistika.finders;

import logistika.domain.Address;

public class AddressPrinter {

	private static final AddressPrinter INSTANCE = new AddressPrinter();

	public static AddressPrinter getInstance() {
		return INSTANCE;
	}

	private AddressPrinter() {
	}

	public void print(Address address) {
		if (address == null) {
			throw new NullPointerException("country cannot be null");
		}

		System.out.print("id :          ");
		System.out.println(address.getId());
		System.out.print("Street name:   ");
		System.out.println(address.getStreetName());
		System.out.print("Street number:    ");
		System.out.println(address.getStreetNumber());
		System.out.print("City: ");
		System.out.println(address.getCity());
		System.out.print("Post code: ");
		System.out.println(address.getPostCode());
		System.out.print("Country id: ");
		System.out.println(address.getCountryId());
		System.out.println();
	}
}
