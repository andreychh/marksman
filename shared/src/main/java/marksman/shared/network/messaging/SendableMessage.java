package marksman.shared.network.messaging;

public interface SendableMessage {
    SendableMessage with(String name, String value);
    String content();
}
