package com.khominhvi.moneyjar.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.khominhvi.moneyjar.reports.CategoryTotal;

public class ReportsDao {
	
	private static Logger logger = Logger.getLogger(ReportsDao.class);
	private SessionFactory sessionFactory;
	
	public ReportsDao(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("rawtypes")
	public List<CategoryTotal> getCategoryTotals() {
		// TODO Add date range for category totals
		logger.debug("Get category totals called.");
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		String queryString = "SELECT new com.khominhvi.moneyjar.reports.CategoryTotal(c.name, c.color, SUM(t.amount)) "
								+ "FROM Transaction t, Category c "
								+ "WHERE t.category.id = c.id "
								+ "GROUP BY c.name, c.color"; 
								
		List<CategoryTotal> categoryTotals = new ArrayList<>();
		
		try {
            tx  = session.beginTransaction();
            Query query = session.createQuery(queryString);
            List results = query.list();
            logger.debug("Database results retrieved: " + results.size());
            logger.debug(results);
            for (Iterator itr = results.iterator(); itr.hasNext();){
            	categoryTotals.add((CategoryTotal) itr.next());
            }
		    tx.commit();
		} catch ( HibernateException e ) {
			logger.error("Could not complete SQL request." + e.getMessage());
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
		
		return categoryTotals;
	}

}
