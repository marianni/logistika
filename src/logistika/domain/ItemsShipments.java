package logistika.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logistika.manager.Entity;
import logistika.manager.EntityService;

public class ItemsShipments extends EntityWithoutId {
	public static String INSERT ="INSERT INTO item_shipments (item_id, shipment_id) VALUES (?,?)";
	private Integer itemId;
	private Integer shipmentId;

	public ItemsShipments(Integer itemId, Integer shipmentId) {
		super();
		this.itemId = itemId;
		this.shipmentId = shipmentId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public Integer getShipmentId() {
		return shipmentId;
	}
	public void insert() throws SQLException{
		EntityService em = new EntityService();
		em.insert(INSERT, this);
	}

	@Override
	public void insertMapping(PreparedStatement s) throws SQLException {
		s.setInt(1,itemId);
		s.setInt(2,shipmentId);
		
	}

	@Override
	public void updateMapping(PreparedStatement s) throws SQLException {
		s.setInt(1,itemId);
		s.setInt(2,shipmentId);
		
	}

	@Override
	public void deleteMapping(PreparedStatement s) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entity createFromResult(ResultSet r, Entity c) throws SQLException {
		// TODO Auto-generated method stub
		return null;
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
