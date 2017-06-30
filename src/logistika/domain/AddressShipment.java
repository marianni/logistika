package logistika.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logistika.manager.Entity;

public class AddressShipment extends EntityWithoutId {
	public static String INSERT ="INSERT INTO adresses_shipments(adress_id,shipment_id) VALUES (?,?)";
	public static String SELECT_BY_SHIPMENT_ID = "SELECT * FROM adresses_shipments WHERE shipment_id=?";
	private Integer addressId;
	private Integer shipmentId;
	
	public AddressShipment(Integer addressId, Integer shipmentId) {
		super();
		this.addressId = addressId;
		this.shipmentId = shipmentId;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public Integer getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Integer shipmentId) {
		this.shipmentId = shipmentId;
	}

	@Override
	public void insertMapping(PreparedStatement s) throws SQLException {
		s.setInt(1, addressId);
		s.setInt(2, shipmentId);
		
	}

	@Override
	public void updateMapping(PreparedStatement s) throws SQLException {
		s.setInt(1, addressId);
		s.setInt(2, shipmentId);
		
	}

	@Override
	public void deleteMapping(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entity createFromResult(ResultSet r, Entity c) throws SQLException {
		AddressShipment as =(AddressShipment) c;
		
		as.setAddressId(r.getInt("adress_id"));
		as.setShipmentId(r.getInt("shipment_id"));
		
		return as;
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
	
	
	
}
