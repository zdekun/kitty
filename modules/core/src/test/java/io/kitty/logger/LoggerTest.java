package io.kitty.logger;

import org.junit.Test;

public class LoggerTest {
    @Test
    public void testLog4j2() {
        TestLogger t = new TestLogger();
        t.testLog("zhangdekun\r\nxiaoyufeng", 35);
    }

    @Test
    public void testMDC() {
        TestLogger t = new TestLogger();
        t.testMDC();
    }

}
