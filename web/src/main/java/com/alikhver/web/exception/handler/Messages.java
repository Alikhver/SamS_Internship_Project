package com.alikhver.web.exception.handler;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {


    public static String getMessageForLocale(String messageKey, Locale locale) {

        return ResourceBundle.getBundle("messages", locale)
                .getString(messageKey);
    }

    public static String getMessageForLocale(String messageKey, Locale locale, Long param) {

        String pattern = getMessageForLocale(messageKey, locale);
        String message = MessageFormat.format(pattern, param);
        return message;
    }

}
