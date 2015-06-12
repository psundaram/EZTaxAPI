package com.anpi.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
public class TaxObject {

    private CustomerInfo customerInfo;
    private Exemptions exemptions;
    private Summaries summaries;
    private TaxRequestTypes taxRequestTypes;

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public Exemptions getExemptions() {
        return exemptions;
    }

    public void setExemptions(Exemptions exemptions) {
        this.exemptions = exemptions;
    }

    public Summaries getSummaries() {
        return summaries;
    }

    public void setSummaries(Summaries summaries) {
        this.summaries = summaries;
    }

    public TaxRequestTypes getTaxRequestTypes() {
        return taxRequestTypes;
    }

    public void setTaxRequestTypes(TaxRequestTypes taxRequestTypes) {
        this.taxRequestTypes = taxRequestTypes;
    }
}
