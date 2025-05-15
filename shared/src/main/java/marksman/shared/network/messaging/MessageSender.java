package marksman.shared.network.messaging;

// todo: MessageSender has -er suffix
public interface MessageSender {
    void sendMessage(SendableMessage message);
}
