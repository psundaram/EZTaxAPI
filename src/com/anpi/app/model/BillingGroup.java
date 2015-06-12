package com.anpi.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_billing_group")
public class BillingGroup {
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="billing_group_id")
	private String billingGroupId;
	
	@Column(name="billing_group_name")
	private String billingGroupName;
	
	@Column(name="bill_type")
	private String billType;
	
	@Column(name="exp_month")
	private String expMonth;
	
	@Column(name="exp_year")
	private String expYear;
	
	@Column(name="name_on_card")
	private String nameOnCard;
	
	@Column(name="card_type")
	private String cardType;
	
	@Column(name="account_number")
	private String accountNumber;
	
	@Column(name="routing_number")
	private String routingNumber;
	
	@Column(name="bank_name")
	private String bankName;
	
	@Column(name="active")
	private String active;
	
	@Column(name="bill_country")
	private String billCountry;
	
	@Column(name="currency_code")
	private String currencyCode;
	
	@Column(name="credit_card_4_digit")
	private String ccDigit;
	
	@Column(name="cc_profile")
	private String ccProfile;
	
	@Column(name="auto_pay")
	private String autoPay;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBillingGroupId() {
		return billingGroupId;
	}

	public void setBillingGroupId(String billingGroupId) {
		this.billingGroupId = billingGroupId;
	}

	public String getBillingGroupName() {
		return billingGroupName;
	}

	public void setBillingGroupName(String billingGroupName) {
		this.billingGroupName = billingGroupName;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getExpMonth() {
		return expMonth;
	}

	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	public String getExpYear() {
		return expYear;
	}

	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getRoutingNumber() {
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getBillCountry() {
		return billCountry;
	}

	public void setBillCountry(String billCountry) {
		this.billCountry = billCountry;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCcDigit() {
		return ccDigit;
	}

	public void setCcDigit(String ccDigit) {
		this.ccDigit = ccDigit;
	}

	public String getCcProfile() {
		return ccProfile;
	}

	public void setCcProfile(String ccProfile) {
		this.ccProfile = ccProfile;
	}

	public String getAutoPay() {
		return autoPay;
	}

	public void setAutoPay(String autoPay) {
		this.autoPay = autoPay;
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
	
	
	
	
	
	
	

}
