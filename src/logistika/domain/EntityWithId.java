package logistika.domain;

import java.sql.SQLException;

import logistika.manager.Entity;
import logistika.manager.EntityService;

public abstract class EntityWithId implements Entity {

	private Integer id;

	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public void insert(EntityService em) throws SQLException {
		if (id != null) {
			throw new IllegalStateException("Id already exists!");
		}
		em.insert(getInsertSQL(), this);
	}

	public void update(EntityService em) throws SQLException {
		if (id == null) {
			throw new IllegalStateException(
					"Cannot delete entity with id == null!");
		}
		em.update(getUpdateSQL(), this);
	}

	public void delete(EntityService em) throws SQLException {
		if (id == null) {
			throw new IllegalStateException("id is not set");
		}
		em.delete(getDeleteSQL(), this);
	}

}
