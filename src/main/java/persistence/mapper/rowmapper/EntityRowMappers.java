package persistence.mapper.rowmapper;

import persistence.Person;

import java.util.HashMap;
import java.util.Map;

public class EntityRowMappers {

    private static final Map<Class, RowMapper> mappers = new HashMap<>();

    static {
        mappers.put(Person.class, new PersonRowMapper());
    }


    public static RowMapper getMapper(Class<?> clazz) {
        return mappers.get(clazz);
    }
}
