package logistika.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logistika.manager.Entity;
import logistika.manager.EntityService;

public class Item extends EntityWithId {
	public static String SELECT_ALL_ITEMS = "SELECT * FROM items";
	public static String SELECT_BY_ID = "SELECT * FROM items WHERE id =?";
	public static String INSERT = "INSERT INTO items (type, price,description,customer_id) VALUES (?,?,?,?)";
	public static String UPDATE = "UPDATE items SET type = ?, price = ? , description = ?, customer_id = ? WHERE id = ?";
	public static String DELETE = "DELETE FROM items WHERE id = ?";
	
	private String type;
	private Integer price;
	private String description;
	private Integer customerId;
	
	public Item(){
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Override
	public void insertMapping(PreparedStatement s) throws SQLException {
		s.setString(1,type);
		s.setInt(2, price);
		s.setString(3, description);
		s.setInt(4,customerId);
		
	}

	@Override
	public void updateMapping(PreparedStatement s) throws SQLException {
		s.setString(1,type);
		s.setInt(2, price);
		s.setString(3, description);
		s.setInt(4,customerId);
		s.setInt(5, getId());
		
	}

	@Override
	public void deleteMapping(PreparedStatement s) throws SQLException {
		s.setInt(1, getId());
		
	}

	@Override
	public Entity createFromResult(ResultSet r, Entity c) throws SQLException {
		Item a =(Item)c;
		
		a.setId(r.getInt("id"));
		a.setType(r.getString("type"));
		a.setPrice(r.getInt("price"));
		a.setDescription(r.getString("description"));
		a.setCustomerId(r.getInt("customer_id"));
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
