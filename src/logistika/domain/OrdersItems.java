package logistika.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logistika.manager.Entity;
import logistika.manager.EntityService;

public class OrdersItems extends EntityWithoutId {
	public static String INSERT ="INSERT INTO orders_items (order_id, item_id) VALUES (?,?)";
	public static String SELECT_BY_ORDER_ID ="SELECT * FROM orders_items WHERE order_id=?";
	private Integer orderId;
	private Integer itemId;

	public OrdersItems(){
		super();
	}
	
	public OrdersItems(Integer orderId, Integer itemId) {
		super();
		this.orderId = orderId;
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Override
	public void insertMapping(PreparedStatement s) throws SQLException {
		s.setInt(1,orderId);
		s.setInt(2,itemId);
		
	}

	@Override
	public void updateMapping(PreparedStatement s) throws SQLException {
		s.setInt(1,orderId);
		s.setInt(2,itemId);
		
	}

	@Override
	public void deleteMapping(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public Entity createFromResult(ResultSet r, Entity c) throws SQLException {
		OrdersItems os = (OrdersItems) c;
		
		os.setOrderId(r.getInt("order_id"));
		os.setItemId(r.getInt("item_id"));
		
		return os;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeleteSQL() {
		// TODO Auto-generated method stub
		return null;
	}
	public void insert() throws SQLException{
		EntityService em = new EntityService();
		em.insert(INSERT, this);
	}
	





	
	
	
	
	
	
	
	
}
