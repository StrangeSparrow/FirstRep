package com.mycorp.app.news;

import com.mycorp.app.dao.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class NewsDao implements NewsService {
    private EntityManager manager;

    @Override
    public List<News> fetchNews() throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        List<News> news = manager.createQuery("FROM News").getResultList();
        manager.getTransaction().commit();
        manager.close();

        Collections.reverse(news);

        return news;
    }

    @Override
    public News fetchSingleNews(int id) throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        Query query = manager.createQuery("FROM News WHERE id = :id");
        query.setParameter("id", id);
        News news = (News) query.getSingleResult();
        manager.getTransaction().commit();
        manager.close();

        return news;
    }

    @Override
    public void addNews(News news) throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        manager.persist(news);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void deleteNews(int id) throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        Query query = manager.createQuery("DELETE News WHERE id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void editNews(News news) throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        manager.merge(news);
        manager.getTransaction().commit();
        manager.close();
    }
}
