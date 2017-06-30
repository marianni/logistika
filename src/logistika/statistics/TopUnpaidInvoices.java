package logistika.statistics;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logistika.DbContext;

public class TopUnpaidInvoices {
	public static String QUERY = "select inv.id, inv.issue_date, inv.due_date, pmnts.unpaid_amount AS unpaid_amount "
			+ "from invoices inv JOIN " + "( " + "select invoice_id AS inv_id, sum(amount) AS unpaid_amount "
			+ "from public.payments " + "where amount > 0 " + "GROUP BY invoice_id "
			+ " ) as pmnts ON pmnts.inv_id = inv.id " + "ORDER BY unpaid_amount DESC";

	private Integer id;
	private Date issueDate;
	private Date dueDate;
	private Integer unpaidAmount;

	public static void list() {
		try {
			List<TopUnpaidInvoices> list = TopUnpaidInvoices.getStatistics();
			for (TopUnpaidInvoices cs : list) {
				String format = String.format("%s, %s, %s, %s", String.valueOf(cs.getId()),
						cs.getIssueDate().toString(), cs.getDueDate().toString(), cs.getUnpaidAmount().toString());
				System.out.println(format);
			}
		} catch (SQLException e) {
			System.out.println("Problem occured" + e.getMessage());
		}
	}

	private static TopUnpaidInvoices createFromResult(ResultSet r) throws SQLException {
		TopUnpaidInvoices cs = new TopUnpaidInvoices();

		cs.setId(r.getInt("id"));
		cs.setIssueDate(r.getDate("issue_date"));
		cs.setDueDate(r.getDate("due_date"));
		cs.setUnpaidAmount(r.getInt("unpaid_amount"));
		return cs;
	}

	private static List<TopUnpaidInvoices> getStatistics() throws SQLException {
		try (PreparedStatement s = DbContext.getConnection().prepareStatement(TopUnpaidInvoices.QUERY)) {
			ResultSet r = s.executeQuery();
			List<TopUnpaidInvoices> list = new ArrayList<>();
			while (r.next()) {
				TopUnpaidInvoices cs = TopUnpaidInvoices.createFromResult(r);
				list.add(cs);
			}
			return list;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Integer getUnpaidAmount() {
		return unpaidAmount;
	}

	public void setUnpaidAmount(Integer unpaidAmount) {
		this.unpaidAmount = unpaidAmount;
	}

}
