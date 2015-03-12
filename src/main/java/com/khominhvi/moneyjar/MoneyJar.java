package com.khominhvi.moneyjar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.khominhvi.moneyjar.hibernate.CategoryDao;
import com.khominhvi.moneyjar.hibernate.TransactionDao;
import com.khominhvi.moneyjar.transaction.Category;
import com.khominhvi.moneyjar.transaction.Transaction;

@Controller
public class MoneyJar {

	@Autowired
	private TransactionDao transactionDao;
	@Autowired
	private CategoryDao categoryDao;
	private Logger logger = Logger.getLogger(MoneyJar.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		logger.info("MoneyJar Controller called");

		// Create default from and to date and pass it to transaction history.
		Calendar current = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date toDate = current.getTime();
		String to = sdf.format(toDate);
		logger.debug("Retrieving current date: " + to);

		current.add(Calendar.MONTH, -1);
		Date fromDate = current.getTime();
		String from = sdf.format(fromDate);
		logger.debug("Calculating date of previous month: " + from);

		model.addAttribute("from", from);
		model.addAttribute("to", to);
		
		List<Category> categories = categoryDao.getCategories();
		model.addAttribute("categories",categories);

		// Get transactions for the period
		List<Transaction> transactions = transactionDao.getDateRange(fromDate,
				toDate);
		logger.debug("Retrieving transactions from " + from + " to " + to
				+ " : " + transactions.size());
		model.addAttribute("transactions", transactions);

		return "transaction-history";
	}

}
