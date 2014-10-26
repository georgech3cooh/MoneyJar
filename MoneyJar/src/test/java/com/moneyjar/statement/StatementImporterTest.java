package com.moneyjar.statement;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class StatementImporterTest {
	
	TransactionDAO tdao;
	StatementImporter statementImporter;
	File testFile;
	
	@Before
	public void setUp() {
		statementImporter = new StatementImporter();
		testFile = new File("testfile.csv");
		tdao = mock(TransactionDAO.class);
	}
		
	@Test
	public void testGetFileExtension() {
		String extension = statementImporter.getFileExtension(testFile);
		assertEquals("csv", extension);
	}
	
	@Test
	public void testGetParser() {
		StatementParser parser = statementImporter.getParser("csv");
		assertThat(parser, instanceOf(CSVParser.class));
	}

	@Test
	public void testDefaultParser() {
		StatementParser parser = statementImporter.getParser("undefinedExtension");
		assertThat(parser, instanceOf(CSVParser.class));
	}
	
	@Test
	public void testImportStatement() throws Exception {
		doNothing().when(tdao).create(any(List.class));
		MockitoAnnotations.initMocks(tdao);	
		
		statementImporter.setTdao(tdao);
		statementImporter.importStatement(testFile);
		
		verify(tdao).create(any(List.class));
	}
	
}