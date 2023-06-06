package persistence.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class H2Test {

    @Autowired
    private DataSource dataSource;

    @Test
    void connection_test() {
        H2 h2 = new H2(dataSource);
        h2.executeQuery("select 1");
    }
}
