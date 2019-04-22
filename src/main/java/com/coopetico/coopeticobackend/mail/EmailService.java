/***
 * Interface para envio de emails
 */

package com.coopetico.coopeticobackend.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


/***
 * Interface para envio de emails
 * @author Kevin Jimenez
 */
@Validated
public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

    void sendSimpleMessageUsingTemplate(String to, String subject, SimpleMailMessage template, String ...templateArgs);

    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);

    /***
     * Envia un correo que contiene un link para recuperar la contraseña del usuario
     * @param para Direccion de destino
     * @param token Token para recuperar contraseña
     * @author Kevin Jimenez
     */
    void enviarCorreoRecuperarContrasena(@Email String para, @NotNull String token);
}