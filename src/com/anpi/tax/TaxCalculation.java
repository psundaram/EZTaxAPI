package com.anpi.tax;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.anpi.app.model.InvoiceAddress;
import com.anpi.app.model.TaxService;
import com.anpi.app.model.TaxServiceMaster;
import com.anpi.app.util.HibernateUtil;
import com.anpi.domain.Address;
import com.anpi.domain.CustomerInfo;
import com.anpi.domain.TaxList;
import com.anpi.domain.TaxObject;
import com.anpi.domain.TaxProperty;
import com.anpi.domain.TaxRequestType;
import com.anpi.domain.TaxRequestTypes;

public class TaxCalculation {

	public static HashMap<String, TaxProperty> fetchFromDatabase(Session session) {

		// Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		// session.beginTransaction();
		Criteria criteria = session.createCriteria(InvoiceAddress.class);
		criteria.add(Restrictions.isNotNull("city")).add(Restrictions.isNotNull("state")).add(Restrictions.isNotNull("country")).add(Restrictions.isNotNull("zip"));
		criteria.setMaxResults(10);
		List<InvoiceAddress> listOfTaxInvoices = criteria.list();
		System.out.println(listOfTaxInvoices.size());
		HashMap<String, TaxProperty> invoiceMap = new HashMap<String, TaxProperty>();
		for (int i = 0; i < listOfTaxInvoices.size(); i++) {
			List<TaxList> listOfTaxes = new ArrayList<TaxList>();
			InvoiceAddress invoiceAddress = (InvoiceAddress) listOfTaxInvoices.get(i);
			String key = invoiceAddress.getInvoiceNumber() + "_" + invoiceAddress.getCity() + "_" + invoiceAddress.getCountry();
			TaxProperty tax = invoiceMap.get(key);
			if (tax != null) {
				List<TaxList> mapedTaxedList = tax.getTaxList();
				TaxList taxList = new TaxList();
				taxList.setCrmProductId(invoiceAddress.getCrmProductId());
				taxList.setTotalBill(invoiceAddress.getTotalBillBefore());
				mapedTaxedList.add(taxList);
				tax.setTaxList(mapedTaxedList);
				// System.out.println("Map already has data");
			} else {
				System.out.println("Map has no data");
				TaxProperty taxProperty = new TaxProperty();
				taxProperty.setBillFrom(invoiceAddress.getBillFrom());
				taxProperty.setBillTo(invoiceAddress.getBillTo());
				taxProperty.setCustomerId(invoiceAddress.getCustomerId());
				taxProperty.setCity(invoiceAddress.getCity());
				taxProperty.setCountry(invoiceAddress.getCountry());
				taxProperty.setState(invoiceAddress.getState());
				taxProperty.setZip(invoiceAddress.getZip());
				taxProperty.setInvoiceNumber(invoiceAddress.getInvoiceNumber());
				taxProperty.setPartnerId(invoiceAddress.getPartnerId());
				
				TaxList taxList = new TaxList();
				taxList.setCrmProductId(invoiceAddress.getCrmProductId());
				taxList.setTotalBill(invoiceAddress.getTotalBillBefore());
				listOfTaxes.add(taxList);
				taxProperty.setTaxList(listOfTaxes);
				invoiceMap.put(key, taxProperty);
			}
		}
		// HibernateUtil.getSessionAnnotationFactory().close();
		return invoiceMap;

	}

	
	

	public static void main(String[] args) throws Exception {
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		session.beginTransaction();
		
		HashMap<String, TaxProperty> invoiceMap = fetchFromDatabase(session);
		System.out.println(invoiceMap.size());
		
		for (String key : invoiceMap.keySet()) {
			System.out.println("Key: " + key + ", Value: " + invoiceMap.get(key));
			TaxProperty taxProperty = invoiceMap.get(key);
			calcTax(taxProperty,session);
		}
		
		HibernateUtil.getSessionAnnotationFactory().close();

	}

