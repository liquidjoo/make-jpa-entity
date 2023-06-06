package persistence.sql.dml;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class Delete {

    private final CustomDeleteQueryBuilder customDeleteQueryBuilder = new CustomDeleteQueryBuilder();

    public String toStatementString(Class<?> clazz) {
        return customDeleteQueryBuilder.translatorDeleteQuery(clazz);
    }

    private static class CustomDeleteQueryBuilder {
        private Map<String, String> columns = new LinkedHashMap<>();

        public String translatorDeleteQuery(Class<?> clazz) {
            validateEntityAnnotation(clazz);
            StringBuilder query = new StringBuilder();
            query.append(deleteClause(clazz));
            query.append(fromClause(clazz));
            query.append(whereClause(clazz));
            return query.toString();
        }

        private String deleteClause(Class<?> clazz) {
            return "delete ";
        }

        private String fromClause(Class<?> clazz) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" from ");
            if (clazz.isAnnotationPresent(Table.class)) {
                Table annotation = clazz.getAnnotation(Table.class);
                stringBuilder.append(annotation.name());
                return stringBuilder.toString();
            }

            stringBuilder.append(clazz.getSimpleName());
            return stringBuilder.toString();
        }

        private String whereClause(Class<?> clazz) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" where ");
            stringBuilder.append(getIdFields(clazz).getName());
            stringBuilder.append(" = ");
            stringBuilder.append(" ?");
            return stringBuilder.toString();
        }

        private Field getIdFields(Class<?> clazz) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(Id.class)) {
                    return declaredField;
                }
            }
            throw new IllegalArgumentException();
        }

        private void validateEntityAnnotation(Class<?> clazz) {
            if (!clazz.isAnnotationPresent(Entity.class)) {
                throw new IllegalArgumentException();
            }
        }
    }
}
