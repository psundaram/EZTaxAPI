/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anpi.util;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import billsoft.eztax.TaxData;

import com.anpi.domain.Taxitem;
import com.anpi.domain.Taxitems;

/**
 *
 * @author MouliDaren
 */
public class TaxUtil {
    
    private String taxXml;
    
    public String createXML(String taxType, TaxData [] taxes, DecimalFormat df) throws ParserConfigurationException {
        System.out.println("====================Begin Tax: " + taxType + "============================");
        if (taxes == null) {
            System.out.println("No taxes returned for the following transaction:" + taxType);
        } else {
            System.out.println("************* # of taxes: " + taxes.length);
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("taxinfo");
            doc.appendChild(rootElement);
            
            Element taxItem = null;
            for (TaxData tax : taxes) {
                taxItem = doc.createElement("taxitem");
                rootElement.appendChild(taxItem);
                
                taxItem.appendChild(createItem("tax", tax.getTaxLevel().name() +" - "+ tax.getDescription(), doc));
                taxItem.appendChild(createItem("rate", df.format(tax.getRate()), doc));
                taxItem.appendChild(createItem("amount", df.format(tax.getTaxAmount()), doc));
                taxItem.appendChild(createItem("taxable_measure", df.format(tax.getTaxableMeasure()), doc));
                System.out.print("Tax: " + tax.getTaxLevel() + " - " + tax.getDescription());
                System.out.println("   Rate: " + df.format(tax.getRate())
                        + ",  Amount: " + df.format(tax.getTaxAmount())
                        + ",  Taxable Measure: " + df.format(tax.getTaxableMeasure()));
//                TODO
//                addToTaxSummary(tax);
            }
            System.out.println("-------------------End Tax: " + taxType + "-------------------------------");
            taxXml = getStringFromDocument(doc);
        }
        return taxXml.toString();
    }
    
    private Node createItem(String tagName, String value, Document doc) throws ParserConfigurationException{
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(value));
        return element;
    }
    
    public String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Taxitems getTaxItems(String taxType, ArrayList<TaxData> taxes, DecimalFormat df) throws ParserConfigurationException{
        System.out.println("====================Begin Tax: " + taxType + "============================");
        if (taxes == null) {
            System.out.println("No taxes returned for the following transaction:" + taxType);
        } else {
            System.out.println("************* # of taxes: " + taxes.size());
            
            Taxitems taxitems = new Taxitems();
            
            Taxitem taxItem;
            List<Taxitem> taxItemList = new ArrayList<Taxitem>();
            for (TaxData tax : taxes) {
                taxItem = new Taxitem();             
                taxItem.setTax(tax.getTaxLevel().name() +" - "+ tax.getDescription());
                taxItem.setRate(df.format(tax.getRate()));
                taxItem.setAmount(df.format(tax.getTaxAmount()));
                taxItem.setTaxable_measure(df.format(tax.getTaxableMeasure()));
                taxItemList.add(taxItem);
                
                System.out.print("Tax: " + tax.getTaxLevel() + " - " + tax.getDescription());
                System.out.println("   Rate: " + df.format(tax.getRate())
                        + ",  Amount: " + df.format(tax.getTaxAmount())
                        + ",  Taxable Measure: " + df.format(tax.getTaxableMeasure()));
//                TODO
//                addToTaxSummary(tax);
            }
            taxitems.setTaxitem(taxItemList);
            System.out.println("-------------------End Tax: " + taxType + "-------------------------------");
            return taxitems;
        }
        return null;
    }
}
