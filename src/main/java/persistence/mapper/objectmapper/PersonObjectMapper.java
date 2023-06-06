package persistence.mapper.objectmapper;

import persistence.Person;

public class PersonObjectMapper {

    public String mapping(String sql, Person person) {
        sql = sql.replaceFirst("\\?", person.getName());
        sql = sql.replaceFirst("\\?", person.getAge().toString());
        sql = sql.replaceFirst("\\?", person.getEmail());
        return sql;
    }
}
