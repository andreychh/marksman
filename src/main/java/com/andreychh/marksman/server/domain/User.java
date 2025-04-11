package com.andreychh.marksman.server.domain;

import java.io.OutputStream;

public final class User {
    private final String name;
    private final OutputStream outputStream;

    public User(final String name, final OutputStream outputStream) {
        this.name = name;
        this.outputStream = outputStream;
    }

    public String name() {
        return name;
    }

    // todo: outputStream breaks encapsulation
    public OutputStream outputStream() {
        return outputStream;
    }
}
