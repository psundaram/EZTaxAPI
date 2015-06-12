/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anpi.tax;

import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import billsoft.eztax.CustomerType;
import billsoft.eztax.TaxData;

import com.anpi.app.model.InvoiceAddress;
import com.anpi.app.model.TaxInvoice;
import com.anpi.app.model.TaxService;
import com.anpi.app.model.TaxServiceMaster;
import com.anpi.app.util.DateUtil;
import com.anpi.app.util.DbConnect;
import com.anpi.app.util.HibernateUtil;
import com.anpi.domain.Address;
import com.anpi.domain.CustomerInfo;
import com.anpi.domain.Summary;
import com.anpi.domain.TaxObject;
import com.anpi.domain.TaxProperty;
import com.anpi.domain.TaxRequestType;
import com.anpi.domain.TaxRequestTypes;
import com.anpi.domain.Taxitem;
import com.anpi.util.TaxUtil;
import com.google.gson.Gson;

/**
 * REST Web Service
 *
 * @author MouliDaren
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of com.tax.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @Path("/test")
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        String returnthisString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><root><msg>Hooray !  Home page for the tax API</msg></root>";
        return returnthisString;
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }

    @Path("/runScenario2")
    @GET
    @Produces("application/xml")
    public String getAccessTax() throws ParserConfigurationException {
        ANPITaxEngine engine = new ANPITaxEngine();
        engine.init();
        ANPITaxEngine.CustomerInfo info = new ANPITaxEngine.CustomerInfo("Shelby Wagner Design",
                "RCID:100976",
                CustomerType.BUSINESS,
                9002,
                new ANPITaxEngine.Address("USA", "TX", "Dallas", "Dallas", "75219", "4510"),
                new ANPITaxEngine.Address("USA", "TX", "Dallas", "Dallas", "75219", "4510"),new Date(),new Date());

        engine.setCustomerInfo(info);

        Calendar cal = Calendar.getInstance();
        cal.set(2015, 2, 28);
        Date date = cal.getTime();

        TaxData[] taxData = engine.getAccessTax((short) 19, (short) 6, date, 89.97);
        String taxStr = new TaxUtil().createXML("Access Tax", taxData, ANPITaxEngine.df);
        System.out.println("TAX XML => " + taxStr);
        return taxStr;
    }
    
    @Path("/runScenario1")
    @GET
    @Produces("application/xml")
    public String getLineTax() throws ParserConfigurationException {
        ANPITaxEngine engine = new ANPITaxEngine();
        engine.init();
        ANPITaxEngine.CustomerInfo info = new ANPITaxEngine.CustomerInfo("Shelby Wagner Design",
                "RCID:100976",
                CustomerType.BUSINESS,
                9002,
                new ANPITaxEngine.Address("USA", "TX", "Dallas", "Dallas", "75219", "4510"),
                new ANPITaxEngine.Address("USA", "TX", "Dallas", "Dallas", "75219", "4510"),new Date(),new Date());

        engine.setCustomerInfo(info);

        Calendar cal = Calendar.getInstance();
        cal.set(2015, 2, 28);
        Date date = cal.getTime();

        TaxData[] taxData = engine.getLinesTax(date, 3);
        String taxStr = new TaxUtil().createXML("Access Tax", taxData, ANPITaxEngine.df);
        System.out.println("TAX XML => " + taxStr);
        return taxStr;
    }
    
    
    @Path("/calcTax")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public String calcTax(String content) throws JAXBException, ParserConfigurationException, ParseException {
    	System.out.println("ContentL"+content);
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
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    @Path("/saveTax")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public String calcAndSaveTax(String content) throws JAXBException, ParserConfigurationException, ParseException {
    	System.out.println("ContentL"+content);
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
            saveToDatabase(taxObject);
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
    
    @Path("/calcTax2")
    @POST
    @Consumes("application/xml")
    @Produces("application/json")
    public String calcTax2(String content) throws JAXBException, ParserConfigurationException, ParseException {
        TaxObject taxObject = new CalculateTax().generateTax(content);
        Gson gson = new Gson();
        return gson.toJson(taxObject);
    }
    
    
    @Path("/saveTax")
    @GET
    @Produces("application/xml")
    public String calculateTax() throws Exception  {
    	
    	Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		session.beginTransaction();
		
		HashMap<String, TaxProperty> invoiceMap = TaxCalculation.fetchFromDatabase(session);
		System.out.println(invoiceMap.size());
		
		for (String key : invoiceMap.keySet()) {
			System.out.println("Key: " + key + ", Value: " + invoiceMap.get(key));
			TaxProperty taxProperty = invoiceMap.get(key);
			String content = TaxCalculation.calcTax(taxProperty,session);
			String response = calculateTax(content);
			System.out.println(response);
		}
		
		
		HibernateUtil.getSessionAnnotationFactory().close();
		return "Tax calculated";
    }
   
    private String calculateTax(String content) throws Exception {
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
             saveToDatabase(taxObject);
         } catch (JAXBException e) {
             e.printStackTrace();
         }
         return sw.toString();
	}

    @Path("/cc_charge")
    @GET
    private void ccChargeCalculation(String date) throws Exception {
     Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		new CreditChargeCalculation().retrieveTax(session,date);
		HibernateUtil.getSessionAnnotationFactory().close();
    }
    
    
	public static void main(String[] args) throws Exception {
//		new GenericResource().calculateTax();
	}
    
    
    
}
