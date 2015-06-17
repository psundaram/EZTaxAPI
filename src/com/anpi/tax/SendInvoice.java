package com.anpi.tax;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.anpi.app.model.TaxInvoiceSummary;
import com.anpi.app.util.HibernateUtil;
import com.google.common.base.Strings;

public class SendInvoice {

	public static void main(String[] args) throws Exception {

		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		List<TaxInvoiceSummary> listOfTaxInvoiceSummaries = new SendInvoice().retrieveTaxInfo(session,null);
		String response = new SendInvoice().sendMail(listOfTaxInvoiceSummaries);
		System.out.println("response: " + response);
	}

	public List<TaxInvoiceSummary> retrieveTaxInfo(Session session,String billDate) throws Exception {
		Transaction transaction = session.beginTransaction();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		Date convertedDate = new Date();
		Criteria criteria = session.createCriteria(TaxInvoiceSummary.class);
		criteria.add(Restrictions.eq("mailSent", 0));
		criteria.setMaxResults(10);
		if(!Strings.isNullOrEmpty(billDate)){
			 convertedDate = (Date) format.parse(billDate);
			 criteria.add(Restrictions.le("billTo",convertedDate));
		}
		List<TaxInvoiceSummary> listOfTaxInvoice = criteria.list();
		System.out.println("li"+listOfTaxInvoice.get(0).getCustomerId());
		System.out.println(listOfTaxInvoice.get(0).getCustomer().getCustomerId());
		System.out.println(listOfTaxInvoice.size());
		session.close();
		return listOfTaxInvoice;
	}
		
	public String sendMail(List<TaxInvoiceSummary> listOfTax) throws Exception{
	
		Store store = null;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.anpi.com");
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		Transaction transaction =  session.beginTransaction();

		/****** IMAP Server ********/
		props.setProperty("mail.store.protocol", "pop3");
		javax.mail.Session emailSession = javax.mail.Session.getDefaultInstance(props, null);
		props.setProperty("mail.store.protocol", "pop3");
		emailSession = javax.mail.Session.getDefaultInstance(props, null);
		for(TaxInvoiceSummary taxInvoiceSummary : listOfTax){
			Message formMessage = new MimeMessage(emailSession);
			String fromAddress = "donotReply@anpi.com";
			String toAddress = taxInvoiceSummary.getCustomer().getEmail();
			String accountNo = taxInvoiceSummary.getCustomer().getCrmCCustomerId();
			String customerName = taxInvoiceSummary.getCustomer().getCustomerName();
			System.out.println("toAddress:"+toAddress);
			formMessage.setFrom(new InternetAddress(fromAddress));
			formMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
			formMessage.setRecipient(Message.RecipientType.BCC, new InternetAddress("psundaram@anpi.com"));
			formMessage.setSubject("Your ANPI VIP Invoice is Ready to View");
			String content = "<html style='background:#d8dad5; background:url(http://www.anpi.com/wp-content/uploads/crosshatch.jpg)'> <head> </head> <body style='font-family:arial, helvetica neue, helvetica, verdana;'>  <table cellpadding='50' width='100%' style='font-family: arial, helvetica neue, helvetica, verdana'> <tr> <td id='mainBackground' bgcolor='#D8DAD5' style='background:url('http://www.anpi.com/wp-content/uploads/crosshatch.jpg'), #e6e6e6; background-repeat:repeat;'> <table id='mainContent' align='center' style='font-family:arial, helvetica neue, helvetica, verdana; width:680px; margin-left:auto; margin-right:auto;'> <tr> <td bgcolor='#FFFFFF' colspan='2' cellpadding='25' style='width:675px; padding:25px 25px 50px 25px; background:#fff; border-radius: 9px; border:1px solid #aaacab;'> <table id='header' width='100%' style='font-family:arial, helvetica neue, helvetica, verdana;'> <tr> <td id='title' style='vertical-align:bottom; width:100%'> <h1 style='font-family: arial, helvetica neue, helvetica, verdana; color:#0061a4; font-size:27px; font-weight:400; margin:0 10	0px 0 0'>Your ANPI VIP Invoice is ready</h1> </td> <td id='logo' style='vertical-align:top; text-align:right;'> <img width='118' height='54' src='http://www.anpi.com/wp-content/uploads/email/email-logo.png'> </td> </tr> <tr class='break'> <td height='40'> </td> </tr> </table> <p style='margin:0; font-size:12px; margin:0 0 20px 0;'>Dear "+customerName+",<br > <br /> Your monthly ANPI VIP invoice for account "+accountNo+" is now available for viewing in the ANPI VIP Portal. If you are paying by credit card, the credit card you have on file will be charged on 06/30/2015. <blockquote style='margin-top:5.0pt;margin-bottom:5.0pt'><p style='mso-margin-top-alt:0in;margin-right:0in;margin-bottom:15.0pt;margin-left:0in'><a href='https://vip.anpi.com/login'><span style='text-decoration:none'><img border=0 width=114 height=35  src='http://www.anpi.com/wp-content/uploads/email/View-Invoice.png' alt='View Invoice'></span></a></span></p></blockquote><P style='MARGIN-BOTTOM: 15pt; MARGIN-LEFT: 0cm; MARGIN-RIGHT: 0cm; mso-margin-top-alt: 0cm'><SPAN style='FONT-SIZE: 9pt; FONT-FAMILY: 'Arial','sans-serif''>"
					+"If you have any questions about this invoice, please <a href='mailto:customercare@anpi.com'>contact us</a>..  </p> <P style='MARGIN-BOTTOM: 15pt; MARGIN-LEFT: 0cm; MARGIN-RIGHT: 0cm; mso-margin-top-alt: 0cm'><SPAN style='FONT-SIZE: 9pt; FONT-FAMILY: 'Arial','sans-serif''>And thanks for trusting your business to ANPI. We are 100% committed to your success.</SPAN></P> <P style='MARGIN-BOTTOM: 15pt; MARGIN-LEFT: 0cm; MARGIN-RIGHT: 0cm; mso-margin-top-alt: 0cm'><SPAN style='FONT-SIZE: 9pt; FONT-FAMILY: 'Arial','sans-serif''>ANPI Customer Care<BR>(855) 492-2300<BR><A href='mailto:CustomerCARE@ANPI.com'>CustomerCARE@anpi.com</A></SPAN></P></td> </tr> <tr> <td> </td> </tr> </table> </td> </tr> </table> </body></html>";
			formMessage.setContent(content, "text/html");
			Transport.send(formMessage);
			taxInvoiceSummary.setMailSent(1);
			session.update(taxInvoiceSummary);
		}
		transaction.commit();
		return "Mail sent successfully";
	}
	
}
