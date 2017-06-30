package logistika.domainfunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import logistika.DbContext;
import logistika.domain.Address;
import logistika.domain.AddressShipment;
import logistika.domain.Shipment;
import logistika.domain.Warehouse;
import logistika.manager.EntityService;

public class ShipmentDestinationChange {

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Warehouse manazment

	public static void change() throws NumberFormatException, InstantiationException, IllegalAccessException,
			IOException, SQLException, ParseException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter shipment ID");
		int shipmentId = Integer.valueOf(br.readLine());
		Shipment shipment = EntityService.em.findById(Shipment.SELECT_BY_ID, shipmentId, Shipment.class);
		if (shipment == null) {
			System.out.println("Shipment does not exits !");
			return;
		}

		System.out.println("Enter new warehouse ID");
		int warehouseId = Integer.valueOf(br.readLine());
		Warehouse newWarehouse = EntityService.em.findById(Warehouse.SELECT_BY_ID, warehouseId, Warehouse.class);
		if (newWarehouse == null) {
			System.out.println("Warehouse does not exits !");
			return;
		}

		if (newWarehouse.getStatus().equals("full")) {
			System.out.println("Warehouse is full, please choose another warehouse!");
			return;
		}

		if (newWarehouse.getStatus().equals("closed")) {
			System.out.println("Warehouse is closed, please choose another warehouse!");
			return;
		}

		DbContext.getConnection().setAutoCommit(false);
		try {
			List<AddressShipment> existingConnection = EntityService.em
					.searchQuery(AddressShipment.SELECT_BY_SHIPMENT_ID, AddressShipment.class, shipment.getId());

			for (AddressShipment as : existingConnection) {
				as.delete();
			}

			Address newWarehouseAddress = newWarehouse.getAddress();
			AddressShipment addressShipment = new AddressShipment(newWarehouseAddress.getId(), shipment.getId());
			addressShipment.insert();

			System.out.println("Destination warehouse has been changed.");

		} catch (SQLException e) {
			DbContext.rollBackWhenException(e);
		}

	}
}
