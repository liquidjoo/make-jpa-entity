package persistence.jdbc;

import org.junit.jupiter.api.Test;
import persistence.database.DatabaseServer;
import persistence.database.H2;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTemplateTest {

    @Test
    void connection_test() throws SQLException {

        final DatabaseServer server = new H2();
        server.start();

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

        jdbcTemplate.execute("select 1");

        server.stop();
    }
}
