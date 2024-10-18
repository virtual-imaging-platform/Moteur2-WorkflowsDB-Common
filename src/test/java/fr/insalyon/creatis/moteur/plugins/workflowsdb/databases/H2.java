package fr.insalyon.creatis.moteur.plugins.workflowsdb.databases;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.jdbcx.JdbcDataSource;

import fr.insalyon.creatis.moteur.plugins.workflowsdb.Database;

public class H2 extends Database {

    @Override
    public void create() throws SQLException {
        source = new JdbcDataSource();
        source.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=TRUE");
        source.setUser("test");
        source.setPassword("pass");

        schema = "test";
        driverClass = "org.h2.Driver";
        url = source.getUrl();
        dialect = "org.hibernate.dialect.MySQLDialect";
        user = source.getUser();
        password = source.getPassword();

        try (Connection connection = source.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE SCHEMA IF NOT EXISTS " + schema);
            }
        }
        super.create();
    }

    @Override
    public void delete() {}

    @Override
    public boolean isAvailable() {
        return true;
    }
}
