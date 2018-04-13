package com.cui.code.test.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具
 *
 * @author cuishixiang
 * @date 2018-05-11
 */
public class DateUtil {

    /**
     * 周岁计算：必须过了生日，才表示满周岁。
     * <p>
     * 1、先看“年”，用当前年份减去生日年份得出年龄age
     * 2、再看“月”，如果当前月份小于生日月份，说明未满周岁age，年龄age需减1；如果当前月份大于等于生日月份，则说明满了周岁age，计算over！
     * 3、最后"日"，如果月份相等并且当前日小于等于出生日，说明仍未满周岁，年龄age需减1；反之满周岁age，over！
     * <p>
     * 示例：
     * 2000-10-01出生，当前日期是2000-11-01，未满1周岁，算0周岁！
     * 2000-10-01出生，当前日期是2005-10-01，未满5周岁，算4周岁（生日当天未过完）！
     * 2000-10-01出生，当前日期是2005-10-02，已满5周岁了！
     *
     * @param birthday 生日
     * @return 周岁
     */
    public static int getAge(LocalDate birthday) {
        LocalDate now = LocalDate.now();

        int age = now.getYear() - birthday.getYear();
        if (age <= 0) {
            return 0;
        }

        int currentMonth = now.getMonthValue();
        int currentDay = now.getDayOfMonth();
        int bornMonth = birthday.getMonthValue();
        int bornDay = birthday.getDayOfMonth();
        if (currentMonth < bornMonth || (currentMonth == bornMonth && currentDay <= bornDay)) {
            age--;
        }

        return age < 0 ? 0 : age;
    }

    /**
     * 周岁计算
     *
     * @param birthday 生日
     * @return 周岁
     * @see DateUtil#getAge(LocalDate)
     */
    public static int getAge(Date birthday) {
        LocalDate birthdayDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return getAge(birthdayDate);
    }

    /**
     * 周岁计算
     *
     * @param birthday 生日（字符串）
     * @param format   字符串格式
     * @return 周岁
     * @see DateUtil#getAge(LocalDate)
     */
    public static int getAge(String birthday, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate birthdayDate = LocalDate.parse(birthday, formatter);
        return getAge(birthdayDate);
    }
}
