package com.igitan.springboothome.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;

  @Async
  public void sendWelcomeEmail(String toEmail, String username) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(toEmail);
      message.setSubject("Welcome to Supermarket Billing System!");
      message.setText(
          "Hi " + username + ",\n\nWelcome! Your account is ready. You can now use our system to manage your billing.");

      mailSender.send(message);
      System.out.println("✅ Welcome email sent to " + toEmail);
    } catch (Exception e) {
      System.err.println("❌ Failed to send welcome email to " + toEmail + ": " + e.getMessage());
    }
  }

  @Async
  public void sendNotificationEmail(String toEmail, String subject, String content) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setTo(toEmail);
    helper.setSubject(subject);
    helper.setText(content, true);

    mailSender.send(message);
    System.out.println("✅ Notification email sent to " + toEmail);
  }
}
