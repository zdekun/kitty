package io.kitty.logger;

import javax.naming.InsufficientResourcesException;
import java.io.FileNotFoundException;
import java.net.BindException;
import java.security.acl.NotOwnerException;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.MissingResourceException;
import java.util.jar.JarException;

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
        } else if (e instanceof SQLException) {
            return true;
        } else if (e instanceof BindException) {
            return true;
        } else if (e instanceof OutOfMemoryError) {
            return true;
        } else if (e instanceof ConcurrentModificationException) {
            return true;
        } else if (e instanceof StackOverflowError) {
            return true;
        } else if (e instanceof MissingResourceException) {
            return true;
        } else if (e instanceof NotOwnerException) {
            return true;
        } else if (e instanceof InsufficientResourcesException) {
            return true;
        } else {
            return e instanceof JarException;
        }
    }
}
