package com.XAUS.Services.EmailSender;

public interface HtmlEmailGenerator {

    public String generateRecoverPasswordHtml(String userName, String url);

}
