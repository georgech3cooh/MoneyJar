package com.moneyjar;

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

import com.moneyjar.statement.TransactionDao;
import com.moneyjar.transaction.Transaction;

@Controller
public class TransactionHistoryController {

	@Autowired
	TransactionDao transactionDao;
	static Logger logger = Logger.getLogger(TransactionHistoryController.class);

	@RequestMapping(value = "/transactions", method = RequestMethod.GET)
	public String getTransactionHistoryRange(
			@RequestParam("from-date") String from, @RequestParam("to-date") String to,
			Model model) throws ParseException {

		logger.debug("Transaction history request called: from [" + from
				+ "] to [" + to + "]");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = sdf.parse(from);
		Date toDate = sdf.parse(to);
		
		List<Transaction> transactions = transactionDao.getDateRange(fromDate, toDate);
		logger.debug("Retrieved transactions " + transactions.size());
		
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		model.addAttribute("transactions", transactions);
		return "transaction-history";
	}

}
