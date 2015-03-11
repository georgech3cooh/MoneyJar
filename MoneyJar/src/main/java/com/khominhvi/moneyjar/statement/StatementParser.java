package com.moneyjar.statement;

import java.io.File;
import java.util.List;

import com.moneyjar.transaction.Transaction;

public interface StatementParser {

	public List<Transaction> parseStatement(File statement) throws Exception;
	
}
