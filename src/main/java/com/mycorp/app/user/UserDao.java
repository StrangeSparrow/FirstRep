package com.mycorp.app.user;

import com.mycorp.app.dao.HibernateUtil;
import com.mycorp.app.permission.Permission;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDao implements UserService {
    private EntityManager manager;

    @Override
    public List<User> fetchUsers() throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        List<User> users = manager.createQuery("FROM User").getResultList();
        manager.getTransaction().commit();
        manager.close();

        return users;
    }

    @Override
    public User fetchSingleUser(int id) throws SQLException {
        manager = HibernateUtil.getManager();
        Query query = manager.createQuery("FROM User WHERE id = :id");
        query.setParameter("id", id);
        manager.getTransaction().begin();
        User user = (User) query.getSingleResult();
        manager.getTransaction().commit();
        manager.close();

        return user;
    }

    @Override
    public void addUser(User user) throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        user.setUserGroup(manager.merge(user.getUserGroup()));
        manager.persist(user);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void editUser(User user) throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        manager.merge(user);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        Query query = manager.createQuery("DELETE User WHERE id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public Set<String> getUserRoles(int id) {
        User user = null;
        try {
            user = fetchSingleUser(id);
        } catch (SQLException e) {}

        Set<String> roles = new HashSet<>();

        for (Permission p : user.getUserGroup().getPermission()) {
            roles.add(p.getName());
        }
        return roles;
    }

    @Override
    public String issueToken(int id) {
        String token = RandomStringUtils.random(20, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");

        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        Query query = manager.createQuery("UPDATE User SET token = :token WHERE id = :id");
        query.setParameter("token", token);
        query.setParameter("id", id);
        query.executeUpdate();
        manager.getTransaction().commit();
        manager.close();

        return token;
    }

    @Override
    public User authenticate(String username, String password) throws Exception {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        Query query = manager.createQuery("FROM User WHERE login = :login AND password = :password");
        query.setParameter("login", username);
        query.setParameter("password", password);
        User user = (User) query.getSingleResult();
        manager.getTransaction().commit();
        manager.close();

        if (user != null) {
            return user;
        } else {
            throw new Exception();
        }
    }

    @Override
    public void deleteToken(String token) {
        manager = HibernateUtil.getManager();
        manager.getTransaction().begin();
        Query query = manager.createQuery("UPDATE User SET token = NULL WHERE token = :token");
        query.setParameter("token", token);
        query.executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }
}
