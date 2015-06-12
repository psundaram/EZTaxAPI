/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anpi.tax;

import billsoft.eztax.CustomerType;
import billsoft.eztax.TaxData;

import com.anpi.domain.CustomerInfo;
import com.anpi.domain.Summaries;
import com.anpi.domain.Summary;
import com.anpi.domain.TaxObject;
import com.anpi.domain.TaxRequestType;
import com.anpi.domain.TaxRequestTypes;
import com.anpi.parser.XmlParser;
import com.anpi.tax.ANPITaxEngine;
import static com.anpi.tax.ANPITaxEngine.df;
import com.anpi.util.TaxUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author MouliDaren
 */
public class CalculateTax {
    
    TaxObject taxObject = null;
    Map summaryMap = null;
    
    public TaxObject generateTax(String xml) throws JAXBException, ParserConfigurationException, ParseException{
        taxObject = new XmlParser().xmlObject(xml);
//        TaxObject returnThisObject = taxObject;
        ANPITaxEngine engine = new ANPITaxEngine();
        engine.init();
        ANPITaxEngine.CustomerInfo info = new ANPITaxEngine.CustomerInfo(taxObject.getCustomerInfo().getCompanyIdentifier(),
                taxObject.getCustomerInfo().getCustNumber(),
                CustomerType.BUSINESS,
                Long.parseLong(taxObject.getCustomerInfo().getInvoiceNumber()),
                new ANPITaxEngine.Address(taxObject.getCustomerInfo().getBa().getCountry(), 
                                            taxObject.getCustomerInfo().getBa().getState(), 
                                            taxObject.getCustomerInfo().getBa().getCounty(), 
                                            taxObject.getCustomerInfo().getBa().getCity(), 
                                            taxObject.getCustomerInfo().getBa().getZip(),
                                            taxObject.getCustomerInfo().getBa().getZip4()),
                new ANPITaxEngine.Address(taxObject.getCustomerInfo().getJa().getCountry(), 
                                            taxObject.getCustomerInfo().getJa().getState(), 
                                            taxObject.getCustomerInfo().getJa().getCounty(), 
                                            taxObject.getCustomerInfo().getJa().getCity(), 
                                            taxObject.getCustomerInfo().getJa().getZip(),
                                            taxObject.getCustomerInfo().getJa().getZip4()),taxObject.getCustomerInfo().getBillFrom(),taxObject.getCustomerInfo().getBillTo());
        
        System.out.println("CustomerInfof:"+taxObject.getCustomerInfo().getBillFrom());
        engine.setCustomerInfo(info);
        
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        
        String taxTypeName = null;
        ArrayList<TaxData> taxDataList = null;
        Date date = null;
       try{ for(TaxRequestType taxType : taxObject.getTaxRequestTypes().getTaxRequestType()){
            taxTypeName = taxType.getName();
//            Should be handled based on the code below - TODO
//            taxType.getCode();
            date = formatter.parse(taxType.getDate());
            System.out.println("Date:"+date);
            if(taxTypeName.equalsIgnoreCase("Access Tax")){
                System.out.println("Getting access tax");
                taxDataList = new ArrayList<TaxData>(Arrays.asList(engine.getAccessTax((short) 19, (short) 6, date, Double.parseDouble(taxType.getAmount()))));
            } else if(taxTypeName.equalsIgnoreCase("Lines Tax")){
                System.out.println("Getting lines tax" + taxType.getLineCount());
                taxDataList = new ArrayList<TaxData>(Arrays.asList(engine.getLinesTax(date, Integer.parseInt(taxType.getLineCount()))));
            } else if(taxTypeName.equalsIgnoreCase("Invoice Tax")){
                System.out.println("Getting invoice tax"+date);
                taxDataList = new ArrayList<TaxData>(Arrays.asList(engine.getVOIPInvoiceTax(date)));
            } else if(taxTypeName.equalsIgnoreCase("Tollfree Tax")){
                System.out.println("Getting Tollfree Tax");
                taxDataList = new ArrayList<TaxData>(Arrays.asList(engine.getAccessTax((short) 19, (short) 30, date, Double.parseDouble(taxType.getAmount()))));
            } else if(taxTypeName.equalsIgnoreCase("InterStateUsage Tax")){
                System.out.println("Getting InterStateUsage Tax");
                taxDataList = new ArrayList<TaxData>(Arrays.asList(engine.getInterStateUsage((short) 19, (short) 6, date, Double.parseDouble(taxType.getUsageMin()), Double.parseDouble(taxType.getAmount()))));
            } else if(taxTypeName.equalsIgnoreCase("IntraStateUsage Tax")){
                System.out.println("Getting IntraStateUsage Tax");
                taxDataList = new ArrayList<TaxData>(Arrays.asList(engine.getIntraStateUsage((short) 19, (short) 6, date, Double.parseDouble(taxType.getUsageMin()), Double.parseDouble(taxType.getAmount()))));
            } else if(taxTypeName.equalsIgnoreCase("Outbound International") || taxTypeName.equalsIgnoreCase("Outbound International Mobile") || taxTypeName.equalsIgnoreCase("Outbound Intl NADP") ||
                    taxTypeName.equalsIgnoreCase("Inbound Non Toll Free") || taxTypeName.equalsIgnoreCase("Outbound Toll Free")){
                System.out.println("Getting "+taxTypeName+" Tax");
                taxDataList = new ArrayList<TaxData>(Arrays.asList(engine.getInternationalUsage((short) 19, (short) 6, date, Double.parseDouble(taxType.getUsageMin()), Double.parseDouble(taxType.getAmount()))));
            }
            taxType.setTaxitems(new TaxUtil().getTaxItems(taxTypeName, taxDataList, ANPITaxEngine.df));
            addToSummary(taxDataList);
        }
       }catch(Exception e){
    	   e.printStackTrace();
    	   System.out.println("Error :"+ e.getMessage()+ " stack :" +e.getStackTrace());
       }
        createSummary();
        return taxObject;
    }
    
    private void addToSummary(ArrayList<TaxData> taxDataList){
        for(TaxData tax : taxDataList){
            if (summaryMap == null) {
                summaryMap = new HashMap();
            }
            String key = tax.getTaxLevel() + "_" + tax.getDescription();
            Double val = (Double) summaryMap.get(key);
            if (val == null) {
                val = new Double(0.00);
            }
            summaryMap.put(key, new Double(val.doubleValue() + tax.getTaxAmount()));
        }
    }
    
    private void createSummary(){
        if (summaryMap == null || summaryMap.isEmpty()) {
            System.out.println("No summary found...");
            return;
        }
        double totalTax = 0;
        Set keys = summaryMap.keySet();
        String k;
        Double val;
        Summary summary = null;
        List summaryList = new ArrayList();
        for(Object key : keys){
            summary = new Summary();
            k = (String) key;
            val = (Double) summaryMap.get(key);
            System.out.println("Desc:" + k + ",value:" + df.format(val));
            totalTax += val;
            summary.setDescription(k);
            summary.setValue(String.valueOf(val));
            summaryList.add(summary);
        }
        
        Summaries summaries = new Summaries();
        summaries.setSummary(summaryList);
        summaries.setTotal(String.valueOf(totalTax));
        taxObject.setSummaries(summaries);
    }
    
}
