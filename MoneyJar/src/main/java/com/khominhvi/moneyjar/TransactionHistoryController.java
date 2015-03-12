package com.khominhvi.moneyjar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khominhvi.moneyjar.hibernate.CategoryDao;
import com.khominhvi.moneyjar.hibernate.TransactionDao;
import com.khominhvi.moneyjar.transaction.Category;
import com.khominhvi.moneyjar.transaction.Transaction;

@Controller
public class TransactionHistoryController {

	@Autowired
	private TransactionDao transactionDao;
	@Autowired
	private CategoryDao categoryDao;
	private static Logger logger = Logger
			.getLogger(TransactionHistoryController.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping(value = "/transactions", method = RequestMethod.GET)
	public String getTransactionHistoryRange(
			@RequestParam("from-date") String from,
			@RequestParam("to-date") String to, Model model)
			throws ParseException {

		logger.debug("Transaction history request called: from [" + from
				+ "] to [" + to + "]");

		List<Transaction> transactions = getTransactionHistoryRange(from, to);
		List<Category> categories = categoryDao.getCategories();

		model.addAttribute("from", from);
		model.addAttribute("to", to);
		model.addAttribute("transactions", transactions);
		model.addAttribute("categories", categories);
		return "transaction-history";
	}

	@RequestMapping(value = "/transactions", produces = { "application/json" })
	public @ResponseBody List<Transaction> getTransactionHistoryRange(
			@RequestParam("from-date") String from,
			@RequestParam("to-date") String to) throws ParseException {

		logger.debug("JSON transaction history request called.");
		Date fromDate = sdf.parse(from);
		Date toDate = sdf.parse(to);

		List<Transaction> transactions = transactionDao.getDateRange(fromDate, toDate);
		logger.debug("Retrieved transaction " + transactions.size());
		return transactions;
	}	
	
	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	public String setBulkCategoriesView(
			@RequestParam("selection") String[] selection, 
			@RequestParam("category") String category,
			@RequestParam("from") String from,
			@RequestParam("to") String to,
			Model model) throws ParseException {
		
		logger.debug("executing bulk update.");
		int result = transactionDao.bulkUpdate(category, selection);
		model.addAttribute("message", "Records changed: " + result);
		
		logger.debug("retrieving updated entries");
		List<Transaction> transactions = getTransactionHistoryRange(from, to);
		List<Category> categories = categoryDao.getCategories();
		
		logger.debug("adding data to model");
		model.addAttribute("transactions", transactions);
		model.addAttribute("categories", categories);
		
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		
		logger.debug("returning view.");
		return "transaction-history";
		
	}

	/* This is wrong - how to perform bulk category change using RESTful api?
	 * 
	 * @RequestMapping(value = "/transactions", produces = { "application/json" })
	public @ResponseBody String setBulkCategoriesJson(
			@RequestParam String[] selection, 
			@RequestParam String category) {
		
		logger.debug("Setting category '" + category + "' for transactions: " + selection);
		// How to get id from category name?
		
		// How would you like to code it up?
		transactionDao.bulkUpdate(category, selection);
		
		String message = "Changed " + selection.length + "transactions: " + category;
		return message;
	}
	 */
	
}