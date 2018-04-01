package io.kitty.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.impl.DefaultLogEventFactory;
import org.apache.logging.log4j.message.Message;

import java.util.List;
import java.util.Objects;

public class SafeLogEventFactory extends DefaultLogEventFactory {

    @Override
    public LogEvent createEvent(String loggerName, Marker marker, String fqcn, Level level, Message data, List<Property> properties, Throwable t) {
        if (Objects.nonNull(t)) {
            t = new ExceptionProxy(ExceptionProxy.desensitizeClassNameAndMessage(t), t);
        }
        return super.createEvent(loggerName, marker, fqcn, level, data, properties, t);
    }
}
