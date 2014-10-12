package com.moneyjarweb.statement;

import java.io.File;
import java.util.List;

import com.moneyjarweb.transaction.Transaction;

public interface StatementParser {

	public List<Transaction> parseStatement(File statement) throws Exception;
	
}
