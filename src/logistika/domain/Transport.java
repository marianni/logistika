package logistika.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logistika.manager.Entity;
import logistika.manager.EntityService;

public class Transport extends EntityWithId {
	public static String SELECT_ALL_TRANSPORTS = "SELECT * FROM transport";
	public static String INSERT = "INSERT INTO transport (type, shipment_id) VALUES (?,?)";
	public static String UPDATE = "UPDATE transport SET type = ?, shipment_id = ? WHERE id = ?";
	public static String DELETE = "DELETE FROM transport WHERE id = ?";
	
	private String type;
	private Integer shipmentId;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(Integer shipmentId) {
		this.shipmentId = shipmentId;
	}
	@Override
	public void insertMapping(PreparedStatement s) throws SQLException {
		s.setString(1, type);
		s.setInt(2, shipmentId);
		
	}
	@Override
	public void updateMapping(PreparedStatement s) throws SQLException {
		s.setString(1, type);
		s.setInt(2, shipmentId);
		s.setInt(3, getId());
		
	}
	@Override
	public void deleteMapping(PreparedStatement s) throws SQLException {
		s.setInt(1, getId());
		
	}
	@Override
	public Entity createFromResult(ResultSet r, Entity c) throws SQLException {
		Transport t =(Transport) c;
		 t.setId(r.getInt("id"));
		 t.setType(r.getString("type"));
		 t.setShipmentId(r.getInt("shipment_id"));
		 
		 return t;
		
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
