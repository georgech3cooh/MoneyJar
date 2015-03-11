package com.khominhvi.moneyjar.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.common.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.khominhvi.moneyjar.transaction.Category;

public class CategoryDao {
	
	private static Logger logger = Logger.getLogger(CategoryDao.class);

	@SuppressWarnings("rawtypes")
	public List<Category> getCategories() {
		

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		Transaction tx = null;
		Query query = null;
		List<Category> categories = new ArrayList<>();
		String queryString = "FROM Category";
		
		try {
			tx = session.beginTransaction();
			query = session.createQuery(queryString);
			List results = query.list();
			logger.debug("Retrieved " + results.size() + " categories from database." );
			
			for(Iterator itr = results.iterator(); itr.hasNext();){
				categories.add((Category) itr.next());
			}
		} catch (HibernateException e) {
			if (tx != null) 
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		logger.debug("returning results: " + categories.size());
		return categories;
	}

}
