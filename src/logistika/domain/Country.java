package logistika.domain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import logistika.manager.Entity;
import logistika.manager.EntityService;

public class Country extends EntityWithId {
	public static String SELECT_BY_COUNTRY_CODE ="SELECT * FROM countries WHERE shortcut_of_country = ?";
	public static String SELECT_ALL_COUNTRIES = "SELECT * FROM countries";
	public static String SELECT_BY_ID = "SELECT * FROM countries WHERE id=?";
	public static String INSERT = "INSERT INTO countries (name, shortcut_of_country,continent) VALUES (?,?,?)";
	public static String UPDATE = "UPDATE countries SET name = ?, shortcut_of_country = ? , continent = ? WHERE id = ?";
	public static String DELETE = "DELETE FROM countries WHERE id = ?";
	
	private String name;
	private String shortcutOfCountry;
	private String continent;
	
	public Country(){
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortcutOfCountry() {
		return shortcutOfCountry;
	}

	public void setShortcutOfCountry(String shortcutOfCountry) {
		this.shortcutOfCountry = shortcutOfCountry;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	
	
	@Override
	public void insertMapping(PreparedStatement s) throws SQLException {
		s.setString(1, name);
		s.setString(2, shortcutOfCountry);
		s.setString(3, continent);
		
	}

	@Override
	public void updateMapping(PreparedStatement s) throws SQLException {
		s.setString(1, name);
		s.setString(2, shortcutOfCountry);
		s.setString(3, continent);
		s.setInt(4,getId());
		
	}

	@Override
	public void deleteMapping(PreparedStatement s) throws SQLException {
		s.setInt(1,getId());
		
	}

	@Override
	public Entity createFromResult(ResultSet r, Entity c) throws SQLException {
		Country a = (Country) c;
		
		a.setId(r.getInt("id"));
		a.setName(r.getString("name"));
		a.setShortcutOfCountry(r.getString("shortcut_of_country"));
		a.setContinent(r.getString("continent"));
		return a;
	}

	public static Country findByShortcut(String shortcut) throws InstantiationException, IllegalAccessException, SQLException {
		return EntityService.em.findByString(Country.SELECT_BY_COUNTRY_CODE,shortcut,Country.class);
	}
	
	public static Country findById(Integer countryId) throws InstantiationException, IllegalAccessException, SQLException {
		return EntityService.em.findById(Country.SELECT_BY_ID,countryId,Country.class);
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
		System.out.println(String.format("name: %s", getName()));		
		System.out.println(String.format("shortcut of country: %s", getShortcutOfCountry()));	
		System.out.println(String.format("continent: %s", getContinent()));
		System.out.println();
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
