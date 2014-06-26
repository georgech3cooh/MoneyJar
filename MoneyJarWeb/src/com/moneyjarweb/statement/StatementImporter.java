package com.moneyjarweb.statement;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.moneyjarweb.transaction.Transaction;

public class StatementImporter {

	private Logger logger = Logger.getLogger(StatementImporter.class);

	public void statementImport(File statement) throws Exception {
		logger.debug(">> statementImport()");
		logger.info("-- statementImport() -- Importing file:"
				+ statement.getName());

		String fileExtension = getFileExtension(statement);
		StatementParser parser = getParser(fileExtension);

		List<Transaction> transactions = null;
		if (parser != null) {
			transactions = parser.parseStatement(statement);
		}

		if (transactions != null) {
			TransactionDAO tdao = new TransactionDAO();
			tdao.create(transactions);
		}
	}

	public String getFileExtension(File file) {

		logger.debug(">> fileType() - retrieving file type");

		String name = file.getName();
		int dot = name.lastIndexOf('.');
		String extension = name.substring(dot + 1);

		logger.debug("<< fileType() - returning '" + extension
				+ "' as file extension");

		return extension;
	}

	public StatementParser getParser(String fileExtension) {

		logger.debug(">> getParser() -- retrieving correct parser for extension:"
				+ fileExtension);

		// Can be replaced with factory pattern as new parsers are added to
		// project
		StatementParser parser = null;
		if (fileExtension.equals("csv")) {
			parser = new CSVParser();
		} else {
			// Default parser
			parser = new CSVParser();
		}

		logger.debug("<< getParser() -- returning parser: " + parser.getClass());
		return parser;
	}

}
