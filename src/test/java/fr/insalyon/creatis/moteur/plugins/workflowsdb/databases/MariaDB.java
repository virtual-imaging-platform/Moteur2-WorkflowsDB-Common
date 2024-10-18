package fr.insalyon.creatis.moteur.plugins.workflowsdb.databases;

import java.sql.SQLException;

import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;

import fr.insalyon.creatis.moteur.plugins.workflowsdb.Database;

public class MariaDB extends Database {

    public MariaDBContainer<?> mariadb = new MariaDBContainer<>(DockerImageName.parse("mariadb:11.5-ubi"));

    @Override
    public void create() throws SQLException {
        mariadb.start();
        
        try (var connection = mariadb.createConnection("")) {
            try (var stmt = connection.createStatement()) {
                stmt.execute("ALTER DATABASE " + mariadb.getDatabaseName() + " CHARACTER SET latin1;");
            }
        }
        schema = mariadb.getDatabaseName();
        driverClass = "org.mariadb.jdbc.Driver";
        url = mariadb.getJdbcUrl();
        dialect = "org.hibernate.dialect.MySQLDialect";
        user = mariadb.getUsername();
        password = mariadb.getPassword();
        super.create();
    }

    @Override
    public void delete() {
        mariadb.stop();
    }

    @Override
    public boolean isAvailable() {
        return DockerClientFactory.instance().isDockerAvailable();
    }
}
