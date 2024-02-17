package com.XAUS.Services.Email;

import com.XAUS.Managers.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.Executors;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;


    public void sendSimpleMessage( String to, String subject, String text) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("murilobarbosa358@gmail.com");
        helper.setTo(to);
        helper.setText(text, true);
        Executors.newSingleThreadExecutor().execute(() -> emailSender.send(message));


    }

    @Override
    public void sendRecoverPasswordMessage(String to, String subject, String text)   {

        try{

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setText(text, true);
            ClassPathResource xausLogo = new ClassPathResource("/static/images/xaus-logo.png");
            helper.addInline("xaus-logo", xausLogo);
            helper.setFrom("murilobarbosa358@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            Executors.newSingleThreadExecutor().execute(() -> emailSender.send(message));


        }catch(MessagingException e ){
            LogManager.logError(getClass(), "Error while send user message ", e);
        }

    }
}
