package la.com.unitel.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Component
public class Translator {
    public static ResourceBundleMessageSource messageSource;

    @Autowired
    public Translator(ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    public static String toLocale(String keyword) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(keyword, (Object[])null, locale);
    }
}
