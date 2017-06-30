package logistika.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logistika.manager.Entity;
import logistika.manager.EntityService;

public class Warehouse extends EntityWithId{
	public static String SELECT_BY_ID = "SELECT * FROM warehouse where id = ?";
	public static String SELECT_ALL_STATUSES = "SELECT * FROM warehouse";
	public static String SELECT_BY_ADDRESS_ID ="SELECT * FROM warehouse WHERE address_id=?";
	public static String INSERT = "INSERT INTO warehouse (status, address_id) VALUES (?,?,?)";
	public static String UPDATE = "UPDATE warehouse SET status = ?, address_id = ? WHERE id = ?";
	public static String DELETE = "DELETE FROM warehouse WHERE id = ?";
	
	private String status;
	private Integer addressId;
	
	public Warehouse() {
		super();
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getAddressId() {
		return addressId;
	}
	
	public Address getAddress() throws InstantiationException, IllegalAccessException, SQLException {
		return EntityService.em.findById(Address.SELECT_BY_ID, addressId, Address.class);
	}
	
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	
	@Override
	public void insertMapping(PreparedStatement s) throws SQLException {
		s.setString(1, status);
		s.setInt(2, addressId);
	}

	@Override
	public void updateMapping(PreparedStatement s) throws SQLException {
		s.setString(1, status);
		s.setInt(2, addressId);
		s.setInt(3, getId());
	}

	@Override
	public void deleteMapping(PreparedStatement s) throws SQLException {
		s.setInt(1, getId());
	}

	@Override
	public Entity createFromResult(ResultSet r, Entity e) throws SQLException {
		Warehouse c = (Warehouse) e;

		c.setId(r.getInt("id"));
		c.setStatus(r.getString("status"));
		c.setAddressId(r.getInt("address_id"));

		return c;
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

