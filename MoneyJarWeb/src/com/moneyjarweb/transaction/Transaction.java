package com.moneyjarweb.transaction;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class Transaction {

	private long id;
	private Date date;
	private String description;
	private BigDecimal amount;
	private Logger logger;
	
	public Transaction(){
		logger = Logger.getLogger(Transaction.class);
		this.description = "";
		this.date = new Date();
		this.amount = new BigDecimal("0.00");
	}
	
	public Transaction(String date, String desc, String amount) {
		
		logger = Logger.getLogger(Transaction.class);
		logger.debug(">> Transaction() - Creating object: " 
				+ date + ", " + desc + ", " + amount);
		
		setDate(date);
		this.description = desc;
		setAmount(amount);
		
		logger.debug("<< Transaction() - Transaction object created");
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date pdate = null;
		try {
			pdate = sdf.parse(date);
		} catch (ParseException e) { 
			logger.error(">> setDate() -  could not parse date string", e);
		}
		
		this.date = pdate;		
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		BigDecimal temp = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.amount = temp;
	}
	
	public void setAmount(String amount) {
		BigDecimal temp = new BigDecimal(amount); 
		this.amount = temp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	
}
