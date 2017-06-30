package logistika.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import logistika.finders.CountryService;
import logistika.manager.Entity;
import logistika.manager.EntityService;

public class Address extends EntityWithId {
	public static String SELECT_ALL_ADRESSES = "SELECT * FROM adresses";
	public static String SELECT_BY_ID = "SELECT * FROM adresses where id=?";
	public static String INSERT = "INSERT INTO adresses (street_name, street_number,city,post_code,country_id) VALUES (?,?,?,?,?)";
	public static String UPDATE = "UPDATE adresses SET street_name = ?, street_number = ?, city = ?,post_code = ?,country_id = ? WHERE id = ?";
	public static String DELETE = "DELETE FROM adresses WHERE id = ?";

	private String streetName;
	private Integer streetNumber;
	private String city;
	private String postCode;
	private Integer countryId;
	

	public Address() {
		super();
	}


	public String getStreetName() {
		return streetName;
	}


	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}


	public Integer getStreetNumber() {
		return streetNumber;
	}


	public void setStreetNumber(Integer streetNumber) {
		this.streetNumber = streetNumber;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getPostCode() {
		return postCode;
	}


	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}


	public Integer getCountryId() {
		return countryId;
	}


	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}


	
	
	@Override
	public void insertMapping(PreparedStatement s) throws SQLException {
		s.setString(1, streetName);
		s.setInt(2,streetNumber);
		s.setString(3,city);
		s.setString(4,postCode);
		s.setInt(5,countryId);
		
	}


	@Override
	public void updateMapping(PreparedStatement s) throws SQLException {
		s.setString(1, streetName);
		s.setInt(2,streetNumber);
		s.setString(3,city);
		s.setString(4,postCode);
		s.setInt(5,countryId);
		s.setInt(6, getId());
	}


	@Override
	public void deleteMapping(PreparedStatement s) throws SQLException {
		s.setInt(1,getId());
	}


	@Override
	public Entity createFromResult(ResultSet r, Entity c) throws SQLException {
		Address a = (Address) c;
		
		a.setId(r.getInt("id"));
		a.setStreetName(r.getString("street_name"));
		a.setStreetNumber(r.getInt("street_number"));
		a.setCity(r.getString("city"));
		a.setPostCode(r.getString("post_code"));
		a.setCountryId(r.getInt("country_id"));
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
		System.out.println(String.format("id: %s", String.valueOf(getId())));
		System.out.println(String.format("street name: %s", getStreetName()));		
		System.out.println(String.format("street number: %s", getStreetNumber()));
		System.out.println(String.format("city : %s", getCity()));
		System.out.println(String.format("post code: %s", getPostCode()));
		System.out.println(String.format("country id: %s", getCountryId()));
		System.out.println();
		
	}
	public static Address findById(Integer addressId) throws InstantiationException, IllegalAccessException, SQLException {
		return EntityService.em.findById(Address.SELECT_BY_ID,addressId,Address.class);
	}
	
	public void insert() throws SQLException{
		EntityService em = new EntityService();
		em.insert(INSERT, this);
	}

	
	public void delete() throws SQLException{
		EntityService em = new EntityService();
		em.delete(DELETE, this);
	}
	
	public void update() throws SQLException{
		EntityService em = new EntityService();
		em.update(UPDATE, this);
	}

	
}
