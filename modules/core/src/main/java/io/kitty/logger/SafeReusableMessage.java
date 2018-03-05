package io.kitty.logger;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ReusableMessage;

/**
 * ReusableMessage装饰者.
 * <p>
 * <p>
 * 基于被装饰的对象，增加两个新功能：<br />
 * 1、防日志伪造攻击<br />
 * 2、异常脱敏
 * </p>
 */
public class SafeReusableMessage implements ReusableMessage {
    private final ReusableMessage decoratingReusableMessage;

    public SafeReusableMessage(ReusableMessage decoratingReusableMessage) {
        this.decoratingReusableMessage = decoratingReusableMessage;
    }

    @Override
    public Object[] swapParameters(Object[] emptyReplacement) {
        return decoratingReusableMessage.swapParameters(emptyReplacement);
    }

    @Override
    public short getParameterCount() {
        return decoratingReusableMessage.getParameterCount();
    }

    @Override
    public Message memento() {
        return decoratingReusableMessage.memento();
    }

    @Override
    public String getFormattedMessage() {
        return decoratingReusableMessage.getFormattedMessage();
    }

    @Override
    public String getFormat() {
        return decoratingReusableMessage.getFormat();
    }

    @Override
    public Object[] getParameters() {
        return decoratingReusableMessage.getParameters();
    }

    @Override
    public Throwable getThrowable() {
        Throwable e = decoratingReusableMessage.getThrowable();
        if (DesensitizerException.isSensitive(e)) {
            // 防止异常泄露敏感信息
            return new DesensitizerException(e);
        }
        return e;
    }

    @Override
    public void formatTo(StringBuilder buffer) {
        decoratingReusableMessage.formatTo(buffer);
        int length = buffer.length();
        for (int i = 0; i < length; i++) {
            char value = buffer.charAt(i);
            // 防日志伪造攻击
            if (value == '\r' || value == '\n') {
                buffer.setCharAt(i, '-');
            }
        }
    }
}
