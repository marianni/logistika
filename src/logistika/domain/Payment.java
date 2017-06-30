package logistika.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import logistika.manager.Entity;
import logistika.manager.EntityService;

public class Payment extends EntityWithId {
	public static String SELECT_ALL_PAYMENTS = "SELECT * FROM payments WHERE invoice_id = ?";
	public static String SELECT_FIRST_N_NOT_PAID = "SELECT * FROM payments WHERE invoice_id = ? AND amount != 0 ORDER BY payment_time ASC LIMIT ?";
	public static String INSERT = "INSERT INTO payments (payment_time, amount,invoice_id) VALUES (?,?,?)";
	public static String UPDATE = "UPDATE payments SET payment_time = ?, amount = ? , invoice_id = ? WHERE id = ?";
	public static String DELETE = "DELETE FROM payments WHERE id = ?";
	
	private Timestamp payment_time;
	private Integer amount;
	private Integer invoiceId;
	
	public Payment() {
		super();
	}

	public Timestamp getPaymentTime() {
		return payment_time;
	}

	public void setPaymentTime(Timestamp payment_time) {
		this.payment_time = payment_time;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Override
	public void insertMapping(PreparedStatement s) throws SQLException {
		s.setTimestamp(1, payment_time);
		s.setInt(2,amount);
		s.setInt(3, invoiceId);
		
	}

	@Override
	public void updateMapping(PreparedStatement s) throws SQLException {
		s.setTimestamp(1, payment_time);
		s.setInt(2,amount);
		s.setInt(3, invoiceId);
		s.setInt(4, getId());
		
	}

	@Override
	public void deleteMapping(PreparedStatement s) throws SQLException {
		s.setInt(1, getId());
		
	}

	@Override
	public Entity createFromResult(ResultSet r, Entity c) throws SQLException {
		Payment a =(Payment) c;
		
		a.setId(r.getInt("id"));
		a.setPaymentTime(r.getTimestamp("payment_time"));
		a.setAmount(r.getInt("amount"));
		a.setInvoiceId(r.getInt("invoice_id"));
		
		return a;
	}

	

	@Override
	public String getInsertSQL() {
		return INSERT;
	}

	@Override
	public String getUpdateSQL() {
		return UPDATE;
	}

	@Override
	public String getDeleteSQL() {
		return DELETE;
	}
	
	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}
	public void delete() throws SQLException{
		EntityService em = new EntityService();
		em.delete(DELETE, this);
	}
	public void insert() throws SQLException{
		EntityService em = new EntityService();
		em.insert(INSERT, this);
	}
	
	public void update() throws SQLException{
		EntityService em = new EntityService();
		em.update(UPDATE, this);
	}
}
