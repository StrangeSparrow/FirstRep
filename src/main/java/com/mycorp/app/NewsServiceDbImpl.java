package com.mycorp.app;

import org.apache.log4j.Logger;
import org.flywaydb.core.Flyway;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class NewsServiceDbImpl implements NewsService {
    private final static Logger logger = Logger.getLogger(NewsServiceDbImpl.class);

    static {
        Properties properties = new Properties();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(Config.getInstance().getDBMigration())){
            properties.load(is);
        } catch (IOException e) {
            logger.error(e);
        }
        Flyway flyway = Flyway.configure().configuration(properties).load();
        flyway.migrate();
    }

    public NewsServiceDbImpl() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Config.getInstance().getDBURL(), Config.getInstance().getDBName(), Config.getInstance().getDBPass());
    }

    @Override
    public List<News> fetchNews() {
        List<News> newsList = new ArrayList<>();

        String query = "SELECT * FROM news_db.news ORDER BY id DESC";
        try (Connection connection = getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            ResultSet resultSet = prStmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String head = resultSet.getString(2);
                String briefly = resultSet.getString(3);
                String full = resultSet.getString(4);

                newsList.add(new News(head, briefly, full, id));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return newsList;
    }

    @Override
    public News fetchSingleNews(int id) {
        String query = "SELECT * FROM news_db.news WHERE id=?";
        News news = null;

        try (Connection connection = getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setInt(1, id);
            ResultSet resultSet = prStmt.executeQuery();

            if (resultSet.next()) {
                int index = resultSet.getInt(1);
                String head = resultSet.getString(2);
                String briefly = resultSet.getString(3);
                String full = resultSet.getString(4);
                news = new News(head, briefly, full, index);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return news;
    }

    @Override
    public void addNews(News news) {
        String query = "INSERT INTO news_db.news (head, briefly, full) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setString(1, news.getHead());
            prStmt.setString(2, news.getBriefly());
            prStmt.setString(3, news.getFull());

            prStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void deleteNews(int id) {
        String query = "DELETE FROM news_db.news WHERE id=?";

        try (Connection connection = getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setInt(1, id);
            prStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void editNews(News news) {
        String query = "UPDATE news_db.news SET head=?, briefly=?, full=? WHERE id=?";

        try (Connection connection = getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setInt(4, news.getId());
            prStmt.setString(1, news.getHead());
            prStmt.setString(2, news.getBriefly());
            prStmt.setString(3, news.getFull());

            prStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
