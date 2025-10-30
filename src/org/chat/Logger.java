package org.chat;

public class Logger {
    public static final String LOG_MESSAGE_FORMAT = "[%s]: %s";
    public static final String ERROR_MESSAGE_FORMAT = "[%s] (ERROR): %s";
    private String topic = "LOG";

    public Logger(String topic) {
        this.topic = topic;
    }

    public void log(String message) {
        if (!App.SHOW_LOGS) {
            return;
        }
        System.out.println(formatMessage(message));
    }

    public void log(String format, Object... args) {
        this.log(format.formatted(args));
    }

    public void logError(Exception e) {
        System.err.println(
                ERROR_MESSAGE_FORMAT.formatted(
                        this.topic,
                        e.getClass(),
                        e.getMessage()));
    }

    private String formatMessage(String message) {
        return LOG_MESSAGE_FORMAT.formatted(this.topic, message);
    }
}
