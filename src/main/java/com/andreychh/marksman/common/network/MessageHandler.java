package com.andreychh.marksman.common.network;

import java.io.OutputStream;

public interface MessageHandler {
    void handle(Message message, OutputStream outputStream);
}
