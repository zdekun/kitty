package io.kitty.logger;

import org.junit.Test;

public class LoggerTest {
    @Test
    public void testLog4j2() {
        TestLogger t = new TestLogger();
        t.testLog("zhangdekun\r\nxiaoyufeng", 35);
    }

}
