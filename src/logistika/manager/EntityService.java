package logistika.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logistika.DbContext;
import logistika.domain.EntityWithId;
import logistika.domain.EntityWithoutId;

public class EntityService {

	public static final EntityService em = new EntityService();

	public <E extends Entity> E searchQueryFindFirst(String query, Class<E> entityClass, Object... paramaters)
			throws SQLException, InstantiationException, IllegalAccessException {

		List<E> list = searchQuery(query, entityClass, paramaters);
		if (list.isEmpty()) {
			StringBuilder b = new StringBuilder();
			b.append("Entity ").append(entityClass.toString()).append(" with parameters: ");
			int index = 0;
			for (Object param : paramaters) {
				b.append("param").append(index).append(": ").append(param.toString());
			}
			b.append(" WAS NOT FOUND !");
			throw new SQLException(b.toString());
		}
		return list.get(0);
	}

	public <E extends Entity> List<E> searchQuery(String query, Class<E> entityClass, Object... paramaters)
			throws SQLException, InstantiationException, IllegalAccessException {
		if (query == null) {
			throw new NullPointerException("query cannot be null");
		}
		try (PreparedStatement s = DbContext.getConnection().prepareStatement(query)) {
			int index = 1;
			// zoberie parametre do query z parametra funkcie(pole) a namapuje
			// to do query; nahradi otazniky konkretnymi hodnotami
			for (Object param : paramaters) {
				s.setObject(index, param);
				index++;
			}

			ResultSet r = s.executeQuery();
			List<E> list = new ArrayList<E>();
			while (r.next()) {
				E e = entityClass.newInstance();
				E entity = (E) e.createFromResult(r, e);
				list.add(entity);
			}
			return list;
		}
	}

	public void insert(String sql, Entity entity) throws SQLException {
		try (PreparedStatement s = DbContext.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			entity.insertMapping(s);
			s.executeUpdate();
			try (ResultSet r = s.getGeneratedKeys()) {
				r.next();
				entity.setId(r.getInt(1));
			}
		}
	}

	public void insert(String sql, EntityWithoutId entity) throws SQLException {
		try (PreparedStatement s = DbContext.getConnection().prepareStatement(sql)) {
			entity.insertMapping(s);
			s.executeUpdate();
			try (ResultSet r = s.getGeneratedKeys()) {
				r.next();
			}
		}
	}

	public void update(String sql, Entity entity) throws SQLException {
		try (PreparedStatement s = DbContext.getConnection().prepareStatement(sql)) {
			entity.updateMapping(s);
			s.executeUpdate();
		}
	}

	public void delete(String sql, Entity entity) throws SQLException {
		try (PreparedStatement s = DbContext.getConnection().prepareStatement(sql)) {
			entity.deleteMapping(s);
			s.executeUpdate();
		}
	}

	public <E extends EntityWithId> E findById(String query, int value, Class<E> entityClass)
			throws SQLException, InstantiationException, IllegalAccessException {
		if (query == null) {
			throw new NullPointerException("query cannot be null");
		}

		try (PreparedStatement s = DbContext.getConnection().prepareStatement(query)) {
			s.setInt(1, value);
			ResultSet r = s.executeQuery();
			if (r.next()) {
				E e = entityClass.newInstance();
				E entity = (E) e.createFromResult(r, e);
				if (r.next()) {
					throw new RuntimeException("More than one row was returned");
				}
				return entity;
			} else {
				return null;
			}
		}
	}

	public <E extends Entity> List<E> findAll(String query, Class<E> entityClass)
			throws SQLException, InstantiationException, IllegalAccessException {
		if (query == null) {
			throw new NullPointerException("query cannot be null");
		}

		List<E> list = new ArrayList<E>();
		try (PreparedStatement s = DbContext.getConnection().prepareStatement(query)) {
			ResultSet r = s.executeQuery();
			while (r.next()) {
				E e = entityClass.newInstance();
				E entity = (E) e.createFromResult(r, e);
				list.add(entity);
			}
		}
		return list;
	}

	public <E extends Entity> E findByString(String query, String value, Class<E> entityClass)
			throws SQLException, InstantiationException, IllegalAccessException {
		if (query == null) {
			throw new NullPointerException("query cannot be null");
		}

		try (PreparedStatement s = DbContext.getConnection().prepareStatement(query)) {
			s.setString(1, value);
			ResultSet r = s.executeQuery();
			if (r.next()) {
				E e = entityClass.newInstance();
				E entity = (E) e.createFromResult(r, e);
				if (r.next()) {
					throw new RuntimeException("More than one row was returned");
				}
				return entity;
			} else {
				return null;
			}
		}
	}

}
