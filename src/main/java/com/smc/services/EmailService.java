package com.smc.services;

public interface EmailService {

	boolean sendEmail(String to,  String subject, String body);

	void sendEmailwithHtml();
}
