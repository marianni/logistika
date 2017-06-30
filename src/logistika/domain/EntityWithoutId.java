package logistika.domain;

import java.sql.SQLException;

import logistika.manager.Entity;
import logistika.manager.EntityService;

public abstract class EntityWithoutId implements Entity {

	@Override
	public void setId(Integer id) {
		// does not have ID
	}

	public void insert() throws SQLException {		
		EntityService.em.insert(getInsertSQL(), this);
	}

	public void update() throws SQLException {
		EntityService.em.update(getUpdateSQL(), this);
	}

	public void delete() throws SQLException {
		EntityService.em.delete(getDeleteSQL(), this);
	}

}
