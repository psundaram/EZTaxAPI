package com.anpi.app.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;

import org.hibernate.annotations.CollectionId;
@Entity
@Table(name="t_tax_service_master")
public class TaxServiceMaster {
	
	@Id
	@Column(name="id")
	private int id;
	
	@ManyToOne
    @JoinColumn(name="tax_service_id")
	private TaxService taxService;
	
	@ManyToOne
    @JoinColumn(name="tax_master_id")
	private TaxMaster taxMaster;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TaxService getTaxService() {
		return taxService;
	}

	public void setTaxService(TaxService taxService) {
		this.taxService = taxService;
	}

	public TaxMaster getTaxMaster() {
		return taxMaster;
	}

	public void setTaxMaster(TaxMaster taxMaster) {
		this.taxMaster = taxMaster;
	}
	
	

}
