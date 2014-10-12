package com.moneyjarweb.statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.moneyjarweb.transaction.Transaction;

public class DuplicateManager {

	private List<Transaction> duplicates = null;
	private List<Transaction> originals = null;

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
	private List<Transaction> getOverlappingTransactions(
			List<Transaction> imported) {

		logger.debug(">> getOverlappingTransactions()");

		Date fromDate = getStartDate(imported);
		Date toDate = getEndDate(imported);

		List<Transaction> overlappingTransactions = null;

		TransactionDAO tdao = new TransactionDAO();
		overlappingTransactions = tdao.getDateRange(fromDate, toDate);

		logger.debug("<< getOverlappingTransactions() - retrieved "
				+ overlappingTransactions.size() + " transactions from '"
				+ fromDate + "' to '" + toDate);

		// Optimization - get end date for stored transactions
		// omit any transactions in comparison list to reduce
		// number of comparisons. Effective for typical cases.

		// What does this return if there are no overlapping transactions?

		return overlappingTransactions;
	}

	private Date getStartDate(List<Transaction> transactions) {

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

	private Date getEndDate(List<Transaction> transactions) {

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

	private void checkForDuplicates(List<Transaction> imported,
			List<Transaction> stored) {

		logger.debug(">> checkForDuplicates()");

		duplicates = new ArrayList<>();
		originals = new ArrayList<>();

		for (Transaction timport : imported) {
			for (Transaction simport : stored) {

				if (timport.getDate().equals(simport.getDate())
						&& timport.getDescription().equals(
								simport.getDescription())
						&& timport.getAmount().equals(simport.getAmount())) {
					duplicates.add(timport);
				} else {
					originals.add(timport);
				}
			}
		}

		logger.debug("<< checkForDuplicates - Originals: " + originals.size()
				+ " Duplicates: " + duplicates.size());
	}

	public List<Transaction> getDuplicates() {
		return duplicates;
	}

	public List<Transaction> getOriginals() {
		return originals;
	}

}
