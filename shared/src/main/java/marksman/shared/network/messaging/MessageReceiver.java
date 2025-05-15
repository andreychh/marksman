package marksman.shared.network.messaging;

import marksman.shared.network.connecting.Connection;

// todo: MessageReceiver has -er suffix
public interface MessageReceiver {
    void receiveMessage(ReceivedMessage message, Connection connection);
}
