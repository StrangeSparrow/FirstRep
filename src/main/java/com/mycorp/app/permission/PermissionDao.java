package com.mycorp.app.permission;

import com.mycorp.app.dao.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class PermissionDao implements PermissionService {
    private EntityManager manager = HibernateUtil.getManager();

    @Override
    public List<Permission> fetchPermissions() throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        List<Permission> permissions = manager.createQuery("FROM Permission").getResultList();
        manager.getTransaction().commit();
        manager.close();

        return permissions;
    }

    @Override
    public Permission fetchSinglePermission(int id) throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        Query query = manager.createQuery("FROM Permission WHERE id = :id");
        query.setParameter("id", id);
        Permission permission = (Permission) query.getSingleResult();
        manager.getTransaction().commit();
        manager.close();

        return permission;
    }
}
