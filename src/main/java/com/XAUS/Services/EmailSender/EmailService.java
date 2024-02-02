package com.XAUS.Services.EmailSender;

public interface EmailService {

    public void sendSimpleMessage(String to, String subject, String text);
}
