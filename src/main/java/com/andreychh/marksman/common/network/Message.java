package com.andreychh.marksman.common.network;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public final class Message {
    private final Map<String, String> args;

    public Message() {
        this.args = new HashMap<>();
    }

    public Message with(final String name, final String value) {
        args.put(name, value);
        return this;
    }

    // todo: value breaks encapsulation
    public String value(final String name) {
        String value = args.get(name);
        if (value == null) {
            throw new IllegalArgumentException(String.format("Key '%s' not found", name));
        }
        return value;
    }

    public void writeTo(final OutputStream stream) throws IOException {
        stream.write(this.content().getBytes());
    }

    private String content() {
        return this.args.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(";")) + "\n";
    }
}
