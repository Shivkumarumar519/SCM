package com.smc.services.impl;

import java.util.Properties;

import org.springframework.stereotype.Service;

import com.smc.services.EmailService;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{


	/*
	 * @Override public boolean sendEmail(String to, String body, String subject) {
	 * boolean f = false;
	 * 
	 * String from = "smartcontactmanager2025@gmail.com"; String host =
	 * "smtp.gmail.com"; Properties properties = System.getProperties();
	 * properties.put("mail.smtp.host", host); properties.put("mail.smtp.port",
	 * "465"); properties.put("mail.smtp.ssl.enable", "true");
	 * properties.put("mail.smtp.auth", "true");
	 * 
	 * // Get session Session session = Session.getInstance(properties, new
	 * Authenticator() {
	 * 
	 * @Override protected PasswordAuthentication getPasswordAuthentication() {
	 * //return new PasswordAuthentication("shivkumarumar519@gmail.com",
	 * "yezzkjhzlxszehnr"); return new
	 * PasswordAuthentication("smartcontactmanager2025@gmail.com",
	 * "zbfpiqwjsokndrlp"); } });
	 * 
	 * session.setDebug(true);
	 * 
	 * MimeMessage mimeMessage = new MimeMessage(session); try {
	 * mimeMessage.setFrom(from);
	 * 
	 * // Ensure 'to' is a valid email address
	 * System.out.println("Sending email to: " + to);
	 * mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	 * 
	 * mimeMessage.setSubject(body); mimeMessage.setContent(subject, "text/html");
	 * 
	 * Transport.send(mimeMessage); System.out.println("Email sent successfully.");
	 * f = true; } catch (Exception e) { e.printStackTrace(); } return f; }
	 * 
	 * @Override public void sendEmailwithHtml() { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 */
	
	@Override
	public boolean sendEmail(String to, String subject, String body) {
	    boolean f = false;

	    String from = "smartcontactmanager2025@gmail.com";
	    String host = "smtp.gmail.com";
	    Properties properties = System.getProperties();
	    properties.put("mail.smtp.host", host);
	    properties.put("mail.smtp.port", "465");
	    properties.put("mail.smtp.ssl.enable", "true");
	    properties.put("mail.smtp.auth", "true");

	    // Get session
	    Session session = Session.getInstance(properties, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication("smartcontactmanager2025@gmail.com", "bitcuwdpaxosdgsl");
	        }
	    });

	    session.setDebug(true);

	    MimeMessage mimeMessage = new MimeMessage(session);
	    try {
	        mimeMessage.setFrom(new InternetAddress(from));

	        // Ensure 'to' is a valid email address
	        System.out.println("Sending email to: " + to);
	        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	        
	        

	        // Set Subject
	        mimeMessage.setSubject(subject);
	        
	        
	        

	        // Set Content (HTML format to support paragraphs)
	        String emailBody = "<html><body>" + body + "</body></html>";
	        mimeMessage.setContent(emailBody, "text/html");
	        
	        

	        Transport.send(mimeMessage);
	        System.out.println("Email sent successfully.");
	        f = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return f;
	}

	@Override
	public void sendEmailwithHtml() {
		// TODO Auto-generated method stub
		
	}






	/*
	 * @Autowired private JavaMailSender mailSender;
	 *
	 * @Value("${spring.mail.properties.domain_name}") private String domainName;
	 *
	 * @Override public void sendEmail(String to, String subject, String body) {
	 * SimpleMailMessage message = new SimpleMailMessage(); message.setTo(to);
	 * message.setSubject(subject); message.setText(body);
	 * message.setFrom(domainName);
	 *
	 * mailSender.send(message);
	 *
	 * }
	 *
	 * @Override public void sendEmailwithHtml() { // TODO Auto-generated method
	 * stub
	 *
	 * }
	 */



}
