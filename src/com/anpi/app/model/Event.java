package com.anpi.app.model;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.anpi.app.util.DateFormatterAdapter;
import com.anpi.domain.CustomerInfo;
import com.anpi.domain.TaxObject;

@XmlRootElement(name = "event")
public class Event {

    private Date date;
    private String description;

    @XmlJavaTypeAdapter(DateFormatterAdapter.class)
    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    

    public static void main(final String[] args) throws Exception {
    	  StringWriter sw = null;
              sw = new StringWriter();
        final JAXBContext context = JAXBContext.newInstance(TaxObject.class);
        final Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        final TaxObject event = new TaxObject();
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setBillFrom(new Date());
        event.setCustomerInfo(customerInfo);

        marshaller.marshal(event, System.out);
        
        marshaller.marshal(event, sw);
        
        JAXBContext jaxbContext = JAXBContext.newInstance(TaxObject.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(sw.toString());
        TaxObject xmlParser = (TaxObject) unmarshaller.unmarshal(reader);
        System.out.println("input xml check => " + xmlParser.getCustomerInfo().getBillFrom());
    }
}