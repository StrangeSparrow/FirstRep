package com.mycorp.app.group;

import com.mycorp.app.dao.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class GroupDao implements GroupService {
    private EntityManager manager = HibernateUtil.getManager();

    @Override
    public List<Group> fetchGroup() throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        List<Group> group = manager.createQuery("FROM Group").getResultList();
        manager.getTransaction().commit();
        manager.close();

        return group;
    }

    @Override
    public Group fetchSingleGroup(int id) throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        Query query = manager.createQuery("FROM Group WHERE id = :id");
        query.setParameter("id", id);
        Group group = (Group) query.getSingleResult();
        manager.getTransaction().commit();
        manager.close();

        return group;
    }

    @Override
    public void addGroup(Group group) throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        manager.persist(group);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void editGroup(Group group) throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        manager.merge(group);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void deleteGroup(int id) throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        Query query = manager.createQuery("DELETE Group WHERE id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }
}
