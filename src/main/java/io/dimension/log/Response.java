package io.dimension.log;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Response {
    private final static Response instance = new Response();

    private Consumer<String> info;

    private BiConsumer<String, Throwable> infoError;

    public static Response getInstance() {
        return instance;
    }

    public static void init(Consumer<String> log, BiConsumer<String, Throwable> infoError) {
        instance.info = log;
        instance.infoError = infoError;
    }

    public void info(String msg) {
        info.accept(msg);
    }

    public void info(String msg, Throwable t) {
        infoError.accept(msg, t);
    }
}
