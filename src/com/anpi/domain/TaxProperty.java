package com.anpi.domain;

import java.util.Date;
import java.util.List;

public class TaxProperty {

	private String invoiceNumber;
	private String city;
	private Date billFrom;
	private Date billTo;
	private String customerId;
	private String partnerId;
	private String crmProductId;
	private String state;
	private String country;
	private String zip;
	private List<TaxList> taxList;

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getCrmProductId() {
		return crmProductId;
	}

	public void setCrmProductId(String crmProductId) {
		this.crmProductId = crmProductId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public List<TaxList> getTaxList() {
		return taxList;
	}

	public void setTaxList(List<TaxList> taxList) {
		this.taxList = taxList;
	}

	@Override
	public String toString() {
		return "TaxProperty [invoiceNumber=" + invoiceNumber + ", city=" + city + ", billFrom=" + billFrom + ", billTo=" + billTo + ", customerId=" + customerId + ", partnerId=" + partnerId
				+ ", crmProductId=" + crmProductId + ", state=" + state + ", country=" + country + ", zip=" + zip + ", taxList=" + taxList.size() + "]";
	}

}
