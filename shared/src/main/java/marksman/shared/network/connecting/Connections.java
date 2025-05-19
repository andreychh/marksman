package marksman.shared.network.connecting;

import java.util.ArrayList;
import java.util.List;

public final class Connections implements StringSender {
    private final List<StringSender> senders;

    public Connections(final List<StringSender> senders) {
        this.senders = senders;
    }

    public Connections() {
        this(new ArrayList<>());
    }

    public void add(final StringSender sender) {
        this.senders.add(sender);
    }

    public void remove(final StringSender sender) {
        this.senders.remove(sender);
    }

    @Override
    public void sendString(final String string) {
        this.senders.forEach(c -> c.sendString(string));
    }
}
