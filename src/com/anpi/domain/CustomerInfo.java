package com.anpi.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.anpi.app.util.DateFormatterAdapter;



public class CustomerInfo {

	private String companyIdentifier;

	private String custNumber;
	
	private String customerType;

	private String invoiceNumber;
	
	private Date billFrom;
	private Date billTo;
	

	private Address ba;
	
	private Address ja;

	public String getCompanyIdentifier() {
		return companyIdentifier;
	}

	public void setCompanyIdentifier(String companyIdentifier) {
		this.companyIdentifier = companyIdentifier;
	}

	public String getCustNumber() {
		return custNumber;
	}

	public void setCustNumber(String custNumber) {
		this.custNumber = custNumber;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Address getBa() {
		return ba;
	}

	public void setBa(Address ba) {
		this.ba = ba;
	}

	public Address getJa() {
		return ja;
	}

	public void setJa(Address ja) {
		this.ja = ja;
	}

	 @XmlJavaTypeAdapter(DateFormatterAdapter.class)
	public Date getBillFrom() {
		return billFrom;
	}

	public void setBillFrom(Date billFrom) {
		this.billFrom = billFrom;
	}

	 @XmlJavaTypeAdapter(DateFormatterAdapter.class)
	public Date getBillTo() {
		return billTo;
	}

	public void setBillTo(Date billTo) {
		this.billTo = billTo;
	}
	
	
	

}
