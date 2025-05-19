package marksman.shared.network.connecting;

public final class LoggedStringSender implements StringSender {
    private final StringSender origin;

    public LoggedStringSender(final StringSender origin) {
        this.origin = origin;
    }

    @Override
    public void sendString(final String string) {
        this.origin.sendString(string);
        System.out.println("Sent string: " + string);
    }
}
