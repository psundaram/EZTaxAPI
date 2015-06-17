package com.anpi.tax;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.anpi.app.model.BillingGroup;
import com.anpi.app.model.InvoicePaymentLog;
import com.anpi.app.model.TaxInvoiceSummary;
import com.anpi.app.util.HibernateUtil;
import com.anpi.domain.CCChargeRequest;
import com.anpi.domain.CCChargeResponse;
import com.anpi.domain.Order;
import com.anpi.domain.Profile;
import com.google.common.base.Strings;
import com.google.gson.Gson;

public class CreditChargeCalculation {
	
	
	public static int generateRandomNumbers(){
		Random random = new Random();
		int num = 11 * new Random().nextInt();
		if(num <= 0){
//			System.out.println("Negative --> " + num);
			return generateRandomNumbers();
		}
		else {
			return num;
		}
	}
	
	
	public String callAPI(String request) throws Exception {
		System.out.println("REquest: "+ request);
		String response = null;
		String urlStr = "http://fabric01tstap01.anpinetwork.com/api/CreditCard/ProfileCharge";
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Content-Type", "application/json");

		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
		writer.write(request);
		writer.flush();
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}
		String output;
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		while ((output = br.readLine()) != null) {
//			System.out.println(output);
			response = output;
		}
		System.out.println("Output from Server .... \n" );

