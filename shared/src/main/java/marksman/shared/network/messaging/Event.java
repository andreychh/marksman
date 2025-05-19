package marksman.shared.network.messaging;

import marksman.shared.network.connecting.StringSender;

public interface Event {
    void sendTo(StringSender sender);
}
