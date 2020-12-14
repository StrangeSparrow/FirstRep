package com.mycorp.app.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private final static EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("News");

    public static EntityManager getManager() {
        return managerFactory.createEntityManager();
    }
}