		conn.disconnect();
		return response;
		
	}
	
	public static void main(String[] args) throws Exception {
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		new CreditChargeCalculation().retrieveTax(session,"01/04/2015",null,"1");
	}

	public void  retrieveTax(Session session,String date,String toDate,String value) throws Exception {
		boolean isMonth = false;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		Date convertedDate = new Date();
		 Date tobillDate = new Date();
		String hql = null;
		Transaction transaction =  session.beginTransaction();
		if(!Strings.isNullOrEmpty(date)){
			 convertedDate = (Date) format.parse(date);
			 System.out.println(convertedDate);
			 if(!Strings.isNullOrEmpty(toDate)){
				  tobillDate = (Date) format.parse(toDate);
				  hql = "from TaxInvoiceSummary t,BillingGroup b where t.billingGroupId = b.billingGroupId and t.billTo BETWEEN :date AND :toDate  and bill_type='CreditCard'"+
						"and payment_status='UP'";
			 }else if(!Strings.isNullOrEmpty(value)){
				 if(value.equals("1")){
					 hql = "from TaxInvoiceSummary t,BillingGroup b where t.billingGroupId = b.billingGroupId and t.billTo >= :date   and bill_type='CreditCard'"+
								"and payment_status='UP'"; 
				 }else{
					 hql = "from TaxInvoiceSummary t,BillingGroup b where t.billingGroupId = b.billingGroupId and t.billTo <= :date   and bill_type='CreditCard'"+
								"and payment_status='UP'"; 
				 }
			 }else{
				 
				 int month = convertedDate.getMonth()+1;
				 
				 hql = "from TaxInvoiceSummary t,BillingGroup b where t.billingGroupId = b.billingGroupId and month(t.billTo) = "+month+" and bill_type='CreditCard'"+
							"and payment_status='UP'";
				 isMonth = true;
			 }
		}
		else{
		 hql = "from TaxInvoiceSummary t,BillingGroup b where t.billingGroupId = b.billingGroupId and bill_type='CreditCard'"+
		"and payment_status='UP'";
		}

		System.out.println("hql:"+hql);
		Query query = session.createQuery(hql);
		if(!isMonth && !Strings.isNullOrEmpty(date)){
			query.setDate("date", convertedDate);
			if(!Strings.isNullOrEmpty(toDate)){
				query.setDate("toDate", tobillDate);
			}
		}
		
		query.setMaxResults(1);
		List<Object[]> list = query.list();
		System.out.println(list.size());
        for(Object[] arr : list){
//            System.out.println(Arrays.toString(arr));
        	TaxInvoiceSummary taxInvoiceSummary = (TaxInvoiceSummary) arr[0];
        	BillingGroup billingGroup = (BillingGroup) arr[1];
        	System.out.println("tax"+ taxInvoiceSummary.getBillingGroupId());
        	System.out.println("billing:"+ billingGroup.getBillingGroupId());
				CCChargeRequest request = new CCChargeRequest();
				Order order = new Order();
				Profile profile = new Profile();
				profile.setCustomerRefNum(billingGroup.getCcProfile());
				order.setOrderId(taxInvoiceSummary.getCustomerId()+"_"+taxInvoiceSummary.getInvoiceNumber());
				int number = generateRandomNumbers();
				System.out.println("Random "+ number);
				order.setRetryTrace(number);
				order.setAmount(String.valueOf(taxInvoiceSummary.getTotalBill()));
				request.setOrder(order);
				request.setProfile(profile);
				
				String json = new Gson().toJson(request);
				
				String responseStr = new CreditChargeCalculation().callAPI(json);
				System.out.println("response: " + responseStr);
				
				CCChargeResponse response = new Gson().fromJson(responseStr, CCChargeResponse.class);
//				
				if(!Strings.isNullOrEmpty(response.getProcStatus()) && !Strings.isNullOrEmpty(response.getProcStatusMessage()) && response.getProcStatus().equals("0") && response.getProcStatusMessage().equals("Approved")){
//					
					System.out.println(taxInvoiceSummary.getId() + " . " + taxInvoiceSummary.getCustomerId() + " . "+ taxInvoiceSummary.getInvoiceNumber());
					taxInvoiceSummary.setPaymentDate(new Date());
					taxInvoiceSummary.setPaymentType(billingGroup.getBillType());
					taxInvoiceSummary.setPaymentRefId(response.getGatewayTransactionReferenceNumber());
					taxInvoiceSummary.setPaymentStatus("P");
					System.out.println(taxInvoiceSummary.getPaymentStatus());
					session.update(taxInvoiceSummary);
					response.setIsSuccess(1);
					session.save(response);
					
					InvoicePaymentLog invoicePaymentLog = new InvoicePaymentLog();
					
					PropertyUtils.copyProperties(invoicePaymentLog, taxInvoiceSummary);
					System.out.println(invoicePaymentLog.getBilledFrom() + ", " + invoicePaymentLog.getBilledTo());
					invoicePaymentLog.setBilledFrom(taxInvoiceSummary.getBillFrom());
					invoicePaymentLog.setBilledTo(taxInvoiceSummary.getBillTo());
					invoicePaymentLog.setAccountNumber(billingGroup.getAccountNumber());
					invoicePaymentLog.setInvoiceAmount(taxInvoiceSummary.getTotalBill());
					invoicePaymentLog.setCustomerId(taxInvoiceSummary.getCustomerId());
					invoicePaymentLog.setPaymentType(billingGroup.getBillType());
					invoicePaymentLog.setPaymentRefId(response.getGatewayTransactionReferenceNumber());
					invoicePaymentLog.setCcNumber(billingGroup.getCcDigit());
					invoicePaymentLog.setExpMonth(billingGroup.getExpMonth());
					invoicePaymentLog.setExpYear(billingGroup.getExpYear());
					invoicePaymentLog.setCardType(billingGroup.getCardType());
					invoicePaymentLog.setName(billingGroup.getNameOnCard());
					invoicePaymentLog.setRoutingNumber(billingGroup.getRoutingNumber());
					invoicePaymentLog.setBankName(billingGroup.getBankName());
					invoicePaymentLog.setAttemptedAmount(taxInvoiceSummary.getTotalBill());
					invoicePaymentLog.setAuthCode(response.getAuthorizationCode());
					invoicePaymentLog.setProviderName("CHASE");
					// TODO
					invoicePaymentLog.setPaymentDate(new Date());
					invoicePaymentLog.setRequest(json);
					invoicePaymentLog.setResponse(responseStr);
					session.save(invoicePaymentLog);
					
				}else{
					System.out.println("Invalid data --> "+ response.getFaultCode() + " , " + response.getErrorMessage());
					response.setOrderID(taxInvoiceSummary.getCustomerId()+"_"+taxInvoiceSummary.getInvoiceNumber());
					response.setIsSuccess(0);
					session.save(response);
				}
//				System.exit(0);
			}
        session.getTransaction().commit();
	}

}
