package marksman.shared.network;

// todo: MessageHandler has -er suffix
public interface MessageHandler {
    void handleMessage(Message message, Connection connection);
}
