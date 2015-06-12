package com.anpi.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_customer")
public class Customer {
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="customer_id")
	private String customerId;
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name="partner_id")
	private String partnerId;
	
	@Column(name="agent_id")
	private String agentId;
	
	@Column(name="sales_rep_id")
	private String salesRepId;
	
	@Column(name="contact_id")
	private String contactId;
	
	@Column(name="customer_source")
	private String customerSource;
	
	@Column(name="billing_group_id")
	private String billingGroupId;
	
	@Column(name="billing_start_date")
	private Date billingStartDate;
	
	@Column(name="active")
	private String active;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="home_country")
	private String country;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="demo_ind")
	private String demoId;
	
	@Column(name="created_date")
	private Date createdDate; 
	
	@Column(name="updated_date")
	private Date updatedDate;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="external_ref_id1")
	private String externalRefId1;
	
	@Column(name="external_ref_id2")
	private String externalRefId2;
	
	@Column(name="external_ref_id3")
	private String externalRefId3;
	
	@Column(name="custom_attribute1")
	private String customAttribute1;
	
	@Column(name="custom_attribute2")
	private String customAttribute2;
	
//	@ManyToOne(optional=false)
//	@JoinColumn(name="billing_group_id" ,insertable=false, updatable=false)
//	private BillingGroup billingGroup;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Date getBillingStartDate() {
		return billingStartDate;
	}

	public void setBillingStartDate(Date billingStartDate) {
		this.billingStartDate = billingStartDate;
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

	public String getDemoId() {
		return demoId;
	}

	public void setDemoId(String demoId) {
		this.demoId = demoId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getExternalRefId1() {
		return externalRefId1;
	}

	public void setExternalRefId1(String externalRefId1) {
		this.externalRefId1 = externalRefId1;
	}

	public String getExternalRefId2() {
		return externalRefId2;
	}

	public void setExternalRefId2(String externalRefId2) {
		this.externalRefId2 = externalRefId2;
	}

	public String getExternalRefId3() {
		return externalRefId3;
	}

	public void setExternalRefId3(String externalRefId3) {
		this.externalRefId3 = externalRefId3;
	}

	public String getCustomAttribute1() {
		return customAttribute1;
	}

	public void setCustomAttribute1(String customAttribute1) {
		this.customAttribute1 = customAttribute1;
	}

	public String getCustomAttribute2() {
		return customAttribute2;
	}

	public void setCustomAttribute2(String customAttribute2) {
		this.customAttribute2 = customAttribute2;
	}

//	public BillingGroup getBillingGroup() {
//		return billingGroup;
//	}
//
//	public void setBillingGroup(BillingGroup billingGroup) {
//		this.billingGroup = billingGroup;
//	}
	
	

}
