package io.kitty.logger;

import java.io.FileNotFoundException;

/**
 * 脱敏处理专用异常类.
 */
public class DesensitizerException extends Exception {

    public DesensitizerException(Throwable cause) {
        super("desensitizer", cause.getCause());
        setStackTrace(cause.getStackTrace());
    }

    @Override
    public synchronized Throwable getCause() {
        Throwable cause = super.getCause();
        if (isSensitive(cause)) {
            return new DesensitizerException(cause);
        }
        return cause;
    }

    /**
     * 基于安全编码规范判断异常类是否涉及敏感信息.
     */
    public static boolean isSensitive(Throwable e) {
        if (e instanceof FileNotFoundException) {
            return true;
        } else {
            return e instanceof RuntimeException;
        }
    }
}
