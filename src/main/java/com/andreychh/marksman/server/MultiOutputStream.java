package com.andreychh.marksman.server;

import java.io.IOException;
import java.io.OutputStream;

public final class MultiOutputStream extends OutputStream {
    private final OutputStream[] streams;

    public MultiOutputStream(final OutputStream... streams) {
        this.streams = streams;
    }

    @Override
    public void write(final int b) throws IOException {
        for (OutputStream stream : streams) {
            stream.write(b);
        }
    }

    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        for (OutputStream stream : streams) {
            stream.write(b, off, len);
        }
    }

    @Override
    public void flush() throws IOException {
        for (OutputStream stream : streams) {
            stream.flush();
        }
    }

    @Override
    public void close() throws IOException {
        for (OutputStream stream : streams) {
            stream.close();
        }
    }
}
