package com.moneyjarweb.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(HibernateUtil.class);
	
	static {
		try{
			logger.debug(">> creating session factory");
			
			Configuration configuration = new Configuration().configure();
			StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
			ssrb.applySettings(configuration.getProperties());
			sessionFactory = configuration.buildSessionFactory(ssrb.build());
		 	
		 	logger.debug("-- session factory created");
		} catch (Throwable t) {
			logger.error("Hibernate exception:", t);
			t.printStackTrace();
			throw new ExceptionInInitializerError();
		}
	}
	
	public static SessionFactory getSessionFactory() {
		logger.debug("returning session factory");
		return sessionFactory;
	}
		
}
