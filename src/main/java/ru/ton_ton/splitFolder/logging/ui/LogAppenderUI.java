package ru.ton_ton.splitFolder.logging.ui;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Plugin(name = "LogAppenderUI", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class LogAppenderUI extends AbstractAppender {

    private static List<Consumer<LogEvent>> listeners = new ArrayList<>();
    public LogAppenderUI(String name, Filter filter) {
        super(name, filter, null);
    }

    @PluginFactory
    public static LogAppenderUI createAppender() {
        return new LogAppenderUI("LogAppenderUI", null);
    }

    public static void addLogListener(Consumer<LogEvent> listener) {
        listeners.add(listener);
    }

    @Override
    public void append(LogEvent event) {
        listeners.forEach(consumer -> consumer.accept(event));
    }
}
