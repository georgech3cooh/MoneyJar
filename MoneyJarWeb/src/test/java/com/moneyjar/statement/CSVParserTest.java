package com.moneyjar.statement;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.moneyjar.statement.CSVParser;
import com.moneyjar.transaction.Transaction;

public class CSVParserTest {

	private File csvTestFileWithValidData;
	private File nonExistentFile;
	private CSVParser csvParser;
	List<Transaction> transactions;

	@Before
	public void setUp() {
		csvParser = new CSVParser();
		csvTestFileWithValidData = new File("testfile.csv");
		nonExistentFile = new File("nonExistentFile.csv");
	}

	@Test
	public void testParseStatementWithValidCSV() {
		try {
			transactions = csvParser.parseStatement(csvTestFileWithValidData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("Size of list", 3, transactions.size());
		assertEquals("Description of transaction", "test string 2",
				transactions.get(1).getDescription());
	}
	
	@Test(expected=Exception.class)
	public void testFileNotFoundException() throws Exception {
		csvParser.parseStatement(nonExistentFile);
	}

}
