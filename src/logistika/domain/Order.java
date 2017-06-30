package logistika.domain;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logistika.manager.Entity;
import logistika.manager.EntityService;


public class Order extends EntityWithId {
	public static String SELECT_ALL_ORDERS = "SELECT * FROM orders";
	public static String SELECT_BY_ID = "SELECT * FROM orders WHERE id=?";
	public static String SELECT_BY_CUSTOMER_ID ="SELECT * FROM orders WHERE customer_id=?";
	public static String SELECT_BY_ORDER_ID ="SELECT * FROM orders WHERE id=?";
	
	public static String INSERT = "INSERT INTO orders (date_of_order,quantity,customer_id) VALUES (?,?,?)";
	public static String UPDATE = "UPDATE orders SET date_of_order = ?, quantity = ?,customer_id=?  WHERE id = ?";
	public static String DELETE = "DELETE FROM orders WHERE id = ?";

	private Date dateOfOrder;
	private Long quantity;
	private Integer customerId;
	

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Order() {
		super();
	}

	public Date getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public void insertMapping(PreparedStatement s) throws SQLException {
		s.setDate(1, dateOfOrder);
		s.setLong(2, quantity);
		s.setInt(3, customerId);

	}
	@Override
	public void deleteMapping(PreparedStatement s) throws SQLException {
		s.setInt(1, getId());
	}
	
	@Override
	public Entity createFromResult(ResultSet r, Entity e) throws SQLException {
		Order c = (Order) e;

		c.setId(r.getInt("id"));
		c.setDateOfOrder(r.getDate("date_of_order"));
		c.setQuantity(r.getLong("quantity"));
		c.setCustomerId(r.getInt("customer_id"));


		return c;
	}

	@Override
	public void updateMapping(PreparedStatement s) throws SQLException {
		s.setInt(1, getId());
		
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
	
	public static Order findByCustomerId(Integer customerId) throws InstantiationException, IllegalAccessException, SQLException {
		return EntityService.em.findById(Order.SELECT_BY_CUSTOMER_ID,customerId,Order.class);
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
