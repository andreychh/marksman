package marksman.shared.network;

// todo: MessageReceiver has -er suffix
public interface MessageReceiver {
    void receiveMessage(ReceivedMessage message, Connection connection);
}
