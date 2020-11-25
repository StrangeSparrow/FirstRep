package com.mycorp.app.news;

import com.mycorp.app.dao.DbManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsServiceDbImpl implements NewsService {
    private final static Logger logger = LoggerFactory.getLogger(NewsServiceDbImpl.class);
    private final DbManager dbManager;

    public NewsServiceDbImpl() throws SQLException {
        try {
            dbManager = new DbManager();
            logger.info("Db manager for News serviceDbImpl has created");
        } catch (SQLException e) {
            logger.error("Error in class NewsServiceDbImpl", e);
            throw e;
        }
    }

    @Override
    public List<News> fetchNews() throws SQLException {
        List<News> newsList = new ArrayList<>();

        String query = "SELECT n.id, n.head, n.briefly, n.full, u.login " +
                "FROM news_db.news n LEFT OUTER JOIN news_db.users u ON n.author=u.id ORDER BY n.id DESC";
        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            ResultSet resultSet = prStmt.executeQuery();

            logger.info("Connection in method fetchNews()");

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String head = resultSet.getString(2);
                String briefly = resultSet.getString(3);
                String full = resultSet.getString(4);
                String author = resultSet.getString(5);

                newsList.add(new News(head, briefly, full, id, author));
            }
        } catch (SQLException e) {
            logger.error("Error in class NewsServiceDbImpl", e);
            throw e;
        }
        return newsList;
    }

    @Override
    public News fetchSingleNews(int id) throws SQLException {
        String query = "SELECT n.id, n.head, n.briefly, n.full, u.login FROM news_db.news n LEFT OUTER JOIN news_db.users u ON n.author=u.id WHERE n.id=?";
        News news = null;

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setInt(1, id);
            ResultSet resultSet = prStmt.executeQuery();

            logger.info("Connection in method fetchSingleNews()");

            if (resultSet.next()) {
                int index = resultSet.getInt(1);
                String head = resultSet.getString(2);
                String briefly = resultSet.getString(3);
                String full = resultSet.getString(4);
                String author = resultSet.getString(5);
                news = new News(head, briefly, full, index, author);
            }
        } catch (SQLException e) {
            logger.error("Error in class NewsServiceDbImpl", e);
            throw e;
        }
        return news;
    }

    @Override
    public void addNews(News news) throws SQLException {
        String query = "INSERT INTO news_db.news (head, briefly, full) VALUES (?, ?, ?)";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setString(1, news.getHead());
            prStmt.setString(2, news.getBriefly());
            prStmt.setString(3, news.getFull());

            logger.info("Connection in method addNews()");

            prStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error in class NewsServiceDbImpl", e);
            throw e;
        }
    }

    @Override
    public void deleteNews(int id) throws SQLException {
        String query = "DELETE FROM news_db.news WHERE id=?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setInt(1, id);
            prStmt.executeUpdate();

            logger.info("Connection in method deleteNews()");
        } catch (SQLException e) {
            logger.error("Error in class NewsServiceDbImpl", e);
            throw e;
        }
    }

    @Override
    public void editNews(News news) throws SQLException {
        String query = "UPDATE news_db.news SET head=?, briefly=?, full=? WHERE id=?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement prStmt = connection.prepareStatement(query)) {
            prStmt.setInt(4, news.getId());
            prStmt.setString(1, news.getHead());
            prStmt.setString(2, news.getBriefly());
            prStmt.setString(3, news.getFull());

            logger.info("Connection in method editNews()");

            prStmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error in class NewsServiceDbImpl", e);
            throw e;
        }
    }
}
