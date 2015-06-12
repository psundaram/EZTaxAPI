package com.anpi.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateFormatterAdapter extends XmlAdapter<String, Date> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Date unmarshal(final String v) throws Exception {
    	System.out.println("Parse:"+dateFormat.parse(v));
        return dateFormat.parse(v);
    }

    @Override
    public String marshal(final Date v) throws Exception {
    	System.out.println("format:"+dateFormat.format(v));
        return dateFormat.format(v);
    }
}