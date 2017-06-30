package logistika.domainfunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import logistika.DbContext;
import logistika.domain.Customer;
import logistika.domain.Invoice;
import logistika.domain.Item;
import logistika.domain.Order;
import logistika.domain.OrdersItems;
import logistika.domain.Payment;

public class NewOrder {

	public static void newOrder()
			throws IOException, ParseException, SQLException, InstantiationException, IllegalAccessException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		Item item = new Item();
		Order order = new Order();
		Invoice invoice = new Invoice();
		Payment payment = new Payment();

		System.out.print("Enter type of item: ");
		item.setType(br.readLine());

		System.out.println("Enter price of item: ");
		item.setPrice(Integer.valueOf(br.readLine()));

		System.out.println("Enter description: ");
		item.setDescription(br.readLine());

		System.out.println("Enter quantity: ");
		order.setQuantity(Long.valueOf(br.readLine()));

		System.out.println("Enter date of order: ");
		String issueD = br.readLine();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateUtil = f.parse(issueD);

		Date issueDate = Date.valueOf(issueD);
		order.setDateOfOrder(issueDate);
		invoice.setIssueDate(issueDate);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		java.util.Date d = format.parse(dateAddition(issueD));
		invoice.setDueDate(new Date(d.toInstant().toEpochMilli()));

		System.out.println("Enter number of payments you wished: ");
		Integer paym = Integer.valueOf(br.readLine());
		invoice.setPayments(paym);

		System.out.println("Enter your customer number: ");
		Integer customerId = Integer.valueOf(br.readLine());

		try {
			DbContext.getConnection().setAutoCommit(false);
			Customer customer = Customer.findById(customerId);
			if (customer == null) {
				System.out.println("Unknown customer number,please register now");
				customer = CustomerAdd.add();
				if (customer == null) {
					return;
				}
			}

			Order ordCustomer = Order.findByCustomerId(customerId);

			if (ordCustomer != null) {
				if (customer.getId() == ordCustomer.getCustomerId()) {
					System.out.println("Dodger! Order denied!");
					return;
				}
			}

			item.setCustomerId(customer.getId());
			order.setCustomerId(customer.getId());
			order.insert();
			invoice.setOrderId(order.getId());

			item.insert();
			OrdersItems ordersItems = new OrdersItems(order.getId(), item.getId());
			ordersItems.insert();
			invoice.insert();

			for (int i = 0; i < paym; i++) {
				payment.setPaymentTime(new Timestamp(dateUtil.toInstant().toEpochMilli()));
				payment.setAmount((item.getPrice()) / paym);
				payment.setInvoiceId(invoice.getId());
				payment.insert();
			}
			DbContext.getConnection().commit();
			System.out.println("The order was made succesfully");
			System.out.print("The ordes's id is: ");
			System.out.println(order.getId());
		} catch (SQLException e) {
			DbContext.rollBackWhenException(e);
		}
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
