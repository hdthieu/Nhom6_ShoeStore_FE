package com.shoestore.client.service.impl;

import com.shoestore.client.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private RestTemplate restTemplate;
    private static final String SERVER_API_URL = "http://localhost:8765/notification/sendEmail";

    @Override
    public String sendInvoiceEmail(Map<String, Object> orderDetails) {
        String url = "http://localhost:8765/notification/sendEmail";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(orderDetails, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        return response.getBody().get("message").toString();
    }


//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendEmail(String toEmail, String subject, String htmlContent) throws MessagingException {
//        // Tạo đối tượng MimeMessage
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//        // Thiết lập người nhận và tiêu đề
//        helper.setTo(toEmail);
//        helper.setSubject(subject);
//
//        // Cài đặt nội dung email (nội dung HTML)
//        helper.setText(htmlContent, true);
//
//        // Gửi email
//        mailSender.send(message);
//    }
}
