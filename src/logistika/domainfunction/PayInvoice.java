package logistika.domainfunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import logistika.DbContext;
import logistika.domain.Invoice;
import logistika.domain.Payment;
import logistika.manager.EntityService;

public class PayInvoice {

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Platba faktury a paymentov

	public static Integer pay()
			throws NumberFormatException, IOException, InstantiationException, IllegalAccessException, SQLException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter invoice id: ");
		int invoiceId = Integer.parseInt(br.readLine());

		try {
			DbContext.getConnection().setAutoCommit(false);
			Invoice invoice = EntityService.em.findById(Invoice.SELECT_BY_ID, invoiceId, Invoice.class);
			if (invoice == null) {
				throw new SQLException("Invoice was not found!");
			}

			System.out.println("Enter number of payments you would like to pay: ");
			int payments = Integer.parseInt(br.readLine());

			List<Payment> notPaidPayments = EntityService.em.searchQuery(Payment.SELECT_FIRST_N_NOT_PAID, Payment.class, invoiceId,
					payments);
			for (Payment p : notPaidPayments) {
				p.setAmount(0);
				p.update();
			}
			List<Payment> allPayments = EntityService.em.searchQuery(Payment.SELECT_ALL_PAYMENTS, Payment.class, invoiceId);
			boolean allPaymentsAreZero = true;
			for (Payment p : allPayments) {
				if (p.getAmount() != 0) {
					allPaymentsAreZero = true;
				}
			}

			if (allPaymentsAreZero) {
				for (Payment p : allPayments) {
					p.delete();
				}
			}

			DbContext.getConnection().commit();
			System.out.println("Invoices has been successfully paid");
			return invoiceId;
		} catch (SQLException e) {
			DbContext.rollBackWhenException(e);
			return null;
		}
	}

}
