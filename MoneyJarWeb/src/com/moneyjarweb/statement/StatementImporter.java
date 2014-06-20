package com.moneyjarweb.statement;

import java.io.File;
import java.util.List;

import com.moneyjarweb.transaction.Transaction;

public class StatementImporter {
	
	private File statement;

	public StatementImporter() {}
	
	public void statementImport(File statement) throws Exception {
		
		this.statement = statement;
		
		// 1. Determine the type of file
		String fileExtension = fileType(statement);

		// 2. Call correct parser
		StatementParser parser;
		if (fileExtension.equals("csv")) {
			 parser = new CSVParser();
		} else {
			// Default parser
			parser = new CSVParser();
		}
		
		// 3. Parse file to transactions
		List<Transaction> transactions = parser.parseStatement(statement);
	
		// 4. Commit transactions to database
		
	}
	
	public String fileType(File file){
		String name = file.getName(); 		
		int dot = name.lastIndexOf('.');

		return name.substring(dot + 1);
	}
	
}
