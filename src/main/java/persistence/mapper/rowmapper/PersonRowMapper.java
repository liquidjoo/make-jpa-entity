package persistence.mapper.rowmapper;

import persistence.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapping(ResultSet resultSet) throws SQLException {

        return new Person(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("age"),
                resultSet.getString("email")
        );
    }
}
