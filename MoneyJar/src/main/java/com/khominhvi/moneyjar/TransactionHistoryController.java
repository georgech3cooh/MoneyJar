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

import com.khominhvi.moneyjar.hibernate.TransactionDao;
import com.khominhvi.moneyjar.transaction.Transaction;

@Controller
public class TransactionHistoryController {

	@Autowired
	TransactionDao transactionDao;
	static Logger logger = Logger.getLogger(TransactionHistoryController.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 

	@RequestMapping(value = "/transactions", method = RequestMethod.GET)
	public String getTransactionHistoryRange(
			@RequestParam("from-date") String from,
			@RequestParam("to-date") String to, Model model)
			throws ParseException {

		logger.debug("Transaction history request called: from [" + from
				+ "] to [" + to + "]");

		List<Transaction> transactions = retrieveTransactions(from, to);

		model.addAttribute("from", from);
		model.addAttribute("to", to);
		model.addAttribute("transactions", transactions);
		return "transaction-history";
	}

	@RequestMapping(value="/transactions", produces={"application/json"})
	public @ResponseBody List<Transaction> getTransactionHistoryRange(@RequestParam("from-date") String from,
			@RequestParam("to-date") String to) throws ParseException{
		logger.debug("JSON transaction history request called.");
		
		List<Transaction> transactions = retrieveTransactions(from, to);
		
		return transactions;
	}
	
	// Utility Method - Used to retrieve transactions from database
	private List<Transaction> retrieveTransactions(String from, String to) throws ParseException {
		List<Transaction> transactions = null;
		Date fromDate = sdf.parse(from);
		Date toDate = sdf.parse(to);
		
		transactions = transactionDao.getDateRange(fromDate, toDate);
		logger.debug("Retrieved transaction " + transactions.size());
		
		return transactions;
	}
}