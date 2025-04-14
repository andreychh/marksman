package marksman.shared.network;

import java.io.OutputStream;

public interface MessageHandler {
    void handle(Message message, OutputStream stream);
}
