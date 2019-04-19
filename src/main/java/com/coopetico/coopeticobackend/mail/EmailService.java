package com.coopetico.coopeticobackend.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Validated
public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
    void sendSimpleMessageUsingTemplate(String to, String subject, SimpleMailMessage template, String ...templateArgs);
    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);
    void enviarCorreoRecuperarContrasena(@Email String to, @NotNull String text);
}