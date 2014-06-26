package com.moneyjarweb.statement;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.moneyjarweb.hibernate.HibernateUtil;
import com.moneyjarweb.transaction.Transaction;

public class TransactionDAO {
	
	private Logger logger = Logger.getLogger(TransactionDAO.class);
	
	public void create(List<Transaction> transactions) {
		
		logger.debug(">> create()");
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx = null;
		
		try{
			tx = session.beginTransaction();
			for(Transaction t : transactions) {
				logger.debug("-- Saving transaction to database: " + t);
				session.save(t);
			}
			tx.commit();
		} catch (HibernateException e) {
			if(tx!=null) tx.rollback();
			logger.error("-- create() - could not save list to database ", e);
		} finally {
			session.close();
		}
		
		logger.debug("<< create()");		
	}
}
