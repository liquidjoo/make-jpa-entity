package persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.database.DatabaseServer;
import persistence.database.H2;
import persistence.jdbc.JdbcTemplate;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
