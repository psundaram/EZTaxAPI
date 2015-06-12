package com.anpi.app.model;

import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.anpi.app.util.HibernateUtil;
import com.anpi.domain.Address;
import com.anpi.domain.CustomerInfo;
import com.anpi.domain.TaxObject;
import com.anpi.domain.TaxRequestType;
import com.anpi.domain.TaxRequestTypes;
import com.anpi.tax.CalculateTax;

public class Main {

	public static void main(String[] args) throws JAXBException, ParserConfigurationException, ParseException {

		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		// start transaction
		session.beginTransaction();

		Criteria criteria = session.createCriteria(InvoiceAddress.class);
		criteria.add(Restrictions.eq("invoiceNumber", "M100001"));
		criteria.add(Restrictions.isNotNull("city")).add(Restrictions.isNotNull("state")).add(Restrictions.isNotNull("country"));
		List<InvoiceAddress> invoiceAddresses = criteria.list();
		System.out.println(invoiceAddresses.size());
		
		TaxObject taxObject = new TaxObject();
		InvoiceAddress inAddress = (InvoiceAddress) invoiceAddresses.get(0);
		
		CustomerInfo customerInfo = new CustomerInfo(); 
		customerInfo.setBillFrom(inAddress.getBillFrom());
		customerInfo.setBillTo(inAddress.getBillTo());
		customerInfo.setInvoiceNumber(inAddress.getInvoiceNumber().substring(1));
		customerInfo.setCompanyIdentifier(inAddress.getPartnerId());
		customerInfo.setCustNumber(inAddress.getCustomerId());
		Address address = new Address();
		System.out.println("getCity:"+inAddress.getCity());
		address.setCity(inAddress.getCity());
		address.setCountry(inAddress.getCountry());
		address.setState(inAddress.getState());
		address.setZip(inAddress.getZip());
		customerInfo.setBa(address);
		customerInfo.setJa(address);
		System.out.println("ba"+customerInfo.getBa().getCountry());
		taxObject.setCustomerInfo(customerInfo);
		
		TaxRequestTypes taxRequestTypes = new TaxRequestTypes(); 
		List<TaxRequestType> listOfTaxRequestTypes = new ArrayList<TaxRequestType>();
		
		double accessTax = 0.0;
		double tollfreeTax = 0.0;
		double interStateTax = 0.0;
		double intraStateTax = 0.0;
		double interUsageMin = 0.0;
		double intraUsageMin = 0.0;
		
		int count = 0;
		
		for (int i = 0; i < invoiceAddresses.size(); i++) {
			
			InvoiceAddress invoiceAddress = (InvoiceAddress) invoiceAddresses.get(i);
			
			System.out.println(invoiceAddress.getServiceId() + ", " + invoiceAddress.getProductId());
			
			Criteria criteria1 = session.createCriteria(TaxService.class);
			criteria1.add(Restrictions.eq("transCode", invoiceAddress.getServiceId()));
			List<TaxService> taxServices = criteria1.list();
			
			System.out.println(taxServices.get(0).getId());
			
			
			Criteria criteria2 = session.createCriteria(TaxServiceMaster.class);
			criteria2.add(Restrictions.eq("taxService", taxServices.get(0)));
			List<TaxServiceMaster> taxServiceMasters = criteria2.list();
			System.out.println("TaxServiceMaster:" + taxServiceMasters.get(0).getTaxMaster().getTaxType());
			double amount = invoiceAddress.getTotalBillBefore();
			
			
			
			for(int j=0;j<taxServiceMasters.size();j++){
				TaxServiceMaster taxServiceMaster = (TaxServiceMaster) taxServiceMasters.get(j);
				
				switch (taxServiceMaster.getTaxMaster().getTaxType()) {
				case "Access Tax":
					System.out.println("Inside Access Tax:"+invoiceAddress.getTotalBillBefore());
					accessTax += invoiceAddress.getTotalBillBefore();
					count++;
					break;
					
				case "Tollfree Tax":
					System.out.println("Inside Tollfree Tax:"+invoiceAddress.getTotalBillBefore());
					tollfreeTax +=invoiceAddress.getTotalBillBefore();
					count++;
				    break;	
				    
				case "InterStateUsage Tax":
					interStateTax+=invoiceAddress.getTotalBillBefore();
					interUsageMin += 0.0;
					break;
					
				case "IntraStateUsage Tax": 
					intraStateTax+=invoiceAddress.getTotalBillBefore();
					intraUsageMin += 0.0;
					break;
					
				default:
					break;
				}
				
			}
			
			
			
		}
		if(accessTax!=0.0){
			TaxRequestType taxRequestType = new TaxRequestType();
			// taxRequestType.setId(taxServiceMaster.getTaxMaster().getTaxId());
			taxRequestType.setName("Access Tax");
			taxRequestType.setAmount(String.valueOf(accessTax));
			taxRequestType.setDate("06/02/2015");
			listOfTaxRequestTypes.add(taxRequestType);
			
		}
		if(tollfreeTax!=0.0){
			TaxRequestType taxRequestType = new TaxRequestType();
			// taxRequestType.setId(taxServiceMaster.getTaxMaster().getTaxId());
			taxRequestType.setName("Tollfree Tax");
			taxRequestType.setAmount(String.valueOf(tollfreeTax));
			taxRequestType.setDate("06/02/2015");
			listOfTaxRequestTypes.add(taxRequestType);
		}
		if(interStateTax!=0.0){
			TaxRequestType taxRequestType = new TaxRequestType();
			// taxRequestType.setId(taxServiceMaster.getTaxMaster().getTaxId());
			taxRequestType.setName("InterStateUsage Tax");
			taxRequestType.setAmount(String.valueOf(interStateTax));
			taxRequestType.setUsageMin(String.valueOf(interUsageMin));
			taxRequestType.setDate("06/02/2015");
			listOfTaxRequestTypes.add(taxRequestType);
		}
		if(intraStateTax!=0.0){
			TaxRequestType taxRequestType = new TaxRequestType();
			// taxRequestType.setId(taxServiceMaster.getTaxMaster().getTaxId());
			taxRequestType.setName("IntraStateUsage Tax");
			taxRequestType.setAmount(String.valueOf(intraStateTax));
			taxRequestType.setUsageMin(String.valueOf(intraUsageMin));
			taxRequestType.setDate("06/02/2015");
			listOfTaxRequestTypes.add(taxRequestType);
		}
		TaxRequestType lineTaxType = new TaxRequestType();
		// taxRequestType.setId(taxServiceMaster.getTaxMaster().getTaxId());
		lineTaxType.setName("Lines Tax");
		lineTaxType.setLineCount(String.valueOf(count));
		lineTaxType.setDate("06/02/2015");
		listOfTaxRequestTypes.add(lineTaxType);
		
		TaxRequestType invoiceTaxType = new TaxRequestType();
		// taxRequestType.setId(taxServiceMaster.getTaxMaster().getTaxId());
		invoiceTaxType.setName("Invoice Tax");
		invoiceTaxType.setDate("06/02/2015");
		listOfTaxRequestTypes.add(invoiceTaxType);
		
		
		
		taxRequestTypes.setTaxRequestType(listOfTaxRequestTypes);
		taxObject.setTaxRequestTypes(taxRequestTypes);

		
		StringWriter sw = new StringWriter();

        JAXBContext jaxbContext = JAXBContext.newInstance(TaxObject.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(taxObject, sw);
        System.out.println("output => " + sw);
		// terminate session factory, otherwise program won't end
        TaxObject taxObject1 = new CalculateTax().generateTax(sw.toString());
        
		HibernateUtil.getSessionAnnotationFactory().close();
	}
}
