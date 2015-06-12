package com.anpi.app.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_invoice_payment_log_mod")
public class InvoicePaymentLog {
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Column(name = "billed_from")
	private Date billedFrom;

	@Column(name = "billed_to")
	private Date billedTo;

	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "payment_type")
	private String paymentType;

	@Column(name = "cc_number")
	private String ccNumber;

	@Column(name = "exp_month")
	private String expMonth;

	@Column(name = "exp_year")
	private String expYear;

	@Column(name = "card_type")
	private String cardType;

	@Column(name = "auth_code")
	private String authCode;

	@Column(name = "check_number")
	private String chequeNumber;

	@Column(name = "check_date")
	private Date chequeDate;

	@Column(name = "name")
	private String name;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "routing_number")
	private String routingNumber;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "invoice_amount")
	private BigDecimal invoiceAmount;

	@Column(name = "attempted_amount")
	private BigDecimal attemptedAmount;

	@Column(name = "charge_status")
	private String chargeStatus;

	@Column(name = "charge_code")
	private String chargeCode;

	@Column(name = "provider_name")
	private String providerName;

	@Column(name = "payment_ref_id")
	private String paymentRefId;

	@Column(name = "payment_date")
	private Date paymentDate;

	@Column(name = "request")
	private String request;

	@Column(name = "response")
	private String response;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "created_by")
	private String createdBy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getBilledFrom() {
		return billedFrom;
	}

	public void setBilledFrom(Date billedFrom) {
		this.billedFrom = billedFrom;
	}

	public Date getBilledTo() {
		return billedTo;
	}

	public void setBilledTo(Date billedTo) {
		this.billedTo = billedTo;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public BigDecimal getAttemptedAmount() {
		return attemptedAmount;
	}

	public void setAttemptedAmount(BigDecimal attemptedAmount) {
		this.attemptedAmount = attemptedAmount;
	}

	public String getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getPaymentRefId() {
		return paymentRefId;
	}

	public void setPaymentRefId(String paymentRefId) {
		this.paymentRefId = paymentRefId;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
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
	
}