	public static String calcTax(TaxProperty taxProperty,Session session) throws Exception {
			double accessTax = 0.0;
			double tollfreeTax = 0.0;
			double interStateTax = 0.0;
			double intraStateTax = 0.0;
			double interUsageMin = 0.0;
			double intraUsageMin = 0.0;
			int count = 0;
			TaxObject taxObject = new TaxObject();

			CustomerInfo customerInfo = new CustomerInfo();

			customerInfo.setBillFrom(taxProperty.getBillFrom());
			customerInfo.setBillTo(taxProperty.getBillTo());
			customerInfo.setInvoiceNumber(taxProperty.getInvoiceNumber().substring(1));
			customerInfo.setCompanyIdentifier(taxProperty.getPartnerId());
			customerInfo.setCustNumber(taxProperty.getCustomerId());
			Address address = new Address();

			System.out.println("getCity:" + taxProperty.getCity());
			address.setCity(taxProperty.getCity());
			address.setCountry(taxProperty.getCountry());
			address.setState(taxProperty.getState());
			address.setZip(taxProperty.getZip());
			customerInfo.setBa(address);
			customerInfo.setJa(address);
			System.out.println("ba" + customerInfo.getBa().getCountry());
			taxObject.setCustomerInfo(customerInfo);

			TaxRequestTypes taxRequestTypes = new TaxRequestTypes();
			List<TaxRequestType> listOfTaxRequestTypes = new ArrayList<TaxRequestType>();

		for (int i = 0; i < taxProperty.getTaxList().size(); i++) {
			TaxList taxList = (TaxList) taxProperty.getTaxList().get(i);
			Criteria criteria1 = session.createCriteria(TaxService.class);
			criteria1.add(Restrictions.eq("transCode", taxList.getCrmProductId()));
			List<TaxService> taxServices = criteria1.list();
			if (taxServices != null && taxServices.size() > 0) {
				System.out.println(taxServices.get(0).getId());
				Criteria criteria2 = session.createCriteria(TaxServiceMaster.class);
				criteria2.add(Restrictions.eq("taxService", taxServices.get(0)));
				List<TaxServiceMaster> taxServiceMasters = criteria2.list();
				System.out.println("TaxServiceMaster:" + taxServiceMasters.get(0).getTaxMaster().getTaxType());
				double amount = taxList.getTotalBill();
				for (int j = 0; j < taxServiceMasters.size(); j++) {
					TaxServiceMaster taxServiceMaster = (TaxServiceMaster) taxServiceMasters.get(j);

					switch (taxServiceMaster.getTaxMaster().getTaxType()) {
					case "Access Tax":
						System.out.println("Inside Access Tax:" + taxList.getTotalBill());
						accessTax += taxList.getTotalBill();
						count++;
						break;

					case "Tollfree Tax":
						System.out.println("Inside Tollfree Tax:" + taxList.getTotalBill());
						tollfreeTax += taxList.getTotalBill();
						count++;
						break;

					case "InterStateUsage Tax":
						interStateTax += taxList.getTotalBill();
						interUsageMin += 0.0;
						break;

					case "IntraStateUsage Tax":
						intraStateTax += taxList.getTotalBill();
						intraUsageMin += 0.0;
						break;

					default:
						break;
					}
				}
			}
		}
			DateTime dt = new DateTime(new Date());
			dt = dt.withZone(DateTimeZone.forID("UTC"));
			String date = dt.toString("MM/dd/yyyy");
			
			if (accessTax != 0.0) {
				TaxRequestType taxRequestType = new TaxRequestType();
				// taxRequestType.setId(taxServiceMaster.getTaxMaster().getTaxId());
				taxRequestType.setName("Access Tax");
				taxRequestType.setAmount(String.valueOf(accessTax));
				taxRequestType.setDate(date);
				listOfTaxRequestTypes.add(taxRequestType);

			}
			if (tollfreeTax != 0.0) {
				TaxRequestType taxRequestType = new TaxRequestType();
				// taxRequestType.setId(taxServiceMaster.getTaxMaster().getTaxId());
				taxRequestType.setName("Tollfree Tax");
				taxRequestType.setAmount(String.valueOf(tollfreeTax));
				taxRequestType.setDate(date);
				listOfTaxRequestTypes.add(taxRequestType);
			}
			if (interStateTax != 0.0) {
				TaxRequestType taxRequestType = new TaxRequestType();
				// taxRequestType.setId(taxServiceMaster.getTaxMaster().getTaxId());
				taxRequestType.setName("InterStateUsage Tax");
				taxRequestType.setAmount(String.valueOf(interStateTax));
				taxRequestType.setUsageMin(String.valueOf(interUsageMin));
				taxRequestType.setDate(date);
				listOfTaxRequestTypes.add(taxRequestType);
			}
			if (intraStateTax != 0.0) {
				TaxRequestType taxRequestType = new TaxRequestType();
				// taxRequestType.setId(taxServiceMaster.getTaxMaster().getTaxId());
				taxRequestType.setName("IntraStateUsage Tax");
				taxRequestType.setAmount(String.valueOf(intraStateTax));
				taxRequestType.setUsageMin(String.valueOf(intraUsageMin));
				taxRequestType.setDate(date);
				listOfTaxRequestTypes.add(taxRequestType);
			}
			if(count!=0){
				TaxRequestType lineTaxType = new TaxRequestType();
				// taxRequestType.setId(taxServiceMaster.getTaxMaster().getTaxId());
				lineTaxType.setName("Lines Tax");
				lineTaxType.setLineCount(String.valueOf(count));
				lineTaxType.setDate(date);
				listOfTaxRequestTypes.add(lineTaxType);
			}

			TaxRequestType invoiceTaxType = new TaxRequestType();
			// taxRequestType.setId(taxServiceMaster.getTaxMaster().getTaxId());
			invoiceTaxType.setName("Invoice Tax");
			invoiceTaxType.setDate(date);
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
//			 TaxObject taxObject1 = new CalculateTax().generateTax(sw.toString());
			return sw.toString();
			


	}

}
