package com.XAUS.Services.EmailSender;

import javax.mail.MessagingException;
public interface EmailService {

    public void sendSimpleMessage(String to, String subject, String text) throws MessagingException;
    public void sendRecoverPasswordMessage(String to, String subject, String text);
}