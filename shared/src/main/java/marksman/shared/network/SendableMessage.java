package marksman.shared.network;

public interface SendableMessage {
    SendableMessage with(String name, String value);
    String content();
}
