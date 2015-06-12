package com.anpi.tax;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.anpi.app.model.BillingDocument;
import com.anpi.app.util.HibernateUtil;

public class SendInvoice {

	public static void main(String[] args) throws Exception {

		Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<BillingDocument> listOfBillingDocuments = new SendInvoice().sendEmail(session);
		System.out.println(listOfBillingDocuments.size());

	}

	private List<BillingDocument> sendEmail(Session session) throws Exception {
		Criteria criteria = session.createCriteria(BillingDocument.class);
		criteria.add(Restrictions.eq("mailSent", 0));
		List<BillingDocument> listOfBillingDocuments = criteria.list();
		System.out.println(listOfBillingDocuments.get(0).toString());
		Store store = null;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.anpi.com");
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		/****** IMAP Server ********/
		props.setProperty("mail.store.protocol", "pop3");
		javax.mail.Session emailSession = javax.mail.Session.getDefaultInstance(props, null);
		props.setProperty("mail.store.protocol", "pop3");
		emailSession = javax.mail.Session.getDefaultInstance(props, null);
		for (BillingDocument billingDocument : listOfBillingDocuments) {
			Message formMessage = new MimeMessage(emailSession);
			String fromAddress = "donotReply@anpi.com";
			String toAddress = "psundaram@anpi.com";
			formMessage.setFrom(new InternetAddress(fromAddress));
			formMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
			formMessage.setRecipient(Message.RecipientType.CC, new InternetAddress("psundaram@anpi.com"));
			formMessage.setSubject("Invoice Summary");
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			String content = "<html style='background:#d8dad5; background:url(http://www.anpi.com/wp-content/uploads/crosshatch.jpg)'> <head> </head> <body style='font-family:arial, helvetica neue, helvetica, verdana;'>  <table cellpadding='50' width='100%' style='font-family: arial, helvetica neue, helvetica, verdana'> <tr> <td id='mainBackground' bgcolor='#D8DAD5' style='background:url('http://www.anpi.com/wp-content/uploads/crosshatch.jpg'), #e6e6e6; background-repeat:repeat;'> <table id='mainContent' align='center' style='font-family:arial, helvetica neue, helvetica, verdana; width:680px; margin-left:auto; margin-right:auto;'> <tr> <td bgcolor='#FFFFFF' colspan='2' cellpadding='25' style='width:675px; padding:25px 25px 50px 25px; background:#fff; border-radius: 9px; border:1px solid #aaacab;'> <table id='header' width='100%' style='font-family:arial, helvetica neue, helvetica, verdana;'> <tr> <td id='title' style='vertical-align:bottom; width:100%'> <h1 style='font-family: arial, helvetica neue, helvetica, verdana; color:#0061a4; font-size:27px; font-weight:400; margin:0 10	0px 0 0'>Your ANPI Invoice is ready</h1> </td> <td id='logo' style='vertical-align:top; text-align:right;'> <img width='118' height='54' src='http://www.anpi.com/wp-content/uploads/email/email-logo.png'> </td> </tr> <tr class='break'> <td height='40'> </td> </tr> </table> <p style='margin:0; font-size:12px; margin:0 0 20px 0;'>Dear ABC,<br > <br /> Please find the attached file containing the invoice data. If you are paying by credit card, the credit card you have on file will be charged on 30th of this month. <br> <br/>"
+"If you have any questions about this invoice, please <a href='mailto:customercare@anpi.com'>contact us</a>..  </p> <P style='MARGIN-BOTTOM: 15pt; MARGIN-LEFT: 0cm; MARGIN-RIGHT: 0cm; mso-margin-top-alt: 0cm'><SPAN style='FONT-SIZE: 9pt; FONT-FAMILY: 'Arial','sans-serif''>And thanks for trusting your business to ANPI. We are 100% committed to your success.</SPAN></P> <P style='MARGIN-BOTTOM: 15pt; MARGIN-LEFT: 0cm; MARGIN-RIGHT: 0cm; mso-margin-top-alt: 0cm'><SPAN style='FONT-SIZE: 9pt; FONT-FAMILY: 'Arial','sans-serif''>ANPI Customer Care<BR>(855) 492-2300<BR><A href='mailto:CustomerCARE@ANPI.com'>CustomerCARE@anpi.com</A></SPAN></P></td> </tr> <tr> <td> </td> </tr> </table> </td> </tr> </table> </body></html>";
			messageBodyPart.setContent(content, "text/html");
			multipart.addBodyPart(messageBodyPart);

			List<String> filePath = downloadFiles(billingDocument.getPath(), billingDocument.getUuid());
			if (null != filePath) {
				MimeBodyPart attachPart = null;
				for (String fileString : filePath) {
					attachPart = new MimeBodyPart();
					attachPart.attachFile(fileString);
					multipart.addBodyPart(attachPart);
				}
			}
			formMessage.setContent(multipart);
			Transport.send(formMessage);
			deleteGeneratedFiles(filePath);
		}
		return listOfBillingDocuments;
	}

