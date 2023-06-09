package persistence.sql.ddl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

public class JdbcTypeJavaClassMappings {
    private static final Logger log = LoggerFactory.getLogger(JdbcTypeJavaClassMappings.class);

    public static final JdbcTypeJavaClassMappings INSTANCE = new JdbcTypeJavaClassMappings();

    private final ConcurrentHashMap<Class, Integer> javaClassToJdbcTypeCodeMap;
    private final ConcurrentHashMap<Integer, Class> jdbcTypeCodeToJavaClassMap;

    private JdbcTypeJavaClassMappings() {
        javaClassToJdbcTypeCodeMap = buildJavaClassToJdbcTypeCodeMappings();
        jdbcTypeCodeToJavaClassMap = buildJdbcTypeCodeToJavaClassMappings();
    }

    /**
     * For the given Java type, determine the JDBC recommended JDBC type.
     * <p>
     * This includes the mappings defined in <i>TABLE B-2 - Java Types Mapped to JDBC Types</i>
     * as well as some additional "common sense" mappings for things like BigDecimal, BigInteger,
     * etc.
     */
    public int determineJdbcTypeCodeForJavaClass(Class cls) {
        Integer typeCode = javaClassToJdbcTypeCodeMap.get(cls);
        if (typeCode != null) {
            return typeCode;
        }

        int specialCode = cls.hashCode();
        log.debug("JDBC type code mapping not known for class [" + cls.getName() + "]; using custom code [" + specialCode + "]");
        return specialCode;
    }

    /**
     * For the given JDBC type, determine the JDBC recommended Java type.  These mappings
     * are defined by <i>TABLE B-1 - JDBC Types Mapped to Java Types</i>
     */
    public Class determineJavaClassForJdbcTypeCode(Integer typeCode) {
        Class cls = jdbcTypeCodeToJavaClassMap.get(typeCode);
        if (cls != null) {
            return cls;
        }

        log.debug(String.format("Java Class mapping not known for JDBC type code [%s]; using java.lang.Object", typeCode));
        return Object.class;
    }

    /**
     * @see #determineJavaClassForJdbcTypeCode(Integer)
     */
    public Class determineJavaClassForJdbcTypeCode(int typeCode) {
        return determineJavaClassForJdbcTypeCode(Integer.valueOf(typeCode));
    }


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private static ConcurrentHashMap<Class, Integer> buildJavaClassToJdbcTypeCodeMappings() {
        final ConcurrentHashMap<Class, Integer> workMap = new ConcurrentHashMap<>();

        // these mappings are the ones outlined specifically in the spec
        workMap.put(String.class, Types.VARCHAR);
        workMap.put(BigDecimal.class, Types.NUMERIC);
        workMap.put(BigInteger.class, Types.NUMERIC);
        workMap.put(Boolean.class, Types.BIT);
        workMap.put(Byte.class, Types.TINYINT);
        workMap.put(Short.class, Types.SMALLINT);
        workMap.put(Integer.class, Types.INTEGER);
        workMap.put(Long.class, Types.BIGINT);
        workMap.put(Float.class, Types.REAL);
        workMap.put(Double.class, Types.DOUBLE);
        workMap.put(byte[].class, Types.LONGVARBINARY);
        workMap.put(java.sql.Date.class, Types.DATE);
        workMap.put(Time.class, Types.TIME);
        workMap.put(Timestamp.class, Types.TIMESTAMP);
        workMap.put(Blob.class, Types.BLOB);
        workMap.put(Clob.class, Types.CLOB);
        workMap.put(Struct.class, Types.STRUCT);
        workMap.put(Ref.class, Types.REF);
        workMap.put(Class.class, Types.JAVA_OBJECT);
        workMap.put(RowId.class, Types.ROWID);
        workMap.put(SQLXML.class, Types.SQLXML);


        // additional "common sense" registrations
        workMap.put(Character.class, Types.CHAR);
        workMap.put(char[].class, Types.VARCHAR);
        workMap.put(Character[].class, Types.VARCHAR);
        workMap.put(Byte[].class, Types.LONGVARBINARY);
        workMap.put(java.util.Date.class, Types.TIMESTAMP);
        workMap.put(Calendar.class, Types.TIMESTAMP);

        return workMap;
    }

    private static ConcurrentHashMap<Integer, Class> buildJdbcTypeCodeToJavaClassMappings() {
        final ConcurrentHashMap<Integer, Class> workMap = new ConcurrentHashMap<>();

        workMap.put(Types.CHAR, String.class);
        workMap.put(Types.VARCHAR, String.class);
        workMap.put(Types.LONGVARCHAR, String.class);
        workMap.put(Types.NCHAR, String.class);
        workMap.put(Types.NVARCHAR, String.class);
        workMap.put(Types.LONGNVARCHAR, String.class);
        workMap.put(Types.NUMERIC, BigDecimal.class);
        workMap.put(Types.DECIMAL, BigDecimal.class);
        workMap.put(Types.BIT, Boolean.class);
        workMap.put(Types.BOOLEAN, Boolean.class);
        workMap.put(Types.TINYINT, Byte.class);
        workMap.put(Types.SMALLINT, Short.class);
        workMap.put(Types.INTEGER, Integer.class);
        workMap.put(Types.BIGINT, Long.class);
        workMap.put(Types.REAL, Float.class);
        workMap.put(Types.DOUBLE, Double.class);
        workMap.put(Types.FLOAT, Double.class);
        workMap.put(Types.BINARY, byte[].class);
        workMap.put(Types.VARBINARY, byte[].class);
        workMap.put(Types.LONGVARBINARY, byte[].class);
        workMap.put(Types.DATE, java.sql.Date.class);
        workMap.put(Types.TIME, Time.class);
        workMap.put(Types.TIMESTAMP, Timestamp.class);
        workMap.put(Types.BLOB, Blob.class);
        workMap.put(Types.CLOB, Clob.class);
        workMap.put(Types.NCLOB, NClob.class);
        workMap.put(Types.STRUCT, Struct.class);
        workMap.put(Types.REF, Ref.class);
        workMap.put(Types.JAVA_OBJECT, Class.class);
        workMap.put(Types.ROWID, RowId.class);
        workMap.put(Types.SQLXML, SQLXML.class);

        return workMap;
    }
}
