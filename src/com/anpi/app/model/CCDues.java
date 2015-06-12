package com.anpi.app.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vw_invoice_cc_dues")
public class CCDues {
	
	
	@Column(name = "invoice_date")
	private Date invoiceDate;
	
	
	@Column(name = "billed_from")
	private Date billFrom;

	@Column(name = "billed_to")
	private Date billTo;

	@Id
	@Column(name = "invoice_number")
	private String invoiceNumber;

	
	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "billing_group")
	private String billingGroup;

	@Column(name = "total_bill")
	private BigDecimal totalBill;
	
	
	@Column(name="exp_month")
	private String expMonth;
	
	@Column(name="exp_year")
	private String expYear;
	
	@Column(name="name_on_card")
	private String nameOnCard;
	
	@Column(name="card_type")
	private String cardType;
	
	@Column(name="cc_profile")
	private String ccProfile;
	
	@Column(name="crm_customer_id")
	private String crmCustomerId;
	
	@Column(name="crm_partner_id")
	private String crmPartnerId;

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
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

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getBillingGroup() {
		return billingGroup;
	}

	public void setBillingGroup(String billingGroup) {
		this.billingGroup = billingGroup;
	}

	public BigDecimal getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(BigDecimal totalBill) {
		this.totalBill = totalBill;
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

	public String getCcProfile() {
		return ccProfile;
	}

	public void setCcProfile(String ccProfile) {
		this.ccProfile = ccProfile;
	}

	public String getCrmCustomerId() {
		return crmCustomerId;
	}

	public void setCrmCustomerId(String crmCustomerId) {
		this.crmCustomerId = crmCustomerId;
	}

	public String getCrmPartnerId() {
		return crmPartnerId;
	}

	public void setCrmPartnerId(String crmPartnerId) {
		this.crmPartnerId = crmPartnerId;
	}
	
	
	
	

	
}
