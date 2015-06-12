package com.anpi.domain;

import javax.xml.bind.annotation.XmlAttribute;

public class TaxRequestType {

    private String name;
    private String code;
    private String id;
    private String amount;
    private String lineCount;
    private String usageMin;
    private String date;
    private Taxitems taxitems;

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @XmlAttribute(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    @XmlAttribute(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLineCount() {
        return lineCount;
    }

    public void setLineCount(String lineCount) {
        this.lineCount = lineCount;
    }

    public String getUsageMin() {
        return usageMin;
    }

    public void setUsageMin(String usageMin) {
        this.usageMin = usageMin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Taxitems getTaxitems() {
        return taxitems;
    }

    public void setTaxitems(Taxitems taxitems) {
        this.taxitems = taxitems;
    }
}
