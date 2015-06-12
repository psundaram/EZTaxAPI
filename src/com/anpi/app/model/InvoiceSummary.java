package com.anpi.app.model;

public class InvoiceSummary {
	
	private String description;
	
	private double value;
	
	private String invoiceNumber;
	 
	private double total;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Summary [description=" + description + ", value=" + value + ", invoiceNumber=" + invoiceNumber + ", total=" + total + "]";
	}
	

}
