package persistence.mapper.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T> {

    T mapping(ResultSet resultSet) throws SQLException;
}
