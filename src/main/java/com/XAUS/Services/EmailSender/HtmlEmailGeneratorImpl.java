package com.XAUS.Services.EmailSender;

import com.XAUS.Managers.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class HtmlEmailGeneratorImpl implements HtmlEmailGenerator{

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public String generateRecoverPasswordHtml(String userName, String url) {

        try{
            Context context = new Context();
            context.setVariable("username", userName);
            context.setVariable("recoveryLink", url);
            return templateEngine.process("recover-password", context);

        }catch(Exception e){
            LogManager.logError(getClass(), "Error while processing password recovery html", e);
        }

        return null;
    }
}
