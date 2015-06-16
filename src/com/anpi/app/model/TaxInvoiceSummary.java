package com.anpi.app.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;


@Entity
@Table(name="t_invoice_summary_mod")
public class TaxInvoiceSummary {
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="control_id")
	private String controlId;
	
	@Column(name = "invoice_date")
	private Date invoiceDate;
	
	@Column(name = "invoice_due_date")
	private Date invoiceDueDate;
	
	@Column(name = "billed_from")
	private Date billFrom;

	@Column(name = "billed_to")
	private Date billTo;

	
	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Column(name = "service_id")
	private String serviceId;

	
	@Column(name = "customer_id")
	private String customerId;

	
	@Column(name = "partner_id")
	private String partnerId;
	
	@Column(name = "cost_center_id")
	private String costCenterId;

	@Column(name = "location_name")
	private String locationName;

	@Column(name = "billing_group")
	private String billingGroup;

	@Column(name = "billing_group_id")
	private String billingGroupId;

	@Column(name = "total_bill_before_tax")
	private BigDecimal totalBillBefore;
	
	@Column(name = "total_tax")
	private BigDecimal totalTax;
	
	@Column(name = "total_bill")
	private BigDecimal totalBill;

	@Column(name="previous_balance")
	private BigDecimal previousBalance;
	@Column(name="total_adjustments")
	private BigDecimal totalAdjustments;
	
	
	@Column(name = "partner_total_bill_before_tax")
	private BigDecimal partnerTotalBillBefore;
	
	@Column(name = "partner_total_tax")
	private BigDecimal partnerTotalTax;
	
	@Column(name = "partner_total_bill")
	private BigDecimal partnerTotalBill;

	@Column(name="partner_total_adjustments")
	private BigDecimal partnerTotalAdjustments;
	
	@Column(name="payment_type")
	private String paymentType;
	@Column(name="payment_amount")
	private BigDecimal paymentAmount;
	
	@Column(name="payment_status")
	private String paymentStatus;
	@Column(name="payment_date")
	private Date paymentDate;
	@Column(name="payment_ref_id")
	private String paymentRefId;
	@Column(name="provider_type")
	private String providerType;
	@Column(name="invoice_status")
	private String invoiceStatus;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="created_date")
	private Date createdDate;
	@Column(name="updated_date")
	private Date updatedDate;
	@Column(name="mail_sent")
	private int mailSent;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="customer_id",insertable=false,updatable=false)
	private Customer customer;
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getControlId() {
		return controlId;
	}
	public void setControlId(String controlId) {
		this.controlId = controlId;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public Date getInvoiceDueDate() {
		return invoiceDueDate;
	}
	public void setInvoiceDueDate(Date invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
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
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getCostCenterId() {
		return costCenterId;
	}
	public void setCostCenterId(String costCenterId) {
		this.costCenterId = costCenterId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getBillingGroup() {
		return billingGroup;
	}
	public void setBillingGroup(String billingGroup) {
		this.billingGroup = billingGroup;
	}
	public String getBillingGroupId() {
		return billingGroupId;
	}
	public void setBillingGroupId(String billingGroupId) {
		this.billingGroupId = billingGroupId;
	}
	public BigDecimal getTotalBillBefore() {
		return totalBillBefore;
	}
	public void setTotalBillBefore(BigDecimal totalBillBefore) {
		this.totalBillBefore = totalBillBefore;
	}
	public BigDecimal getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}
	public BigDecimal getTotalBill() {
		return totalBill;
	}
	public void setTotalBill(BigDecimal totalBill) {
		this.totalBill = totalBill;
	}
	public BigDecimal getPreviousBalance() {
		return previousBalance;
	}
	public void setPreviousBalance(BigDecimal previousBalance) {
		this.previousBalance = previousBalance;
	}
	public BigDecimal getTotalAdjustments() {
		return totalAdjustments;
	}
	public void setTotalAdjustments(BigDecimal totalAdjustments) {
		this.totalAdjustments = totalAdjustments;
	}
	public BigDecimal getPartnerTotalBillBefore() {
		return partnerTotalBillBefore;
	}
	public void setPartnerTotalBillBefore(BigDecimal partnerTotalBillBefore) {
		this.partnerTotalBillBefore = partnerTotalBillBefore;
	}
	public BigDecimal getPartnerTotalTax() {
		return partnerTotalTax;
	}
	public void setPartnerTotalTax(BigDecimal partnerTotalTax) {
		this.partnerTotalTax = partnerTotalTax;
	}
	public BigDecimal getPartnerTotalBill() {
		return partnerTotalBill;
	}
	public void setPartnerTotalBill(BigDecimal partnerTotalBill) {
		this.partnerTotalBill = partnerTotalBill;
	}
	public BigDecimal getPartnerTotalAdjustments() {
		return partnerTotalAdjustments;
	}
	public void setPartnerTotalAdjustments(BigDecimal partnerTotalAdjustments) {
		this.partnerTotalAdjustments = partnerTotalAdjustments;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPaymentRefId() {
		return paymentRefId;
	}
	public void setPaymentRefId(String paymentRefId) {
		this.paymentRefId = paymentRefId;
	}
	public String getProviderType() {
		return providerType;
	}
	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
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
	public int getMailSent() {
		return mailSent;
	}
	public void setMailSent(int mailSent) {
		this.mailSent = mailSent;
	}

}
