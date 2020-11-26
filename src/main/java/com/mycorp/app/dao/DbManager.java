package com.mycorp.app.dao;

import com.mycorp.app.Config;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbManager {
    private final static Logger logger = LoggerFactory.getLogger(DbManager.class);

    public DbManager() throws SQLException {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            logger.info("Get connection");
        } catch (SQLException e) {
            logger.error("DbManager constructor error");
            throw e;
        }
    }

    static {
        migrate();
        logger.info("Migration is on");
    }

    private static void migrate() {
        Properties properties = new Properties();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(Config.getInstance().getDBMigration())){
            properties.load(is);
            logger.info("Loaded properties");
        } catch (IOException e) {
            logger.error("Migration error");
        }
        Flyway flyway = Flyway.configure().configuration(properties).load();
        flyway.migrate();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Config.getInstance().getDBURL(), Config.getInstance().getDBName(), Config.getInstance().getDBPass());
    }
}
