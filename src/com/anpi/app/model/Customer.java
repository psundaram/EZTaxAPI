package com.anpi.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_customer")
public class Customer {
	@Id
	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "partner_id")
	private String partnerId;

	@Column(name = "partner_name")
	private String partnerName;

	@Column(name = "partner_type")
	private String partnerType;

	@Column(name = "agent_id")
	private String agentId;

	@Column(name = "sales_rep_id")
	private String salesRepId;

	@Column(name = "contact_id")
	private String contactId;

	@Column(name = "customer_source")
	private String customerSource;

	@Column(name = "billing_group_id")
	private String billingGroupId;

	@Column(name = "billing_date")
	private Date billingDate;

	@Column(name = "crm_customer_id")
	private String crmCCustomerId;

	@Column(name = "crm_partner_id")
	private String crmPartnerId;

	@Column(name = "active")
	private String active;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "home_country")
	private String homeCountry;

	@Column(name = "currency")
	private String currency;

	@Column(name = "bill_type")
	private String billType;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "fname")
	private String fname;

	@Column(name = "lname")
	private String lname;

	@Column(name = "address2")
	private String address2;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "zip")
	private String zip;

	@Column(name = "country")
	private String country;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getSalesRepId() {
		return salesRepId;
	}

	public void setSalesRepId(String salesRepId) {
		this.salesRepId = salesRepId;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public String getBillingGroupId() {
		return billingGroupId;
	}

	public void setBillingGroupId(String billingGroupId) {
		this.billingGroupId = billingGroupId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	public String getCrmCCustomerId() {
		return crmCCustomerId;
	}

	public void setCrmCCustomerId(String crmCCustomerId) {
		this.crmCCustomerId = crmCCustomerId;
	}

	public String getCrmPartnerId() {
		return crmPartnerId;
	}

	public void setCrmPartnerId(String crmPartnerId) {
		this.crmPartnerId = crmPartnerId;
	}

	public String getHomeCountry() {
		return homeCountry;
	}

	public void setHomeCountry(String homeCountry) {
		this.homeCountry = homeCountry;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
