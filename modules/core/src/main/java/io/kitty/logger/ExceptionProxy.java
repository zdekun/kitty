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
 * 脱敏处理代理类.
 */
public class ExceptionProxy extends Exception {

    private final Throwable proxy;

    public ExceptionProxy(String msg, Throwable proxy) {
        super(msg);
        this.proxy = proxy;
        setStackTrace(proxy.getStackTrace());
    }

    @Override
    public synchronized Throwable getCause() {
        Throwable cause = proxy.getCause();
        if (cause != null) {
            return new ExceptionProxy(desensitizeClassNameAndMessage(cause), cause);
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

    /**
     * 脱敏异常的类名与异常消息.
     */
    public static String desensitizeClassNameAndMessage(Throwable e) {
        StringBuilder buff = new StringBuilder();

        String className = e.getClass().getName();
        desensitizeClassName(className, e, buff);

        buff.append(": ");

        String message = e.getMessage();
        desensitizeMessage(message, e, buff);

        return buff.toString();
    }

    private static void desensitizeClassName(String className, Throwable e, StringBuilder target) {
        if (isSensitive(e)) {
            desensitizeString(className, false, target);
        } else {
            target.append(className);
        }
    }

    private static void desensitizeMessage(String message, Throwable e, StringBuilder target) {
        desensitizeString(message, isSensitive(e), target);
    }

    private static void desensitizeString(String data, boolean sensitized, StringBuilder to) {
        int maskMod = 4;
        if (sensitized) {
            maskMod = 3;
        }
        for (int i = 0; i < data.length(); i++) {
            if (((i + 1) % maskMod) == 0) {
                to.append('-');
            } else {
                to.append(data.charAt(i));
            }
        }
    }

}
