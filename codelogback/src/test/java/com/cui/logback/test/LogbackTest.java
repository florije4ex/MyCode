package com.cui.logback.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
