package com.khominhvi.moneyjar.statement;

import java.io.File;
import java.util.List;

import com.khominhvi.moneyjar.transaction.Transaction;

public interface StatementParser {

	public List<Transaction> parseStatement(File statement) throws Exception;
	
}
