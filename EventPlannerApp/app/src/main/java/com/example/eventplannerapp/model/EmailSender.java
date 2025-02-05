package com.example.eventplannerapp.model;

import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private static final String USERNAME = "covid23serbia@gmail.com";
    private static final String PASSWORD = "pztynfydfyfntrqt";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;

    public static void sendActivationEmail(String to, String activationLink) {
        new SendEmailTask().execute(to, activationLink);
    }

    private static class SendEmailTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String to = params[0];
            String activationLink = params[1];

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(USERNAME, PASSWORD);
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(USERNAME));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject("Rejected registration");
                message.setText("Dear,\n\nWe could not accept your request for registration. Reason:\n\n"
                        + activationLink);

                Transport.send(message);
                System.out.println("Email successfully sent to: " + to);

            } catch (MessagingException e) {
                e.printStackTrace(); // Bolje je logirati izuzetak
            }
            return null;
        }
    }
}
