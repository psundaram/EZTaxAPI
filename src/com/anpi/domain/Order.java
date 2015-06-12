package com.anpi.domain;

import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class Order {
	
	
	@SerializedName("OrderID")
	private String orderId;
	
	private int retryTrace;
	
	@SerializedName("Amount")
	private String amount;

	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getRetryTrace() {
		return retryTrace;
	}

	public void setRetryTrace(int retryTrace) {
		this.retryTrace = retryTrace;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
