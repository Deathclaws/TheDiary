package com.deathclaws.thediary.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.HibernateException;
/*
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
*/

public class HibernateUtil {

//	private static SessionFactory sessionFactory = null;
	private static EntityManagerFactory entityManagerFactory = null;
	
	public static void initFactory() throws HibernateException {
		entityManagerFactory = Persistence.createEntityManagerFactory("thePersistenceUnit");
		/*
		Configuration configuration = new Configuration();
	    configuration.configure();
	    sessionFactory = new Configuration().configure().buildSessionFactory();
	    */
	}
/*
	public static Session getSession() {
		return sessionFactory.openSession();
	}
*/
	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
	
	public static void closeSessionFactory() {
		//sessionFactory.close();
		entityManagerFactory.close();
	}
}
