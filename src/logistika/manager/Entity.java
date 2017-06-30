package logistika.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Entity {
    void insertMapping(final PreparedStatement s) throws SQLException;
    void setId(Integer id);
    void updateMapping(final PreparedStatement s) throws SQLException;
    void deleteMapping(final PreparedStatement s) throws SQLException;
    Entity createFromResult(ResultSet r, Entity c) throws SQLException; // z resultu z databazy vytvori entitu v jave
    
    void print();
    
    String getInsertSQL();
    String getUpdateSQL();
    String getDeleteSQL();
}
