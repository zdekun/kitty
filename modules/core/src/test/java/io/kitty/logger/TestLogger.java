package io.kitty.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

public class TestLogger {
    private static final Logger logger = LoggerFactory.getLogger(TestLogger.class);

    public void testLog(String name, int age) {
//        RuntimeException e1 = new RuntimeException("123runtime321");
        FileNotFoundException e = new FileNotFoundException("xxxx");
        RuntimeException e2 = new RuntimeException("123runtime321", e);

        logger.error("test name:{},age:{}", name, age, e2);
    }
}
