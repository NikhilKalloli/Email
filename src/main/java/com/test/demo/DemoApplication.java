package com.test.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    private EmailSender emailSender;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendEmails() {
        try {
            // Read email data from a .txt file
            String emailData = readEmailDataFromFile("email-data.txt");

            // Split email data into individual lines
            String[] lines = emailData.split("\\r?\\n");

            // Process each line
			// Process each line
for (String line : lines) {
    // Split the line into email, subject, and body
    String[] parts = line.split(",", 3);
    if (parts.length == 3) {
        String emailAddress = parts[0].trim();
        String subject = parts[1].trim();
        String body = parts[2].trim();

        // Send email
        emailSender.sendEmail(emailAddress, subject, body);
    } else {
        // Try a more relaxed split
        String[] relaxedParts = line.split("\\s*,\\s*", 3);
        if (relaxedParts.length == 3) {
            String emailAddress = relaxedParts[0].trim();
            String subject = relaxedParts[1].trim();
            String body = relaxedParts[2].trim();

            // Send email
            emailSender.sendEmail(emailAddress, subject, body);
        } else {
            System.err.println("Invalid line format: " + line);
        }
    }
}



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readEmailDataFromFile(String filePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }
}

