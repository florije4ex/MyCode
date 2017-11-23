package com.cui.code.test;

import ch.qos.logback.classic.Level;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cuishixiang on 17/5/16.
 */
public class LogBackTest {

    @Test
    public void testLogBack() {
        //log的默认级别继承自根root的级别，即debug
        //比debug级别低的日志没有输出
        Logger testLogger = LoggerFactory.getLogger("com.cui");
        testLogger.trace("trace log");
        testLogger.debug("debug log");
        testLogger.info("info log");
        testLogger.warn("warn log");
        testLogger.error("error log");

        //slf4j的log无法设置级别，需要转成logback自己的log才能设置
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(LogBackTest.class);
        logger.setLevel(Level.TRACE);
        logger.trace("trace log");
        logger.debug("debug log");
        logger.info("info log");
        logger.warn("warn log");
        logger.error("error log");

        //使用参数化记录来替代字符串的拼接，字符串的拼接（"xx"+var）消耗了性能，
        // logback的考虑就是以最小的性能（尽量不影响原有代码的执行）来记录日志
        String var = "ceuiuuju";
        logger.error("ahah " + new Object() + "  ,  " + var + " ");
        logger.error("ahah {}  ,  {} ", new Object(), var);


//        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
//        StatusPrinter.print(loggerContext);


//        日志使用：
//           1、log名不要自己取，直接使用当前类的类名 getClass()
//           2、配置不同的log，用于记录不同的组件的日志
//           3、配置不同的appender，设置格式等信息，将不同的日志分开存放
//           4、设置级别，将不同的log信息输出到不同的appender
//           5、程序中记录日志（参数化）


//        info
//        error

        try {
            int a = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
