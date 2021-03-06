package com.khominhvi.moneyjar.hibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.khominhvi.moneyjar.transaction.Transaction;

public class TransactionDao {

	private Logger logger = Logger.getLogger(TransactionDao.class);
	private SessionFactory sessionFactory;
	
	public TransactionDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void create(List<Transaction> transactions) {

		logger.debug(">> create()");

		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx = null;

		try {
			tx = session.beginTransaction();
			for (Transaction t : transactions) {
				logger.debug("-- Saving transaction to database: " + t);
				session.save(t);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			logger.error("-- create() - could not save list to database ", e);
		} finally {
			session.close();
		}

		logger.debug("<< create()");
	}

	@SuppressWarnings("rawtypes")
	public List<Transaction> getDateRange(Date from, Date to) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fromDate = sdf.format(from);
		String toDate = sdf.format(to);
		
		logger.debug(">> getDateRange() - from '" + fromDate + "' to '"
				+ toDate + "'");

		List<Transaction> transactions = new ArrayList<>();
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx = null;
		Query query = null;
		String queryString = "FROM Transaction WHERE date BETWEEN '" 
				+ fromDate + "' and '" + toDate + "'";
		try {
			tx = session.beginTransaction();
			query = session.createQuery(queryString);
			List results = query.list();
			logger.debug("Retrieved " + results.size() + " results from database.");
			for (Iterator itr = results.iterator(); itr.hasNext();) {
				transactions.add((Transaction) itr.next());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		logger.debug("returning results: " + transactions.size());
		return transactions;
	}

	public int bulkUpdate(String category, String[] selection) {
		
		List<Long> ids = QueryStringUtil.stringArrayToLongList(selection);
		
		String updateString = "UPDATE Transaction SET category_id=" + 
					"(SELECT id FROM Category WHERE name =:category)" +
					"WHERE id IN (:transactionIds)";
		
		logger.debug("Querying database with: " + updateString );
		
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx = null;
		int result = 0;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(updateString);
			query.setParameter("category", category);
			query.setParameterList("transactionIds", ids); // Not working
			result = query.executeUpdate();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		logger.debug("Records affected: " + result);
		return result;
	}
}
