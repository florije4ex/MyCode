package com.cui.code.test;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;

/**
 * 身份证计算
 * <p>
 * 2017年统计用区划代码和城乡划分代码(截止2017年10月31日) ：http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017/index.html
 *
 * @author cuishixiang
 * @date 2018-07-30
 */
public class IdCardTest {
    int[] weight = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};    //十七位数字本体码权重
    char[] validate = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};    //mod11,对应校验码字符值

    /**
     * 计算身份证最后一位校验码
     */
    @Test
    public void testCalcLastValidateCode() {
        System.out.println(getValidateCode("身份证前17位数字"));
    }

    /**
     * 计算身份证生日：有很多重复的，需要手动排重
     * 已知：XXXXXX ******** XXXX
     */
    @Test
    public void testCalcBirthday() {

        int nowYear = LocalDate.now().getYear();

        String areaCode = "XXXXXX";
        String suffix = "XXX";
        char validateCode = 'X';
        for (int year = 1990; year <= nowYear; year++) {
            for (int month = 1; month <= 12; month++) {
                for (int day = 1; day < 31; day++) {
                    String monthCode = month < 10 ? "0" + month : month + "";
                    String dayCode = day < 10 ? "0" + day : day + "";
                    String birthdayCode = year + monthCode + dayCode;
                    int compare = Character.compare(validateCode, getValidateCode(areaCode + birthdayCode + suffix));
                    if (compare == 0) {
                        System.out.println(birthdayCode);
                    }
                }
            }
        }
    }

    /**
     * 计算身份证生日：火车票上面的信息
     * 已知：XXXXXX XXXX**** XXXX
     */
    @Test
    public void testCalcBirthday2() {
        String areaCode = "XXXXXX";
        String year = "XXXX";
        String suffix = "XXX";
        char validateCode = '9';

        for (int month = 1; month <= 12; month++) {
            for (int day = 1; day < 31; day++) {
                String monthCode = month < 10 ? "0" + month : month + "";
                String dayCode = day < 10 ? "0" + day : day + "";
                String birthdayCode = year + monthCode + dayCode;
                int compare = Character.compare(validateCode, getValidateCode(areaCode + birthdayCode + suffix));
                if (compare == 0) {
                    System.out.println(birthdayCode);
                }
            }
        }
    }

    /**
     * 计算身份证生日：年月，火车票上面的信息
     * 已知：XXXXXX XX****XX XXXX
     */
    @Test
    public void testCalcYearMonth() {
        String areaCode = "XXXXXX";
        String day = "13";
        String suffix = "006";
        char validateCode = '5';

        for (int year = 1980; year <= 1998; year++) {
            for (int month = 1; month <= 12; month++) {
                String monthCode = month < 10 ? "0" + month : month + "";
                String birthdayCode = year + monthCode + day;
                int compare = Character.compare(validateCode, getValidateCode(areaCode + birthdayCode + suffix));
                if (compare == 0) {
                    System.out.println(birthdayCode);
                }
            }
        }
    }

    /**
     * 计算地区，也有重复的
     * 已知条件：****** XXXXXXXX XXXX
     */
    @Test
    public void testCalcArea() throws IOException {
        String birthdayCode = "1890XXXX";
        String suffix = "XXX";
        char validateCode = '9';

        InputStream inputStream = this.getClass().getResourceAsStream("/行政区划代码.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        properties.forEach((key, value) -> {
            int compare = Character.compare(validateCode, getValidateCode(key + birthdayCode + suffix));
            if (compare == 0) {
                System.out.println(key.toString() + " " + value.toString());
            }
        });
    }

    /**
     * 计算区、县
     * 已知条件：XXXX** XXXXXXXX XXXX
     */
    @Test
    public void testCalcCountry() throws IOException {
        String provinceCity = "XXXX";
        String birthdayCode = "199XXXX";
        String suffix = "XXX";
        char validateCode = '7';


        InputStream inputStream = this.getClass().getResourceAsStream("/行政区划代码.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        for (int country = 0; country < 99; country++) {
            String countryCode = country < 10 ? "0" + country : country + "";
            String areaCode = provinceCity + countryCode;
            int compare = Character.compare(validateCode, getValidateCode(areaCode + birthdayCode + suffix));
            if (compare == 0 && properties.containsKey(areaCode)) {
                System.out.println(provinceCity.substring(0, 2) + "0000 " + properties.getProperty(provinceCity.substring(0, 2) + "0000"));
                System.out.println(provinceCity + "00 " + properties.getProperty(provinceCity + "00"));
                System.out.println(areaCode + " " + properties.getProperty(areaCode));
            }
        }
    }

    /**
     * 身份证解析
     *
     * @throws IOException
     */
    @Test
    public void testParseInfo() throws IOException {
        String idCard = "身份证号码";

        InputStream inputStream = this.getClass().getResourceAsStream("/行政区划代码.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        System.out.println("省份：" + properties.getProperty(idCard.substring(0, 2) + "0000"));
        System.out.println("城市：" + properties.getProperty(idCard.substring(0, 4) + "00"));
        String country = properties.getProperty(idCard.substring(0, 6));
        System.out.println("区县：" + country);
        String birthday = idCard.substring(6, 14);
        System.out.println("生日：" + birthday);
        System.out.println(String.format("%s，%s，您是这天第%s个出生的。", birthday, country, idCard.substring(14, 17)));
    }


    /**
     * 获取身份证检验码
     *
     * @param id17 身份证前17位数字
     * @return 身份证检验码（最后一位数字）
     */
    private char getValidateCode(String id17) {
        int sum = 0;
        for (int i = 0; i < id17.length(); i++) {
            sum = sum + Integer.parseInt(String.valueOf(id17.charAt(i))) * weight[i];
        }
        int mode = sum % 11;
        return validate[mode];
    }
}

