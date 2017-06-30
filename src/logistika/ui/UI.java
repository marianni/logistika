package logistika.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import logistika.crud.AddressCrud;
import logistika.crud.CountryCrud;
import logistika.crud.CustomerCrud;
import logistika.domainfunction.CustomerAdd;
import logistika.domainfunction.NewOrder;
import logistika.domainfunction.PayInvoice;
import logistika.domainfunction.PrepareShipment;
import logistika.domainfunction.ShipmentDestinationChange;
import logistika.statistics.CustomerStatistics;
import logistika.statistics.TopUnpaidInvoices;

public class UI {

	private boolean exit;

	public void print() {
		System.out.println("*********************************************");
		System.out.println("* COUNTRY                					*");
		System.out.println("* 1. Show Country by ID                		*");
		System.out.println("* 2. Add Country                       		*");
		System.out.println("* 3. Delete Country by ID             	   	*");
		System.out.println("* 4. List all Countries                   	*");
		
		System.out.println("* ADDRESS                					*");
		System.out.println("* 5. Show Address by ID          	   		*");
		System.out.println("* 6. Add Address							*");
		System.out.println("* 7. Update Address by ID                 	*");
		System.out.println("* 8. Delete Address by ID      				*");
		
		System.out.println("* CUSTOMER                					*");
		System.out.println("* 9. Show Customer by ID                    *");
		System.out.println("* 10.List all Customers                     *");
		System.out.println("* 11.Add Customer                           *");
		System.out.println("* 12.Update Customer                        *");
		System.out.println("* 13.Delete Customer                        *");
		System.out.println("* 14.List Customers with top shipped items  *");		
		
		System.out.println("* ORDER                						*");
		System.out.println("* 15.Place Order                       		*");
		
		System.out.println("* INVOICE                					*");
		System.out.println("* 16.Pay invoice                       		*");
		
		System.out.println("* SHIPMENT                       			*");
		System.out.println("* 17.Prepare shipment                       *");
		System.out.println("* 18.Change shipment destination warehouse 	*");
		System.out.println("* 19.Statistics Top unpaid invoices 	*");
		System.out.println("*********************************************");
	}

	public void run() throws IOException {
		exit = false;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (exit == false) {
			System.out.println();
			print();
			System.out.println();

			String line = br.readLine();
			if (line == null) {
				return;
			}

			System.out.println();

			handle(line);
		}
	}

	public void handle(String option) {
		try {
			switch (option) {

			// COUNTRY
			case "1":
				CountryCrud.showACountry();
				break;
			case "2":
				CountryCrud.addCountry();
				break;
			case "3":
				CountryCrud.deleteACountry();
				break;
			case "4":
				CountryCrud.listAllCountries();
				break;

			// ADDRESS
			case "5":
				AddressCrud.showAddress();
				break;
			case "6":
				AddressCrud.addAddress();
				break;
			case "7":
				AddressCrud.updateAdress();
				break;
			case "8":
				AddressCrud.deleteAddress();
				break;

			// CUSTOMER
			case "9":
				CustomerCrud.showCustomer();
				break;
			case "10":
				CustomerCrud.listAllCustomers();
				break;
			case "11":
				CustomerAdd.add();
				break;
			case "12":
				CustomerCrud.updateCustomer();
				break;
			case "13":
				CustomerCrud.deleteACustomer();
				break;
			case "14":
				CustomerStatistics.listCustomersWithTopShippedItems();
				break;

			// ORDER
			case "15":
				NewOrder.newOrder();
				break;

			// INVOICE
			case "16":
				PayInvoice.pay();
				break;

			// SHIPMENT
			case "17":
				PrepareShipment.prepare();
				break;
			case "18":
				ShipmentDestinationChange.change();
				break;			
			case "19":
				TopUnpaidInvoices.list();
				break;
			default:
				System.out.println("Unknown option");
				break;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
