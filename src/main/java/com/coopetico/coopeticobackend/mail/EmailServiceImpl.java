package com.coopetico.coopeticobackend.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Validated
@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

    // No ha sido formalmente testeado
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    // No ha sido formalmente testeado
    @Override
    public void sendSimpleMessageUsingTemplate(String to,
                                               String subject,
                                               SimpleMailMessage template,
                                               String ...templateArgs) {
        String text = String.format(template.getText(), templateArgs);
        sendSimpleMessage(to, subject, text);
    }

    // No ha sido formalmente testeado
    @Override
    public void sendMessageWithAttachment(String to,
                                          String subject,
                                          String text,
                                          String pathToAttachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /***
     * Envia un correo que contiene un link para recuperar la contraseña del usuario
     * @param para Direccion de destino
     * @param token Token para recuperar contraseña
     * @author Kevin Jimenez
     */
    @Async("emailThreadExecutor")
    @Override
    public void enviarCorreoRecuperarContrasena(@Email  String para, @NotNull String token) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(para);
            helper.setText("<!doctype html>\n" +
                    "\n" +
                    "<html lang=\"es\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"utf-8\">\n" +
                    "\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "<h1> Codigo para recuperar su contraseña</h1>" +
                    "<p>Abra el siguiente link para recuperar su contraseña: http://localhost:4200/usuarios/" +
                        "cambiarContrasena/" + para + "/" + token + "</p>"+
                    "</body>\n" +
                    "</html>", true);
            helper.setSubject("Recuperar contraseña CoopeticoApp");
            emailSender.send(message);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}