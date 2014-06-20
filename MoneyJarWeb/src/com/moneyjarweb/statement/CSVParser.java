package com.moneyjarweb.statement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import com.moneyjarweb.transaction.Transaction;

public class CSVParser implements StatementParser {

	private CSVReader csvReader;

	@Override
	public List<Transaction> parseStatement(File statement) throws Exception {

		// Read CSV file.
		ArrayList<String[]> csvData = readCSV(statement);

		// Convert CSV data to transaction objects
		List<Transaction> transactions = parseCSVData(csvData);

		return transactions;
	}

	private List<Transaction> parseCSVData(ArrayList<String[]> csvData)
			throws Exception {

		ArrayList<Transaction> transactions = new ArrayList<Transaction>();

		for (String[] column : csvData) {
			Transaction transaction = new Transaction();
			try {
				// TODO Replace numbers with constants?
				transaction.setDate(column[0]);
				transaction.setDescription(column[1]);
				transaction.setAmount(column[2]);
				transactions.add(transaction);
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new Exception(
							"Could not parse CSV data, " + 
							"file format may not be supported.", e);
			}
		}
		
		return transactions;
	}

	private ArrayList<String[]> readCSV(File statement) throws Exception {
		
		ArrayList<String[]> transactions = new ArrayList<String[]>();
		String[] nextLine;
		
		try {
			csvReader = new CSVReader(new FileReader(statement));
			while ((nextLine = csvReader.readNext()) != null) {
				transactions.add(nextLine);
			}
		} catch (FileNotFoundException e) {
			throw new Exception("The file \"" + 
								statement.getPath() + 
								"\" could not be found, " +  
								"please check that the file exists.", e);
			
		} catch (IOException e) {
			throw new Exception("Problem reading the file. " + 
								"The file may be corrupted.", e);
			
		} finally {
			try {
				if (csvReader != null)
					csvReader.close();
			} catch (IOException e) {
				throw new Exception("Problem closing the file, " + 
									"the file may be corrupted.", e);
			}
		}
		return transactions;
	}

}
