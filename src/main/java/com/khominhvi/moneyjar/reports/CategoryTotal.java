package com.khominhvi.moneyjar.reports;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryTotal {
	private String name;
	private BigDecimal total;
	private String color;
	
	public CategoryTotal() {}
	
	public CategoryTotal(String name, String color, BigDecimal total) {
		this.name = name;
		this.color = color;
		this.total = total;
	}
	
	public String getName() {
		return name;
	}
	
	@JsonProperty("label")
	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	@JsonProperty("value")
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
}
