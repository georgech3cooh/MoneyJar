package com.moneyjar.statement;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.moneyjarweb.transaction.Transaction;

public class CSVParserTest {

	private File csvTestFileWithValidData;
	private CSVParser csvParser;
	List<Transaction> transactions;

	@Before
	public void setUp() {
		csvParser = new CSVParser();
		csvTestFileWithValidData = new File("testfile.csv");
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

}
