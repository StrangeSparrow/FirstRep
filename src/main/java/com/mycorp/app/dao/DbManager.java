package com.mycorp.app.dao;

import com.mycorp.app.Config;
import com.mycorp.app.news.NewsServiceDbImpl;
import org.apache.log4j.Logger;
import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbManager {
    private final static Logger logger = Logger.getLogger(NewsServiceDbImpl.class);

    public DbManager() throws SQLException {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            logger.error(e);
            throw e;
        }
    }

    static {
        migrate();
    }

    private static void migrate() {
        Properties properties = new Properties();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(Config.getInstance().getDBMigration())){
            properties.load(is);
        } catch (IOException e) {
            logger.error(e);
        }
        Flyway flyway = Flyway.configure().configuration(properties).load();
        flyway.migrate();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Config.getInstance().getDBURL(), Config.getInstance().getDBName(), Config.getInstance().getDBPass());
    }
}
