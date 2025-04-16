package marksman.shared.network;

import java.io.OutputStream;

// todo: MessageHandler has -er suffix
public interface MessageHandler {
    void handleMessage(Message message, OutputStream stream);
}
