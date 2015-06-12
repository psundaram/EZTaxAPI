/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anpi.parser;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.anpi.domain.TaxObject;

/**
 * 
 * @author MouliDaren
 */
public class XmlParser {

	public TaxObject xmlObject(String xml) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(com.anpi.domain.TaxObject.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		StringReader reader = new StringReader(xml);
		TaxObject xmlParser = (TaxObject) unmarshaller.unmarshal(reader);
		System.out.println("input xml check => " + xmlParser.getCustomerInfo().getBillFrom());
		return xmlParser;

	}
}
