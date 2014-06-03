package com.moneyjarweb.transaction;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

	private Date date;
	private String description;
	private BigDecimal amount;
	private BigDecimal amountIn;
	private BigDecimal amountOut;
	
	public Transaction(){
		this.description = "";
		this.date = new Date();
		this.amount = new BigDecimal("0.00");
	}
	
	public Transaction(String date, String desc, String amount) {
		setDate(date);
		this.description = desc;
		setAmount(amount);
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
		} catch (ParseException e) { }

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
	
	public BigDecimal getAmountIn() {
			return amountIn;
	}
	
	public BigDecimal getAmountOut() {
		if (amountOut != null){
			return amountOut.abs();
		} else {
			return null;
		}
	}
	
	public void setAmount(BigDecimal amount) {
		BigDecimal temp = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
		categorizeValue(temp);
		this.amount = temp;
		
	}
	
	public void setAmount(String amount) {
		BigDecimal temp = new BigDecimal(amount); 
		categorizeValue(temp);
		this.amount = temp;
	}
	
	public void categorizeValue(BigDecimal amount) {
		if (amount.compareTo(new BigDecimal("0.00")) <= 0) {
			this.amountOut = amount;
		} else {
			this.amountIn = amount;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result
				+ ((amountIn == null) ? 0 : amountIn.hashCode());
		result = prime * result
				+ ((amountOut == null) ? 0 : amountOut.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
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
		if (amountIn == null) {
			if (other.amountIn != null)
				return false;
		} else if (!amountIn.equals(other.amountIn))
			return false;
		if (amountOut == null) {
			if (other.amountOut != null)
				return false;
		} else if (!amountOut.equals(other.amountOut))
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
		return true;
	}



	
}
