package com.finalproject.Backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.frontend.url:http://localhost:5173}")
    private String frontendUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationEmail(String to, String token, String userName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Verifica tu email - Otra Málaga");
        
        String verificationUrl = frontendUrl + "/verify-email?token=" + token;
        String emailBody = String.format(
            "Hola %s,\n\n" +
            "Gracias por registrarte en Otra Málaga.\n\n" +
            "Por favor, verifica tu dirección de email haciendo clic en el siguiente enlace:\n\n" +
            "%s\n\n" +
            "Este enlace expirará en 24 horas.\n\n" +
            "Si no creaste esta cuenta, puedes ignorar este email.\n\n" +
            "Saludos,\n" +
            "El equipo de Otra Málaga",
            userName, verificationUrl
        );
        
        message.setText(emailBody);
        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String to, String token, String userName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Recuperación de contraseña - Otra Málaga");
        
        String resetUrl = frontendUrl + "/reset-password?token=" + token;
        String emailBody = String.format(
            "Hola %s,\n\n" +
            "Recibimos una solicitud para restablecer tu contraseña.\n\n" +
            "Haz clic en el siguiente enlace para crear una nueva contraseña:\n\n" +
            "%s\n\n" +
            "Este enlace expirará en 1 hora.\n\n" +
            "Si no solicitaste este cambio, puedes ignorar este email.\n\n" +
            "Saludos,\n" +
            "El equipo de Otra Málaga",
            userName, resetUrl
        );
        
        message.setText(emailBody);
        mailSender.send(message);
    }
}

