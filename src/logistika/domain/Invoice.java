package logistika.domain;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logistika.manager.Entity;
import logistika.manager.EntityService;

public class Invoice extends EntityWithId{
	public static String SELECT_ALL_INVOICES = "SELECT * FROM invoices";
	public static String SELECT_BY_ID = "SELECT * FROM invoices WHERE id = ? ";
	public static String INSERT = "INSERT INTO invoices (issue_date,due_date,payments,order_id) VALUES (?,?,?,?)";
	public static String UPDATE = "UPDATE invoices SET issue_date = ?,due_date = ? ,payments = ?, order_id = ? WHERE id = ?";
	public static String DELETE = "DELETE FROM invoices WHERE id = ?";
	
	private Date issueDate;
	private Date dueDate;
	private Integer payments;
	private Integer orderId;
	
	public Invoice(){
		super();
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

	public Integer getPayments() {
		return payments;
	}

	public void setPayments(Integer payments) {
		this.payments = payments;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Override
	public void insertMapping(PreparedStatement s) throws SQLException {
		s.setDate(1, issueDate);
		s.setDate(2, dueDate);
		s.setInt(3,payments);
		s.setInt(4,orderId);
		
	}

	@Override
	public void updateMapping(PreparedStatement s) throws SQLException {
		s.setDate(1, issueDate);
		s.setDate(2, dueDate);
		s.setInt(3,payments);
		s.setInt(4,orderId);
		s.setInt(5, getId());
	}

	@Override
	public void deleteMapping(PreparedStatement s) throws SQLException {
		s.setInt(1, getId());
		
	}

	@Override
	public Entity createFromResult(ResultSet r, Entity c) throws SQLException {
		Invoice i = (Invoice) c;
		
		i.setId(r.getInt("id"));
		i.setIssueDate(r.getDate("issue_date"));
		i.setDueDate(r.getDate("due_date"));
		i.setPayments(r.getInt("payments"));
		i.setOrderId(r.getInt("order_id"));
		return i;
	}
	

	@Override
	public void print() {
		// TODO Auto-generated method stub
		
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
	
	public static Invoice findById(Integer invoiceId) throws InstantiationException, IllegalAccessException, SQLException {
		return EntityService.em.findById(Invoice.SELECT_BY_ID,invoiceId,Invoice.class);
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

