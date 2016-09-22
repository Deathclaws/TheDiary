/**
 * 
 */
package com.deathclaws.thediary.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Contains utility methods 
 * 
 * @author srccodes.com
 * @version 1.0
 *
 */
public class HibernateUtil {
	
	private static SessionFactory sessionFactory = null;  
	  
	private static SessionFactory configureSessionFactory() throws HibernateException {  
	    Configuration configuration = new Configuration();  
	    configuration.configure();      
	    sessionFactory = new Configuration().configure().buildSessionFactory();
	    return sessionFactory;  
	}
	
	static {
		configureSessionFactory();
	}
	
	private HibernateUtil() {}
	
	public static Session getSession() {
		return sessionFactory.openSession();
	}
}
