package com.anpi.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Entity;

@Entity
@Table(name = "vw_get_invoice_address")
public class InvoiceAddress implements Serializable {

	@Column(name = "billed_from")
	private Date billFrom;

	@Column(name = "billed_to")
	private Date billTo;

	@Id
	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Id
	@Column(name = "service_id")
	private String serviceId;

	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "product_id")
	private String productId;
	@Column(name = "partner_id")
	private String partnerId;
	@Column(name = "crm_service_id")
	private String crmServiceId;

	@Column(name = "crm_product_id")
	private String crmProductId;

	@Column(name = "crm_partner_id")
	private String crmPartnerId;

	@Column(name = "product_type")
	private String productType;

	@Column(name = "total_bill_before_tax")
	private double totalBillBefore;

	@Column(name="fname")
	private String fname;
	@Column(name="lname")
	private String lname;
	@Column(name="address1")
	private String address1;
	@Column(name="address2")
	private String address2;
	
	@Column(name="city")
	private String city;
	@Column(name="state")
	private String state;
	@Column(name="country")
	private String country;
	@Column(name="zip")
	private String zip;

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

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getCrmServiceId() {
		return crmServiceId;
	}

	public void setCrmServiceId(String crmServiceId) {
		this.crmServiceId = crmServiceId;
	}

	public String getCrmProductId() {
		return crmProductId;
	}

	public void setCrmProductId(String crmProductId) {
		this.crmProductId = crmProductId;
	}

	public String getCrmPartnerId() {
		return crmPartnerId;
	}

	public void setCrmPartnerId(String crmPartnerId) {
		this.crmPartnerId = crmPartnerId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public double getTotalBillBefore() {
		return totalBillBefore;
	}

	public void setTotalBillBefore(double totalBillBefore) {
		this.totalBillBefore = totalBillBefore;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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
	
	

}
