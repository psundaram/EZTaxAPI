package com.anpi.app.service;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.anpi.app.model.TaxInvoice;
import com.anpi.app.util.DateUtil;
import com.anpi.app.util.DbConnect;
import com.anpi.domain.Summary;
import com.anpi.domain.TaxObject;
import com.anpi.domain.TaxRequestType;
import com.anpi.domain.Taxitem;
import com.anpi.tax.CalculateTax;

public class TaxService {
	
	public String calculateTax(String content) throws Exception {
    	 TaxObject taxObject = new CalculateTax().generateTax(content);
         StringWriter sw = null;
         try {
             sw = new StringWriter();
             JAXBContext jaxbContext = JAXBContext.newInstance(TaxObject.class);
             Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
             // output pretty printed
             jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
             jaxbMarshaller.marshal(taxObject, sw);
             System.out.println("output => " + sw);
             taxObject.getCustomerInfo().setInvoiceNumber("M"+taxObject.getCustomerInfo().getInvoiceNumber());
             // Store tax details in a database
             new TaxService().saveToDatabase(taxObject);
         } catch (JAXBException e) {
             e.printStackTrace();
         }
         return sw.toString();
	}
	
	 public void saveToDatabase(TaxObject taxObject){
			if (taxObject.getSummaries() != null && taxObject.getSummaries().getSummary() != null) {
				for (int i = 0; i < taxObject.getSummaries().getSummary().size(); i++) {
					Summary summary = (Summary) taxObject.getSummaries().getSummary().get(i);
					summary.setInvoiceNumber(taxObject.getCustomerInfo().getInvoiceNumber());
					summary.setTotal(Double.parseDouble(taxObject.getSummaries().getTotal()));
					summary.setBillFrom(taxObject.getCustomerInfo().getBillFrom());
					summary.setBillTo(taxObject.getCustomerInfo().getBillTo());
					System.out.println("Summary:" + summary.toString());
					   String billedFrom = DateUtil.convertToUTCString(summary.getBillFrom());
					   String billedTo = DateUtil.convertToUTCString(summary.getBillTo());
					   System.out.println("Summary Billed FromL"+billedFrom+", billedTo:"+billedTo);
					String sql = "insert into t_invoice_tax_summary (`invoice_number`,`description` ,`value`,`total`,`billed_from`,`billed_to`) VALUES ('"+summary.getInvoiceNumber()+"','"+summary.getDescription()+"','"+summary.getValue()+"',"+summary.getTotal()+",'"+billedFrom+"','"+billedTo+"');";
					
					System.out.println("Sql Summary:"+sql);
					int id = new DbConnect().insert(sql);
					System.out.println("Id:"+id);

				}
			}
			try{
			for(int i=0;i<taxObject.getTaxRequestTypes().getTaxRequestType().size();i++){
				TaxRequestType taxRequestType = (TaxRequestType) taxObject.getTaxRequestTypes().getTaxRequestType().get(i);
				if(taxRequestType.getTaxitems()!=null && taxRequestType.getTaxitems().getTaxitem()!=null){
				for(int j=0;j<taxRequestType.getTaxitems().getTaxitem().size();j++){
					Taxitem taxItem = (Taxitem)taxObject.getTaxRequestTypes().getTaxRequestType().get(i).getTaxitems().getTaxitem().get(j);
					TaxInvoice taxInvoice = new TaxInvoice();
					taxInvoice.setTaxTypeName(taxRequestType.getName());
					if(taxRequestType.getId()!=null)
					taxInvoice.setTaxId(Integer.parseInt(taxRequestType.getId()));
					taxInvoice.setTaxCode(taxRequestType.getCode());
					taxInvoice.setTransTypeName(taxItem.getTax());
					taxInvoice.setInvoiceNumber(taxObject.getCustomerInfo().getInvoiceNumber());
					if(taxItem.getTaxable_measure()!=null)
					taxInvoice.setTransAmountConsidered(Double.parseDouble(taxItem.getTaxable_measure()));
					if(taxItem.getRate()!=null)
					taxInvoice.setTransTaxPercent(Double.parseDouble(taxItem.getRate()));
					if(taxItem.getAmount()!=null)
						taxInvoice.setTransAmount(Double.parseDouble(taxItem.getAmount()));
					taxInvoice.setBillFrom(taxObject.getCustomerInfo().getBillFrom());
					taxInvoice.setBillTo(taxObject.getCustomerInfo().getBillTo());
					String billedFrom = DateUtil.convertToUTCString(taxInvoice.getBillFrom());
							String billedTo = DateUtil.convertToUTCString(taxInvoice.getBillTo());
//					System.out.println("taxInvoice:"+taxInvoice.toString());
					   System.out.println("taxInvoice Billed FromL"+billedFrom+", billedTo:"+billedTo);

					String sql = "insert into t_invoice_tax_detail (`invoice_number`,`tax_code` ,`tax_group_id`,`tax_type_name`,`trans_type_name`,`trans_amount`,`trans_tax_percentage`,`trans_amount_considered`,`billed_from`,`billed_to`) VALUES ('"+taxInvoice.getInvoiceNumber()+"','"+taxInvoice.getTaxCode()+"',"+taxInvoice.getTaxId()+",'"+taxInvoice.getTaxTypeName()+"','"+taxInvoice.getTransTypeName()+"',"+taxInvoice.getTransAmount()+","+taxInvoice.getTransTaxPercent()+","+taxInvoice.getTransAmountConsidered()+",'"+billedFrom+"','"+billedTo+"');";
					System.out.println("tax Sql:"+sql);
					int id = new DbConnect().insert(sql);
//					System.out.println("Id:"+id);
				}
				}
			}
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Exception:"+e.getMessage()+" , "+e);
			}
    
 }

}
