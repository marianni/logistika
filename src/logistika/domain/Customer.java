package logistika.domain;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logistika.manager.Entity;
import logistika.manager.EntityService;

public class Customer extends EntityWithId {
	public static String SELECT_ALL_CUSTOMERS = "SELECT * FROM customers";
	public static String SELECT_BY_ID ="SELECT * FROM customers WHERE id=?";
	public static String INSERT = "INSERT INTO customers (first_name, last_name, date_of_birth, address_id) VALUES (?,?,?,?)";
	public static String UPDATE = "UPDATE customers SET first_name = ?, last_name = ?, date_of_birth = ?, address_id = ? WHERE id = ?";
	public static String DELETE = "DELETE FROM customers WHERE id = ?";

	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private Integer addressId;

	public Customer() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	@Override
	public void insertMapping(PreparedStatement s) throws SQLException {
		s.setString(1, firstName);
		s.setString(2, lastName);
		s.setDate(3, dateOfBirth);
		s.setInt(4, addressId);
	}

	@Override
	public void updateMapping(PreparedStatement s) throws SQLException {
		s.setString(1, firstName);
		s.setString(2, lastName);
		s.setDate(3, dateOfBirth);
		s.setInt(4, addressId);
		s.setInt(5, getId());
	}

	@Override
	public void deleteMapping(PreparedStatement s) throws SQLException {
		s.setInt(1, getId());
	}

	@Override
	public Entity createFromResult(ResultSet r, Entity e) throws SQLException {
		Customer c = (Customer) e;

		c.setId(r.getInt("id"));
		c.setFirstName(r.getString("first_name"));
		c.setLastName(r.getString("last_name"));
		c.setDateOfBirth(r.getDate("date_of_birth"));
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
	
	public static Customer findById(Integer customerId) throws InstantiationException, IllegalAccessException, SQLException {
		return EntityService.em.findById(Customer.SELECT_BY_ID,customerId,Customer.class);
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
	
	@Override
	public void print() {
		System.out.println(String.format("id: %s", String.valueOf(getId())));
		System.out.println(String.format("firstName: %s", getFirstName()));		
		System.out.println(String.format("lastName: %s", getLastName()));
		System.out.println(String.format("dateOfBirth: %s", getDateOfBirth()));
		System.out.println(String.format("addressId: %s", getAddressId()));
		System.out.println();
	}
	
	
	
}
