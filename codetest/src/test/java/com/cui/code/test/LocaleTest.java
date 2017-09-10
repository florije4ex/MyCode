package com.cui.code.test;

import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by cuishixiang on 2017-09-10.
 */
public class LocaleTest {

    /**
     * 获取java支持的所有国家和语言，以及默认的国家语言
     */
    @Test
    public void testLocale() {
        Locale[] availableLocales = Locale.getAvailableLocales();
        for (Locale availableLocale : availableLocales) {
            System.out.println(availableLocale.getDisplayCountry() + "=" + availableLocale.getCountry() + "   " + availableLocale.getDisplayLanguage() + "=" + availableLocale.getLanguage());
        }
        System.out.println(availableLocales.length);


        Locale defaultLocale = Locale.getDefault();
        System.out.println(defaultLocale.getDisplayCountry() + "=" + defaultLocale.getCountry() + "   " + defaultLocale.getDisplayLanguage() + "=" + defaultLocale.getLanguage());
    }

    @Test
    public void testResourceBundle() {
//        Locale locale = Locale.getDefault(Locale.Category.FORMAT);
        Locale locale = new Locale("en", "GB");
        System.out.println(locale.getDisplayCountry() + "=" + locale.getCountry() + "   " + locale.getDisplayLanguage() + "=" + locale.getLanguage());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("message", locale);
        System.out.println(resourceBundle.getString("001"));

    }
}
