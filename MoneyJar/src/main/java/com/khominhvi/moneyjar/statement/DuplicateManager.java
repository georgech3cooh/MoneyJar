package com.khominhvi.moneyjar.statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.khominhvi.moneyjar.transaction.Transaction;

public class DuplicateManager {

	private List<Transaction> duplicates = null;
	private List<Transaction> unique = null;
	private TransactionDao transactionDao;

	Logger logger = Logger.getLogger(DuplicateManager.class);

	public void check(List<Transaction> imported) {

		logger.info(">> check() - checking for duplicates");

		List<Transaction> overlappingTransactions = getOverlappingTransactions(imported);

		if (overlappingTransactions != null) {
			checkForDuplicates(imported, overlappingTransactions);
			logger.info("<< check() - duplicates detected: "
					+ duplicates.size());
		} else {
			logger.info("No transactions within specified date range. No duplicates possible.");
		}

	}

	/**
	 *  Retrieves transactions that fall within the same date range as 
	 *  comparison list. 
	 */
	public List<Transaction> getOverlappingTransactions(
			List<Transaction> imported) {

		logger.debug(">> getOverlappingTransactions()");

		Date fromDate = getStartDate(imported);
		Date toDate = getEndDate(imported);

		List<Transaction> overlappingTransactions = null;

		overlappingTransactions = transactionDao.getDateRange(fromDate, toDate);

		logger.debug("<< getOverlappingTransactions() - retrieved "
				+ overlappingTransactions.size() + " transactions from '"
				+ fromDate + "' to '" + toDate);

		// Optimization - get end date for stored transactions
		// omit any transactions in comparison list to reduce
		// number of comparisons. Effective for typical cases.

		// What does this return if there are no overlapping transactions?

		return overlappingTransactions;
	}

	public Date getStartDate(List<Transaction> transactions) {

		logger.debug(">> getEarliestDate()");

		Date current = transactions.get(0).getDate();
		Date earliest = transactions.get(0).getDate();
		int result = 0;

		for (Transaction t : transactions) {
			current = t.getDate();
			result = current.compareTo(earliest);
			if (result < 0)
				earliest = current;
		}

		logger.debug("<< getEarliestDate() - returning " + earliest);
		return earliest;
	}

	public Date getEndDate(List<Transaction> transactions) {

		logger.debug(">> getLatestDate()");

		Date current = transactions.get(0).getDate();
		Date latest = transactions.get(0).getDate();
		int result = 0;

		for (Transaction t : transactions) {
			current = t.getDate();
			result = current.compareTo(latest);
			if (result > 0)
				latest = current;
		}

		logger.debug("<< getLatestDate() - returning " + latest);
		return latest;
	}

	public void checkForDuplicates(List<Transaction> importedTransactions,
			List<Transaction> storedTransactions) {

		logger.debug(">> checkForDuplicates()");

		duplicates = new ArrayList<Transaction>();
		unique = new ArrayList<Transaction>();

		for (Transaction toImport : importedTransactions) {
                if(storedTransactions.contains(toImport)) {
                        duplicates.add(toImport);
                } else {
                        unique.add(toImport);
                }
		}

		logger.debug("<< checkForDuplicates - Originals: " + unique.size()
				+ " Duplicates: " + duplicates.size());
	}

	public List<Transaction> getDuplicateTransactions() {
		return duplicates;
	}

	public List<Transaction> getUniqueTransactions() {
		return unique;
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	public TransactionDao getTransactionDao() {
		return transactionDao;
	}

}
