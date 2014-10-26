package com.moneyjar.statement;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.moneyjar.transaction.Transaction;

@RunWith(MockitoJUnitRunner.class)
public class DuplicateManagerTest {

	private DuplicateManager duplicateManager;
	private TransactionDAO tdao;
	private List<Transaction> transactionsList;
	private List<Transaction> comparedList;
	
	@Before
	public void setUp() throws Exception {
		duplicateManager = new DuplicateManager();
		tdao = mock(TransactionDAO.class);
		transactionsList = new ArrayList<>();
		transactionsList.add(new Transaction("2014-10-01","Description 001","10.00"));
		transactionsList.add(new Transaction("2014-10-21","Description 002","11.00"));
		transactionsList.add(new Transaction("2014-10-03","Description 003","12.00"));
		transactionsList.add(new Transaction("2014-10-31","Description 004","13.00"));
		
		comparedList = new ArrayList<>();
		comparedList.add(new Transaction("2014-10-02","Description 001","10.00"));
		comparedList.add(new Transaction("2014-10-21","Description 002","11.00"));
		comparedList.add(new Transaction("2014-10-05","Description 003","12.00"));
		comparedList.add(new Transaction("2014-10-30","Description 004","13.00"));	
	}

	@Test
	public void testGetOverlappingTransactions() {
	List<Transaction> overlappingTs;
		when(tdao.getDateRange(any(Date.class), any(Date.class))).thenReturn(new ArrayList<Transaction>());
		duplicateManager.setTransactionDao(tdao);
		List<Transaction> results = duplicateManager.getOverlappingTransactions(transactionsList);
		// What am I trying to achieve?
		// Verifies that tdao method is called to retrieve transactions.
		// Not very interesting...
		verify(tdao).getDateRange(any(Date.class), any(Date.class));
	}
	
	@Test
	@Ignore
	public void testGetStartDate() {
	}
	
	
	@Test
	@Ignore
	public void testGetEndDate() {
	}
	
	
	@Test
	public void testCheckForDuplicates() {
		duplicateManager.checkForDuplicates(transactionsList, comparedList);
		assertTrue(duplicateManager.getDuplicateTransactions().size() == 1);
		assertTrue(duplicateManager.getUniqueTransactions().size() == 3);
	}
	
	
}
