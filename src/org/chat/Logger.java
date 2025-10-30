package org.chat;

public class Logger {
    private static final String LOG_MESSAGE_FORMAT = "[%s]: %s";
    private static final String ERROR_MESSAGE_FORMAT = "[%s] (line %d): %s";
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
                        e.getStackTrace()[0].getLineNumber(),
                        e.getMessage()));
    }

    private String formatMessage(String message) {
        return LOG_MESSAGE_FORMAT.formatted(this.topic, message);
    }
}
