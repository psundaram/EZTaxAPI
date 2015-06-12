package com.anpi.app.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DateUtil {

	
	/**
	 * Convert Date  to UTC Time zone
	 *
	 * @param d the date
	 * @return the date
	 */
	public static Date convertToUTC(Date d){
		DateTime dt = new DateTime(d);
		dt = dt.withZone(DateTimeZone.forID("UTC"));
		System.out.println("UTC Date : " + dt);
		return dt.toDate();
	}
	
	/**
	 * Convert Date to utc string.
	 *
	 * @param d the date
	 * @return the utc date string
	 */
	public static String convertToUTCString(Date d){
		DateTime dt = new DateTime(d);
		dt = dt.withZone(DateTimeZone.forID("UTC"));
		System.out.println("UTC Date in String: " + dt.toString("yyyy-MM-dd HH:mm:ss"));
		return  dt.toString("yyyy-MM-dd HH:mm:ss");
		
	}
}