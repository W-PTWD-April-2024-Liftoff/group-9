package com.parkrangers.parkquest_backend.service;

import com.parkrangers.parkquest_backend.model.response.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSubscriptionEmail(String toEmail, String parkCode, List<Event> events) {
        StringBuilder body = new StringBuilder("You've successfully subscribed to updates for park: " + parkCode + "\n\n");

        if (events == null || events.isEmpty()) {
            body.append("Currently, there are no upcoming events.\n");
        } else {
            body.append("Here are some upcoming events:\n");
            for (Event event : events) {
                body.append("- ")
                        .append(event.getTitle() != null ? event.getTitle() : "Untitled Event")
                        .append(" on ")
                        .append(event.getDate() != null ? event.getDate() : "TBD")
                        .append("\n");
            }
        }


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("You're subscribed to park updates!");
        message.setText(body.toString());

        try {
            mailSender.send(message);
            System.out.println("Email sent to " + toEmail + " for park " + parkCode);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + toEmail + " for park " + parkCode);
            e.printStackTrace();
        }
    }
}