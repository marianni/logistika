package logistika.domainfunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import logistika.DbContext;
import logistika.domain.AddressShipment;
import logistika.domain.Customer;
import logistika.domain.Invoice;
import logistika.domain.ItemsShipments;
import logistika.domain.Order;
import logistika.domain.OrdersItems;
import logistika.domain.Shipment;
import logistika.domain.Transport;
import logistika.manager.EntityService;

public class PrepareShipment {

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Zaradenie itemu do shipmentu

	public static Transport prepare() throws NumberFormatException, InstantiationException,
			IllegalAccessException, IOException, SQLException, ParseException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		DbContext.getConnection().setAutoCommit(false);
		try {
			Shipment shipment = new Shipment();
			Integer invoiceId = PayInvoice.pay();
			Invoice invoice = Invoice.findById(invoiceId);
			if (invoiceId == null) {
				System.out.println("No such invoice exist");
				return null;
			}
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date dateUtil = f.parse(String.valueOf(invoice.getDueDate()));
			shipment.setDateAndTime(new Timestamp(dateUtil.toInstant().toEpochMilli()));
			System.out.println("Enter type of shipment:(air,road,sea,train) ");
			shipment.setType(br.readLine());
			shipment.insert();

			if (invoice.getOrderId() == null) {
				String text = String.format("Invoice with id %s has a NULL orderID", invoice.getId());
				throw new SQLException(text);
			}

			OrdersItems orderItem = EntityService.em.searchQueryFindFirst(OrdersItems.SELECT_BY_ORDER_ID,
					OrdersItems.class, invoice.getOrderId());
			ItemsShipments is = new ItemsShipments(orderItem.getItemId(), shipment.getId());
			is.insert();

			Order order = EntityService.em.searchQueryFindFirst(Order.SELECT_BY_ORDER_ID, Order.class,
					invoice.getOrderId());
			Customer customer = EntityService.em.searchQueryFindFirst(Customer.SELECT_BY_ID, Customer.class,
					order.getCustomerId());
			AddressShipment as = new AddressShipment(customer.getAddressId(), shipment.getId());
			as.insert();

			Transport transport = new Transport();
			transport.setType(shipment.getType());
			transport.setShipmentId(shipment.getId());
			transport.insert();

			DbContext.getConnection().commit();
			System.out.println("Shipment has been successfully prepared");
			System.out.println("Shipment id is : ");
			transport.getShipmentId();
			return transport;

		} catch (SQLException e) {
			DbContext.rollBackWhenException(e);
			return null;
		}
	}

}
