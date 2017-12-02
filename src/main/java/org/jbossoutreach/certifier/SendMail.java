package org.jbossoutreach.certifier;

import org.jbossoutreach.certifier.model.Student;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;


public class SendMail {

    SendMail() {
    }

    public static void sendMail(Student student) {
        // Recipient's email ID
        String to = student.getEmail();

        // Getting system properties
        Properties properties = System.getProperties();

        try {
            URL url = SendMail.class.getResource("/application.properties");
            InputStream input = null;
            if (url!=null) {
                input = SendMail.class.getClassLoader().getResourceAsStream("application.properties");
            } else {
                input = SendMail.class.getClassLoader().getResourceAsStream("default.properties");
            }
            properties.load(input);
        } catch (IOException w) {
            w.printStackTrace();
        }

        String from = properties.getProperty("mail.sender");
        String user = properties.getProperty("mail.user");
        String password = properties.getProperty("mail.password");
        String certificatefilname = properties.getProperty("certfilename");
        String messageSubject = properties.getProperty("message.subject");
        String messageText = properties.getProperty("message.text");

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,
                                password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(messageSubject);
            //message.setText("This is actual message");

            BodyPart messageBodyPart1 = new MimeBodyPart();

            // Fill the message
            messageBodyPart1.setText(String.format(messageText,student.getName()));

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart1);

            File certFile = new File(student.getCertificate());

            if (certFile.exists()) {
                DataSource source = new FileDataSource(certFile);
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(certificatefilname);
                messageBodyPart.setText(messageText);

                multipart.addBodyPart(messageBodyPart);
            }
            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
