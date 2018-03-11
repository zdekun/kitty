package io.kitty.logger;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory2;
import org.apache.logging.log4j.message.ReusableMessage;
import org.apache.logging.log4j.message.ReusableMessageFactory;
import org.apache.logging.log4j.util.PerformanceSensitive;

import java.io.Serializable;

/**
 * ReusableMessageFactory装饰者.
 * <p>
 * 基于被装饰的对象，生成安全增强的ReusableMessage对象.
 * </p>
 */
@PerformanceSensitive("allocation")
public final class SafeReusableMessageFactory implements MessageFactory2, Serializable {
    private final ReusableMessageFactory decoratingReusableMessageFactory;

    public SafeReusableMessageFactory() {
        decoratingReusableMessageFactory = new ReusableMessageFactory();
    }

    @Override
    public Message newMessage(CharSequence charSequence) {
        Message reusableMsg = decoratingReusableMessageFactory.newMessage(charSequence);
        return new SafeReusableMessage((ReusableMessage) reusableMsg);
    }

    @Override
    public Message newMessage(String message, Object p0) {
        Message reusableMsg = decoratingReusableMessageFactory.newMessage(message, p0);
        return new SafeReusableMessage((ReusableMessage) reusableMsg);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1) {
        Message reusableMsg = decoratingReusableMessageFactory.newMessage(message, p0, p1);
        return new SafeReusableMessage((ReusableMessage) reusableMsg);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2) {
        Message reusableMsg = decoratingReusableMessageFactory.newMessage(message, p0, p1, p2);
        return new SafeReusableMessage((ReusableMessage) reusableMsg);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3) {
        Message reusableMsg = decoratingReusableMessageFactory.newMessage(message, p0, p1, p2, p3);
        return new SafeReusableMessage((ReusableMessage) reusableMsg);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        Message reusableMsg = decoratingReusableMessageFactory.newMessage(message, p0, p1, p2, p3, p4);
        return new SafeReusableMessage((ReusableMessage) reusableMsg);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        Message reusableMsg = decoratingReusableMessageFactory.newMessage(message, p0, p1, p2, p3, p4, p5);
        return new SafeReusableMessage((ReusableMessage) reusableMsg);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        Message reusableMsg = decoratingReusableMessageFactory.newMessage(message, p0, p1, p2, p3, p4, p5, p6);
        return new SafeReusableMessage((ReusableMessage) reusableMsg);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        Message reusableMsg = decoratingReusableMessageFactory.newMessage(message, p0, p1, p2, p3, p4, p5, p6, p7);
        return new SafeReusableMessage((ReusableMessage) reusableMsg);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        Message reusableMsg = decoratingReusableMessageFactory.newMessage(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
        return new SafeReusableMessage((ReusableMessage) reusableMsg);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        Message reusableMsg = decoratingReusableMessageFactory.newMessage(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        return new SafeReusableMessage((ReusableMessage) reusableMsg);
    }

    @Override
    public Message newMessage(Object message) {
        Message reusableMsg = decoratingReusableMessageFactory.newMessage(message);
        return new SafeReusableMessage((ReusableMessage) reusableMsg);
    }

    @Override
    public Message newMessage(String message) {
        Message reusableMsg = decoratingReusableMessageFactory.newMessage(message);
        return new SafeReusableMessage((ReusableMessage) reusableMsg);
    }

    @Override
    public Message newMessage(String message, Object... params) {
        Message reusableMsg = decoratingReusableMessageFactory.newMessage(message, params);
        return new SafeReusableMessage((ReusableMessage) reusableMsg);
    }
}
