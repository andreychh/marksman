package marksman.shared.network.messaging;

import marksman.shared.network.connecting.StringSender;

public interface MessageReceiver {
    void receiveMessage(ReceivedMessage message, StringSender sender);
}
