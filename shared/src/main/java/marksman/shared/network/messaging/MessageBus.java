package marksman.shared.network.messaging;

import marksman.shared.network.connecting.StringSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MessageBus implements MessageReceiver {
    private final Map<String, List<MessageReceiver>> handlers = new HashMap<>();

    public void addHandler(final String action, final MessageReceiver handler) {
        if (!handlers.containsKey(action)) {
            handlers.put(action, new ArrayList<>());
        }
        handlers.get(action).add(handler);
    }

    public void removeHandler(final String action, final MessageReceiver handler) {
        if (!handlers.containsKey(action)) {
            return;
        }
        handlers.get(action).remove(handler);
    }

    @Override
    public void receiveMessage(final ReceivedMessage message, final StringSender sender) {
        String action = message.value("event/action");
        if (!handlers.containsKey(action)) {
            return;
        }
        handlers.get(action).forEach(h -> h.receiveMessage(message, sender));
    }
}
