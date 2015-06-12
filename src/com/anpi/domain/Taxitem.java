package com.anpi.domain;

import javax.xml.bind.annotation.XmlAttribute;

public class Taxitem {
	
	private String tax;
	private String rate;
	private String amount;
	private String taxable_measure;
	
        
        @XmlAttribute(name="name") 
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTaxable_measure() {
		return taxable_measure;
	}
	public void setTaxable_measure(String taxable_measure) {
		this.taxable_measure = taxable_measure;
	}
	
	

}
