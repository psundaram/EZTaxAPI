package com.anpi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="t_invoice_tax_summary")
public class Summary {

	@Id
	@Column(name="id")
	private int id;
	
	private String description;

	private String value;

	@Column(name="invoice_number")
	private String invoiceNumber;

	@Column(name="total")
	private double total;

	@Column(name="billed_from")
	private Date billFrom;
	
	@Column(name="billed_to")
	private Date billTo;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlTransient
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	@XmlTransient
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Summary [description=" + description + ", value=" + value + ", invoiceNumber=" + invoiceNumber + ", total=" + total + ", billFrom=" + billFrom + ", billTo=" + billTo + "]";
	}

	@XmlTransient
	public Date getBillFrom() {
		return billFrom;
	}

	public void setBillFrom(Date billFrom) {
		this.billFrom = billFrom;
	}

	@XmlTransient
	public Date getBillTo() {
		return billTo;
	}

	public void setBillTo(Date billTo) {
		this.billTo = billTo;
	}

	@XmlTransient
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
