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


	
	@Override
	public boolean sendEmail(String to, String subject, String body) {
	    boolean f = false;

	    String from = "your_email@gmail.com";
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
	            return new PasswordAuthentication("your_email@gmail.com", "your_secret_key");
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
