package com.anpi.app.model;

import java.util.Date;

public class TaxInvoice {
	
	private String taxCode;
	private int taxId;
	private String taxTypeName;
	private String transTypeName;
	private double transAmount;
	private double transTaxPercent;
	private double transAmountConsidered;
//	private double amount;
//	private String date;
	private String invoiceNumber;
	
	private Date billFrom;
	private Date billTo;
	
	
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public int getTaxId() {
		return taxId;
	}
	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}
	public String getTaxTypeName() {
		return taxTypeName;
	}
	public void setTaxTypeName(String taxTypeName) {
		this.taxTypeName = taxTypeName;
	}
	public String getTransTypeName() {
		return transTypeName;
	}
	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}
	public double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}
	public double getTransTaxPercent() {
		return transTaxPercent;
	}
	public void setTransTaxPercent(double transTaxPercent) {
		this.transTaxPercent = transTaxPercent;
	}
	public double getTransAmountConsidered() {
		return transAmountConsidered;
	}
	public void setTransAmountConsidered(double transAmountConsidered) {
		this.transAmountConsidered = transAmountConsidered;
	}
	@Override
	public String toString() {
		return "TaxInvoice [taxCode=" + taxCode + ", taxId=" + taxId + ", taxTypeName=" + taxTypeName + ", transTypeName=" + transTypeName + ", transAmount=" + transAmount + ", transTaxPercent="
				+ transTaxPercent + ", transAmountConsidered=" + transAmountConsidered + ", invoiceNumber=" + invoiceNumber + ", billFrom=" + billFrom + ", billTo=" + billTo + "]";
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Date getBillFrom() {
		return billFrom;
	}
	public void setBillFrom(Date billFrom) {
		this.billFrom = billFrom;
	}
	public Date getBillTo() {
		return billTo;
	}
	public void setBillTo(Date billTo) {
		this.billTo = billTo;
	}
	
	

}
