package com.XAUS.Services.Email;

import jakarta.mail.MessagingException;
public interface EmailService {

    public void sendSimpleMessage(String to, String subject, String text) throws MessagingException;
    public void sendRecoverPasswordMessage(String to, String subject, String text);
}
