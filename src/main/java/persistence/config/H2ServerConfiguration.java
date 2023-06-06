package persistence.config;

import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class H2ServerConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(H2ServerConfiguration.class);

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() throws SQLException {
        Server server = defaultRun(9093);
        if (server.isRunning(true)) {
            LOGGER.info("server run success");
        }

        LOGGER.info("h2 server url : {}, \nstatus : {} ", server.getURL(), server.getStatus());

        return new HikariDataSource();
    }


    private Server adviceRun(int port, String externalDbName, String dbname, FilePath db_store) throws SQLException {
        return Server.createTcpServer(
                "-tcp",
                "-tcpAllowOthers",
                "-ifNotExists",
                "-tcpPort", port + "", "-key", externalDbName, db_store.value2(dbname)).start();
    }

    private Server defaultRun(int port) throws SQLException {
        return Server.createTcpServer(
                "-tcp",
                "-tcpAllowOthers",
                "-ifNotExists",
                "-tcpPort", port + "").start();
    }

    enum FilePath {
        absolute("~/"),
        relative("./");
        String prefix;

        FilePath(String prefix) {
            this.prefix = prefix;
        }

        public String value2(String dbname) {
            return prefix + dbname;
        }
    }
}
