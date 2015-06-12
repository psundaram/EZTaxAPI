package com.anpi.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_tax_service")
public class TaxService {
	
	@Id
	@Column(name="id")
	private int id;

	@Column(name="trans_type")
	private String transType;
	
	@Column(name="trans_name")
	private String transName;
	
	@Column(name="classification")
	private String classification;

	@Column(name="category")
	private String category;

	@Column(name="trans_type_desc")
	private String transTypeDesc;

	@Column(name="trans_code")
	private String transCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTransTypeDesc() {
		return transTypeDesc;
	}

	public void setTransTypeDesc(String transTypeDesc) {
		this.transTypeDesc = transTypeDesc;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

}
