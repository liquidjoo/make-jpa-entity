package persistence.database;

import java.sql.ResultSet;

public interface Database {
    void execute(String sql);

    ResultSet executeQuery(String sql);
}
