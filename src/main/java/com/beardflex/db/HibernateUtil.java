package com.beardflex.db;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by David on 16/03/2017.
 */
public class HibernateUtil {
    private static HibernateUtil instance;
    private HibernateUtil() throws HibernateException {
        cfg = new Configuration().configure();
        serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties())
                .build();
        sessionFactory = cfg.buildSessionFactory(serviceRegistry);
    }
    public static synchronized HibernateUtil get() throws HibernateException {
        if(instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    private Configuration cfg;
    private SessionFactory sessionFactory;
    private ServiceRegistry serviceRegistry;

    public Session getSession() throws HibernateException {
        Session session = sessionFactory.openSession();
        if(!session.isConnected()) {
            reconnect();
        }
        return session;
    }

    private void reconnect() throws HibernateException {
        sessionFactory = cfg.buildSessionFactory(serviceRegistry);
    }
}