	public boolean deleteGeneratedFiles(List<String> filePaths) {
		System.out.println("Entering deleteGeneratedFiles");
		File f = null;
		boolean successValue = false;
		List<String> deletedFiles = new ArrayList<String>();
		for (String path : filePaths) {
			f = new File(path);
			boolean isdeleted = f.delete();
			if (isdeleted) {
				// System.out.println("The generated attachement is deleted.");
				deletedFiles.add(path);
			} else {
				// System.out.println("The genereated attachement "+path+" failed to get deleted.");
			}
		}
		System.out.println("filePaths.size:" + filePaths.size() + " ,deletedFiles.size:" + deletedFiles.size());
		if (filePaths.size() == deletedFiles.size()) {
			successValue = true;
		}
		System.out.println("Exiting deleteGeneratedFiles --> response: " + successValue);
		return successValue;
	}

	private List<String> downloadFiles(String path, String uuid) throws Exception {
		// List<String> fileStr = new ArrayList<String>();

		System.out.println("Entering downloadFiles");
		List fileList = new ArrayList();
		String[] filePathsArr = path.split(",");
		String[] uuidArr = uuid.split(",");
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (GeneralSecurityException e) {
		}

		for (int i = 0; i < filePathsArr.length; i++) {
			String fileName = "";
			URL url = new URL(filePathsArr[i] + uuidArr[i]);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			int responseCode = httpConn.getResponseCode();
			// always check HTTP response code first
			if (responseCode == HttpURLConnection.HTTP_OK) {

				String disposition = httpConn.getHeaderField("Content-Disposition");
				String contentType = httpConn.getContentType();
				int contentLength = httpConn.getContentLength();
				if (disposition != null) {
					// extracts file name from header field
					int index = disposition.indexOf("filename=");
					if (index > 0) {
						fileName = disposition.substring(index + 10, disposition.length() - 1);
					}
				} else {
					// extracts file name from URL
					String fileURL = filePathsArr[i];
					fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
				}

				System.out.println("Content-Type = " + contentType);
				System.out.println("Content-Disposition = " + disposition);
				System.out.println("Content-Length = " + contentLength);
				System.out.println("fileName = " + fileName);

				// opens input stream from the HTTP connection
				InputStream inputStream = httpConn.getInputStream();
				String saveFilePath = System.getProperty("user.dir") + "/" + fileName;
				System.out.println("SaveFilePath:" + saveFilePath);

				// opens an output stream to save into file
				FileOutputStream outputStream = new FileOutputStream(saveFilePath);

				int bytesRead = -1;
				byte[] buffer = new byte[4096];
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

				outputStream.close();
				inputStream.close();

				System.out.println("File downloaded");
			} else {
				System.out.println("No file to download. Server replied HTTP code: " + responseCode);
			}
			httpConn.disconnect();
			fileList.add(fileName);
			// filesToBeDeleted.add(fileName);
		}
		System.out.println("Exiting downloadFiles");
		return fileList;

	}

}
