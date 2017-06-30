package logistika.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import logistika.manager.Entity;
import logistika.manager.EntityService;

public class Shipment extends EntityWithId {
	public static String SELECT_ALL_SHIPMENTS = "SELECT * FROM shipments";
	public static String SELECT_BY_ID = "SELECT * FROM shipments where id = ?";
	public static String INSERT = "INSERT INTO shipments (type, date_and_time) VALUES (?,?)";
	public static String UPDATE = "UPDATE shipments SET type = ?, date_and_time = ? WHERE id = ?";
	public static String DELETE = "DELETE FROM shipments WHERE id = ?";
	
	private String type;
	private Timestamp dateAndTime;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	@Override
	public void insertMapping(PreparedStatement s) throws SQLException {
		s.setString(1,type);
		s.setTimestamp(2, dateAndTime);
		
	}
	@Override
	public void updateMapping(PreparedStatement s) throws SQLException {
		s.setString(1,type);
		s.setTimestamp(2, dateAndTime);
		s.setInt(3, getId());
	}
	@Override
	public void deleteMapping(PreparedStatement s) throws SQLException {
		s.setInt(1, getId());
		
	}
	@Override
	public Entity createFromResult(ResultSet r, Entity c) throws SQLException {
		Shipment s = (Shipment) c;

		s.setId(r.getInt("id"));
		s.setType(r.getString("type"));
		s.setDateAndTime(r.getTimestamp("date_and_time"));
		
		return s;
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
