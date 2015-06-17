/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anpi.tax;

import java.io.StringWriter;
import java.text.ParseException;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Session;

import billsoft.eztax.CustomerType;
import billsoft.eztax.TaxData;

import com.anpi.app.model.TaxInvoice;
import com.anpi.app.model.TaxInvoiceSummary;
import com.anpi.app.service.TaxService;
import com.anpi.app.util.DateUtil;
import com.anpi.app.util.DbConnect;
import com.anpi.app.util.HibernateUtil;
import com.anpi.domain.Summary;
import com.anpi.domain.TaxObject;
import com.anpi.domain.TaxProperty;
import com.anpi.domain.TaxRequestType;
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
	
	
	private Session getCurrentSession() {       
	    return HibernateUtil.getSessionFactory().getCurrentSession();
	}

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

    /** Calculate and save Tax for a Xml Request */
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
            new TaxService().saveToDatabase(taxObject);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
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
    
    
    /** Fetch uncalculated tax for a invoice, generate EZTax Request Xml and then store the tax*/
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
			String response = new TaxService().calculateTax(content);
			
			System.out.println(response);
		}
		HibernateUtil.getSessionAnnotationFactory().close();
		return "Tax calculated";
    }
    
    
    /** Credit charge calculation */
    @Path("/calcCC")
    @GET
    @Produces("application/xml") 
    public String calculateCC(@QueryParam("date") String date,@QueryParam("toDate") String toDate,@QueryParam("val") String val) throws Exception  {
    	System.out.println("Date:"+date);
    	System.out.println("toDate"+toDate);
    	System.out.println("val:"+val);
    	 Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
			new CreditChargeCalculation().retrieveTax(session,date,toDate,val);
//			HibernateUtil.getSessionAnnotationFactory().close();
			return "CC Charge calculated";
    }
    
    /** Send mail to customer for the status of invoice generation */
    @Path("/sendMail")
    @GET
    @Produces("application/xml") 
    public String sendMail(@QueryParam("date") String date) throws Exception  {
		System.out.println("Date:" + date);
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		List<TaxInvoiceSummary> listOfTaxInvoiceSummaries = new SendInvoice().retrieveTaxInfo(session, date);
		String response = new SendInvoice().sendMail(listOfTaxInvoiceSummaries);
		System.out.println("response: " + response);
		return response;
    }
   
   

    
}
