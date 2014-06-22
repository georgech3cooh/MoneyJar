package com.moneyjarweb.transaction;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class TransactionTest {

	Transaction transaction;

	@Before
	public void setUp() {
		transaction = new Transaction();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testAddNewTransactionData() {
		Date date = new Date(2013, 01, 01);
		String description = "This is a test string";
		BigDecimal amount = new BigDecimal(123.45);

		transaction.setDate(date);
		transaction.setDescription(description);
		transaction.setAmount(amount);

		assertEquals(new Date(2013, 01, 01), transaction.getDate());
		assertEquals("This is a test string", transaction.getDescription());
		assertEquals("123.45", transaction.getAmount().toString());
	}

	@Test
	public void testAddNewStringTransaction() {
		Transaction t = new Transaction("2013-01-01", "Test", "12.00");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = sdf.parse("2013-01-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertEquals(date, t.getDate());
		assertEquals("Test", t.getDescription());
		assertEquals("12.00", t.getAmount().toString());
	}

	@Test
	public void testTransactionHashFunction() {
		Transaction t1 = new Transaction("2013-01-01", "Test abc xyz", "123.00");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = sdf.parse("2013-01-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		BigDecimal amount = new BigDecimal(123.00);
		Transaction t2 = new Transaction();
		t2.setDate(date);
		t2.setAmount(amount);
		t2.setDescription("Test abc xyz");

		assertEquals(t1.getDate(), t2.getDate());
		assertEquals(t1.getAmount(), t2.getAmount());
		assertEquals(t1.getDescription(), t2.getDescription());
		assertEquals("Hashcode Test", t1.hashCode(), t2.hashCode());
		assertEquals(true, t1.equals(t2));
		assertEquals(false, t1 == t2);

	}

	@Test
	public void testTransactionEqualsMethod() {
		Transaction t1 = new Transaction("2013-01-01", "Test abc xyz", "123.00");
		Transaction t2 = new Transaction("2013-01-01", "Test abc xyz", "123.00");
		Transaction t3 = new Transaction("2013-01-01", "Test abc xyz ",
				"123.00");
		Transaction t4 = new Transaction();
		t4.setDescription(null);
		Date d1 = null;
		t4.setDate(d1);
		String s1 = "not a matching object";
		assertEquals(true, t1 != t2);
		assertEquals(true, t1.equals(t1));
		assertEquals(true, t1.equals(t2));
		assertEquals(false, t1.equals(t3));
		assertEquals(false, t1.equals(s1));
		assertEquals(false, t1.equals(null));
		assertEquals(false, t4.equals(t1));
	}

	@Test
	public void testAmountPrecision() {
		transaction.setAmount(new BigDecimal(123.4));
		assertEquals(
				new BigDecimal(123.40).setScale(2, BigDecimal.ROUND_HALF_UP),
				transaction.getAmount());
	}

}
