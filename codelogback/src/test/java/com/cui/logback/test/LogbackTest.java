package com.cui.logback.test;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Logback基础测试
 *
 * @author cuishixiang
 * @date 2018-02-26
 */
public class LogbackTest {

    @Test
    public void testLogback() {
        Logger logger = LoggerFactory.getLogger("com.cui.logback.test.LogbackTest");
        logger.debug("Hello world.");
    }

    /**
     * 通过StatusPrinter打印logback的内部状态
     */
    @Test
    public void testLogbackInternalStatus() {
        Logger logger = LoggerFactory.getLogger("com.cui.logback.test.LogbackTest");
        logger.debug("Hello world2.");

        // print internal state
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(loggerContext);
    }

    @Test
    public void testLogBackLevel() {
        //log的默认级别继承自根root的级别，即debug
        //比debug级别低的日志没有输出
        Logger testLogger = LoggerFactory.getLogger("com.cui");
        testLogger.trace("trace  log");
        testLogger.debug("debug log");
        testLogger.info("info log");
        testLogger.warn("warn log");
        testLogger.error("error \n log");

        //slf4j的log无法设置级别，需要转成logback自己的log才能设置
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(LogbackTest.class);
        logger.setLevel(Level.TRACE);
        logger.trace("trace log");
        logger.debug("debug log");
        logger.info("info log");
        logger.warn("warn log");
        logger.error("error log");
    }

    /**
     * 日志使用：
     * 1、log名不要自己取，直接使用当前类的类名 getClass()
     * 2、配置不同的log，用于记录不同的组件的日志
     * 3、配置不同的appender，设置格式等信息，将不同的日志分开存放
     * 4、设置级别，将不同的log信息输出到不同的appender
     * 5、程序中记录日志（参数化）
     */
    @Test
    public void testLogUse() {
        Logger logger = LoggerFactory.getLogger(LogbackTest.class);

        //使用参数化记录来替代字符串的拼接，字符串的拼接（"xx"+var）消耗了性能，
        // logback的考虑就是以最小的性能（尽量不影响原有代码的执行）来记录日志
        String var = "ceuiuuju";
        logger.error("ahah " + new Object() + "  ,  " + var + " ");
        logger.error("ahah {}  , {} ", new Object(), var);

        try {
            int a = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("logger error：", e);
        }
    }

    /**
     * 50 个线程，打印固定长度日志，总共 100万行日志，
     * 性能测试：带 %file:%line 参数的耗时和去除此参数的耗时
     * 带：19104、18502    106MB
     * 不带：8927、8893    86MB
     * 结论：性能差异很大，所以对于性能要求高的系统，不要带此参数
     */
    @Test
    public void testFileLinePerformance() throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        int threadCount = 50;
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int j = 0; j < 20000; j++) {
                    logger.info("test performance……");
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        logger.info("logback with param: [%file:%line] cost time:{}", costTime);
        System.out.println(costTime);
    }
}
