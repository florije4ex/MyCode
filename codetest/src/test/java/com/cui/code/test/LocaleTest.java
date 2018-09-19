package com.cui.code.test;

import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 国际化测试：ResourceBundle、Locale、MessageFormat
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

        Locale zh = Locale.forLanguageTag("zh");
        System.out.println(zh.getDisplayCountry() + "=" + zh.getCountry());

        Locale china = Locale.CHINA;
        System.out.println(china.getDisplayCountry() + "=" + china.getCountry() + "   " + china.getDisplayLanguage() + "=" + china.getLanguage());

        Locale chinese = Locale.CHINESE;
        System.out.println(chinese.getDisplayCountry() + "=" + chinese.getCountry() + "   " + chinese.getDisplayLanguage() + "=" + chinese.getLanguage());
    }

    @Test
    public void testResourceBundle() {
        //Locale locale = Locale.getDefault(Locale.Category.FORMAT);
        //Locale locale = Locale.getDefault();
        //Locale locale = new Locale("en", "GB");
        //Locale locale = new Locale("en", "US");
        //Locale locale = new Locale("en");
        Locale locale = new Locale("zh");
        //测试使用不存在的语言时的场景
        //Locale locale = new Locale("");
        //Locale locale = new Locale(null);
        //Locale locale = new Locale("nono");
        System.out.println(locale.getDisplayCountry() + "=" + locale.getCountry() + "   " + locale.getDisplayLanguage() + "=" + locale.getLanguage());
        // 加载资源不对？有疑问时请先详细看此方法的注释及源码
        ResourceBundle resourceBundle = ResourceBundle.getBundle("message", locale);
        System.out.println(resourceBundle.getString("001"));

    }
}
