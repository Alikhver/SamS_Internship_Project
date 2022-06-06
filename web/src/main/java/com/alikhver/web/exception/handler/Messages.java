package com.alikhver.web.exception.handler;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {


    public static String getMessageForLocale(String messageKey, Locale locale) {

        return ResourceBundle.getBundle("messages", locale)
                .getString(messageKey);
    }

    public static String getMessageForLocale(String messageKey, Locale locale, String param) {

        String pattern = getMessageForLocale(messageKey, locale);
        return MessageFormat.format(pattern, param);
    }

}
