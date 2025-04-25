package com.shoestore.client.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Map;

public interface EmailService {
    public String sendInvoiceEmail(Map<String, Object> orderDetails);
    // public void sendEmail(String toEmail, String subject, String htmlContent) throws MessagingException;
}
