package marksman.shared.network;

import java.io.OutputStream;

public interface MessageHandler {
    void handleMessage(Message message, OutputStream stream);
}
