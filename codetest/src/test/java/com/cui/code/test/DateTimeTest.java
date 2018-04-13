package com.cui.code.test;

import com.cui.code.test.util.DateUtil;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.junit.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期测试
 *
 * @author cuishixiang
 * @date 2018-03-06
 */
public class DateTimeTest {

    /**
     * java.util.Date ：一个古老的类，从JDK1.0开始就存在了，但是由于设计的不太好，大多数方法都已被废弃了
     * 剩下这么两个构造方法和比较的方法
     */
    @Test
    public void testDate() {
        Date date = new Date();
        System.out.println(date);

        Date date2 = new Date(date.getTime() + 1000L);
        System.out.println(date2);

        System.out.println(date.after(date2));
        System.out.println(date.before(date2));
        System.out.println(date.getTime());
    }

    /**
     * java.util.Calendar：从JDK1.1开始的，是一个接口定义，
     */
    @Test
    public void testCalendar() {
        //获取默认时区和语言的日历对象
        Calendar calendar = Calendar.getInstance();

        System.out.println(calendar);
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH));
        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.DAY_OF_YEAR));
        System.out.println(calendar.get(Calendar.HOUR));
        System.out.println(calendar.get(Calendar.MINUTE));
        System.out.println(calendar.get(Calendar.SECOND));
        System.out.println(calendar.get(Calendar.MILLISECOND));

        //calendar与date互转
        Date date = calendar.getTime();
        System.out.println(date);

        date.setTime(date.getTime() + 1000L);
        calendar.setTime(date);
        System.out.println(calendar.getTime());

        //calendar的指定，月份是从0开始的
        calendar.set(Calendar.MONTH, 1);
        System.out.println(calendar.getTime());
        System.out.println("月份的最大值：" + calendar.getMaximum(Calendar.MONTH));

        //calendar的加减运算，如果超出限制会自动进位
        calendar.add(Calendar.DATE, 30);
        System.out.println(calendar.getTime());

        //calendar的set是延迟修改的，在下次get时才会计算，所以修改时得考虑进位后可能不生效
        calendar.set(2018, 3, 10);
        calendar.set(Calendar.DATE, 31);
        //System.out.println(calendar.getTime());  //如果不注释此句，则在此进行了计算会获取到 2018-5-1，因为4月到31号自动进位了
        calendar.set(Calendar.MONTH, 6);
        System.out.println(calendar.getTime());//如果上句未注释，则将获取到 2018-7-1，而不是2018-6-31了

        calendar.set(1959, 2, 19, 14, 10, 10);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println(calendar.getTime().getTime());
        calendar.set(1970, 0, 1, 8, 0, 0);
        System.out.println(calendar.getTime());
        System.out.println(calendar.getTime().getTime());
    }

    /**
     * 获取日期时间
     */
    @Test
    public void testCurrentDate() {
        //当前时间
        System.out.println(new Date());
        //当前时间——>日期
        System.out.println(DateTime.now().toLocalDate().toDate());
        //解析指定日期
        System.out.println(DateTime.parse("2118-01-01").toDate());
        //获取当天的0点
        System.out.println(DateTime.now().withMillisOfDay(0));

        System.out.println(DateTime.now().withMillisOfDay(0).getMillis());
        //获取当日最小时间：00:00:00
        System.out.println(new Time(DateTime.now().withMillisOfDay(0).getMillis()));
        //获取当日最大时间：23:59:59
        System.out.println(new Time(DateTime.now().secondOfDay().withMaximumValue().getMillis()));
    }

    /**
     * 日期计算
     */
    @Test
    public void testCalcDate() {
        DateTime dateTime = new DateTime();
        DateTime dateTime1 = DateTime.parse("2018-04-04T23:59:59");
        DateTime dateTime2 = DateTime.parse("2018-03-29T18:00:00Z");

        System.out.println(Days.daysBetween(dateTime, dateTime1).getDays());
        System.out.println(Days.daysBetween(dateTime1, dateTime).getDays());
        System.out.println(dateTime2.toLocalDateTime());

        Date startDate = DateTime.parse("2018-04-08T00:00:00").toDate();
        Date endDate = DateTime.parse("2018-04-21T23:59:59").toDate();
        Date endDate2 = DateTime.parse("2018-04-21T23:59:59Z").toDate();
        Date now = new Date();
        System.out.println(startDate.toLocaleString());
        System.out.println(endDate.toLocaleString());
        System.out.println(endDate2.toLocaleString());
        System.out.println(now.toLocaleString());
        System.out.println(now.before(startDate));
        System.out.println(now.after(endDate));

        String startTime = "2018-02-05 00:00:00";
        String endTime = "2018-02-05 00:00:00";
        String pattern = "yyyy-MM-dd HH:mm:ss";

        LocalDateTime now1 = LocalDateTime.now();
        LocalDateTime startDateTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern(pattern));
        LocalDateTime endDateTime = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern(pattern));
        System.out.println(startDateTime == null);
        System.out.println(endDateTime == null);
        System.out.println(startDateTime.isAfter(now1));
        System.out.println(endDateTime.isBefore(now1));

        LocalDateTime localDateTime = LocalDateTime.parse("2018-05-28 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Date from = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(from);
        System.out.println(Days.daysBetween(new DateTime(), new DateTime(from)).getDays());

    }

    /**
     * 时间日期格式化
     */
    @Test
    public void testDateFormat() throws ParseException {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(now);
        System.out.println("now：" + simpleDateFormat.format(now));
        System.out.println("now：" + now.getTime());

        Date parse = simpleDateFormat.parse("2018-03-28 16:58:07");
        System.out.println("startTime：" + parse.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        System.out.println(sdf.format(now));
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        System.out.println(sdf2.format(now));


        Date date = new Date(1526955538000L);
        System.out.println(simpleDateFormat.format(date));

        date = new Date(1526959138000L);
        System.out.println(simpleDateFormat.format(date));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate parse1 = LocalDate.parse("2018-03-28 16:58:07", formatter);
        System.out.println(parse1);
    }

    /**
     * Java 8 Date Time
     */
    @Test
    public void testLocalDateAndTime() {
        LocalDate now = LocalDate.now();
        LocalTime max = LocalTime.MAX;
        //获取当日的最大时间
        LocalDateTime localDateTime = now.atTime(max);
        //LocalDate转Date
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        Date date = Date.from(instant);

        System.out.println(now);
        System.out.println(max);
        System.out.println(localDateTime);
        System.out.println(zonedDateTime);
        System.out.println(instant);
        System.out.println(date);

        //获取当日的最大时间
        LocalDateTime localDateTime1 = LocalDateTime.of(LocalDate.now(), max);
        System.out.println(localDateTime1);

        //获取年月日
        System.out.println(now.getYear());
        System.out.println(now.getMonthValue());
        System.out.println(now.getDayOfMonth());

        //Date转LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(localDate);
    }


    /**
     * 周岁计算
     */
    @Test
    public void testAge() {
        System.out.println(DateUtil.getAge("2012-05-10 16:27:00", "yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateUtil.getAge("2012-05-11 16:27:00", "yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateUtil.getAge("2012-05-12 16:27:00", "yyyy-MM-dd HH:mm:ss"));
        LocalDate now = LocalDate.now();
        now = now.withYear(1992);
        System.out.println(DateUtil.getAge(now));
        now = now.withMonth(12);
        now.withDayOfMonth(28);
        System.out.println(DateUtil.getAge(Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant())));
    }
}
