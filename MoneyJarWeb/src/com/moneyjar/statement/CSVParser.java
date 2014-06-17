package com.moneyjar.statement;

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
	public List<Transaction> parseStatement(File statement) {
		
		// Read CSV file.
		ArrayList<String[]> csvData = readCSV(statement);
		
		// Convert CSV data to transaction objects
		List<Transaction> transactions = parseCSVData(csvData);
		
		return transactions;
	}
	
	private List<Transaction> parseCSVData(ArrayList<String[]> csvData) {
		
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		
		for(String[] column : csvData){
			Transaction transaction = new Transaction();
			try {
				//TODO Replace numbers with constants?
				transaction.setDate(column[0]);
				transaction.setDescription(column[1]);
				transaction.setAmount(column[2]);
				transactions.add(transaction);
			} catch (ArrayIndexOutOfBoundsException e) {
				// log parsing exception.
			}
		}
		return transactions;
		
	}

	private ArrayList<String[]> readCSV(File statement){
		ArrayList<String[]> transactions = new ArrayList<String[]>();
		String[] nextLine;
		try {
			csvReader = new CSVReader(new FileReader(statement));
			while ((nextLine = csvReader.readNext()) != null) {
		    	transactions.add(nextLine);
		    }
		  } catch (FileNotFoundException e) {
			  // Log file not found
		  } catch (IOException e) {
			  // Log IO exception
		  } finally {
			  try {
		    	if(csvReader != null) csvReader.close();
			  } catch (IOException e) {
				  System.out.println("Could not close the file.");
			  }
		}
		return transactions;
	}

}
