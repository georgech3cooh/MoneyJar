package com.moneyjar.statement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.moneyjar.transaction.Transaction;

public class CsvParser implements StatementParser {

	private CSVReader csvReader;
	private Logger logger;

	public CsvParser() {
		logger = Logger.getLogger(CsvParser.class);
	}

	@Override
	public List<Transaction> parseStatement(File statement) throws Exception {

		logger.info(">> parseStatement() - parsing file: "
				+ statement.getName());

		ArrayList<String[]> csvContent = readCSVFile(statement);
		List<Transaction> transactions = parseCSVData(csvContent);

		logger.info("<< parseStatement() - returning " + transactions.size()
				+ " transactions");

		return transactions;
	}

	private ArrayList<String[]> readCSVFile(File statement) throws Exception {

		logger.debug(">> readCSVFile() - reading file: " + statement.getName());

		ArrayList<String[]> transactions = new ArrayList<String[]>();
		String[] nextLine;

		try {
			csvReader = new CSVReader(new FileReader(statement));
			while ((nextLine = csvReader.readNext()) != null) {
				transactions.add(nextLine);
			}
		} catch (FileNotFoundException e) {
			String errorMsg = "The file \""
					+ statement.getPath()
					+ "\" could not be found, please check that the file exists.";
			logger.warn(errorMsg);
			throw new Exception(errorMsg, e);

		} catch (IOException e) {
			String errorMsg = "Problem reading the file. The file may be corrupted.";
			logger.error(errorMsg, e);
			throw new Exception(errorMsg, e);

		} finally {
			try {
				if (csvReader != null)
					csvReader.close();
			} catch (IOException e) {
				String errorMsg = "Problem closing the file, the resource may not be available.";
				logger.error(errorMsg, e);
				throw new Exception(errorMsg, e);
			}
		}

		logger.debug("<< readCSVFile() - successfully read "
				+ transactions.size() + " entries");

		return transactions;
	}

	private List<Transaction> parseCSVData(ArrayList<String[]> csvData)
			throws Exception {

		logger.debug(">> parseCSVData() - parsing " + csvData.size()
				+ " entries");

		ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
		for (String[] entry : csvData) {
			Transaction transaction = new Transaction();
			try {
				// TODO Replace magic numbers with constants/property values.
				transaction.setDate(entry[0]);
				transaction.setDescription(entry[1]);
				transaction.setAmount(entry[2]);
				transactionList.add(transaction);
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new Exception("Could not parse CSV data, "
						+ "file format may not be supported.", e);
			}
		}

		logger.debug("<< parseCSVData() - successfully parsed "
				+ transactionList.size() + " transactions");

		return transactionList;
	}
}
