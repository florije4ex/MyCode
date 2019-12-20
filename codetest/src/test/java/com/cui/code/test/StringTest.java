package com.cui.code.test;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Formatter;

/**
 * String基础测试
 *
 * @author cuishixiang
 * @date 2018-06-04
 */
public class StringTest {

    /**
     * 手机号、身份证号隐藏中间几位数
     */
    @Test
    public void testReplaceAll() {
        String phone = "13300001234";
        phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        System.out.println(phone);

        String idCard = "430409200001017733";
        idCard = idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2");
        System.out.println(idCard);
    }


    /**
     * MessageFormat 的格式化是从下标{0}开始的
     */
    @Test
    public void testMessageFormat() {
        String format = MessageFormat.format("{0},{1},{3}", 1, "222", 3L, 4.0);
        String format2 = MessageFormat.format("{2},{1},{2}", 1, "222", 3L, 4.0);
        String format3 = MessageFormat.format("{1},{2},{3}", 1, "222", 3L, Arrays.asList(1, 2, 3));

        System.out.println(format);
        System.out.println(format2);
        System.out.println(format3);

    /**
     * Java中保留两位小数的几种写法：
     * 1、使用BigDecimal
     * 2、使用DecimalFormat
     * 3、使用NumberFormat
     * 4、使用java.util.Formatter
     * 5、使用String.format 来实现
     */
    @Test
    public void numberFormatTest() {
        // 金额单位：分，需要 ÷100 后转为单位元
        long moneyFen = 12345;
        BigDecimal oneHundred = new BigDecimal(100);
        BigDecimal moneyYuan = new BigDecimal(moneyFen).divide(oneHundred);

        // 1、使用BigDecimal
        // RoundingMode.HALF_UP 代表四舍五入
        BigDecimal result = moneyYuan.setScale(2, RoundingMode.HALF_UP);
        System.out.println(result);

        // 2、使用DecimalFormat
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String format = df.format(moneyYuan);
        System.out.println(format);

        // 3、使用NumberFormat
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        /*
         * setMinimumFractionDigits设置成2
         *
         * 如果不这么做，那么当value的值是100.00的时候返回100
         *
         * 而不是100.00
         */
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        /*
         * 如果想输出的格式用逗号隔开，可以设置成true
         */
        nf.setGroupingUsed(false);
        String format1 = nf.format(moneyYuan);
        System.out.println(format1);

        // 4、使用java.util.Formatter
        // %.2f % 表示 小数点前任意位数 2 表示两位小数 格式后的结果为 f 表示浮点型
        String format2 = new Formatter().format("%.2f", moneyYuan).toString();
        System.out.println(format2);

        // 5、使用String.format 来实现
        // 占位符格式为： %[index$][标识]*[最小宽度][.精度]转换符
        String format3 = String.format("%.2f", moneyYuan);
        System.out.println(format3);

        // 可用标识：
        // -，在最小宽度内左对齐,不可以与0标识一起使用。
        // 0，若内容长度不足最小宽度，则在左边用0来填充。
        // #，对8进制和16进制，8进制前添加一个0,16进制前添加0x。
        // +，结果总包含一个+或-号。
        // 空格，正数前加空格，负数前加-号。
        // ,，只用与十进制，每3位数字间用,分隔。
        // (，若结果为负数，则用括号括住，且不显示符号。
        // 可用转换符：
        //
        // b，布尔类型，只要实参为非false的布尔类型，均格式化为字符串true，否则为字符串false。
        // n，平台独立的换行符, 也可通过System.getProperty("line.separator")获取。
        // f，浮点数型（十进制）。显示9位有效数字，且会进行四舍五入。如99.99。
        // a，浮点数型（十六进制）。
        // e，指数类型。如9.38e+5。
        // g，浮点数型（比%f，%a长度短些，显示6位有效数字，且会进行四舍五入）
    }
}
