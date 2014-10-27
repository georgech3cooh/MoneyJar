package com.moneyjar.statement;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.moneyjar.transaction.Transaction;


@RunWith(MockitoJUnitRunner.class)
public class StatementImporterTest {
	
	TransactionDao tdao;
	StatementImporter statementImporter;
	File testFile;
	
	@Before
	public void setUp() {
		statementImporter = new StatementImporter();
		testFile = new File("testfile.csv");
		tdao = mock(TransactionDao.class);
	}
		
	@Test
	public void testGetFileExtension() {
		String extension = statementImporter.getFileExtension(testFile);
		assertEquals("csv", extension);
	}
	
	@Test
	public void testGetParser() {
		StatementParser parser = statementImporter.getParser("csv");
		assertThat(parser, instanceOf(CsvParser.class));
	}

	@Test
	public void testDefaultParser() {
		StatementParser parser = statementImporter.getParser("undefinedExtension");
		assertThat(parser, instanceOf(CsvParser.class));
	}
	
	@Test
	public void testImportStatement() throws Exception {
		doNothing().when(tdao).create(anyListOf(Transaction.class));
		
		statementImporter.setTransactionDao(tdao);
		statementImporter.importStatement(testFile);
		
		verify(tdao).create(anyListOf(Transaction.class));
	}
	
}