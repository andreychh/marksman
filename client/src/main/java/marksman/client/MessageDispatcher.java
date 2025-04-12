package marksman.client;

import marksman.shared.network.Message;
import marksman.shared.network.MessageHandler;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// todo: MessageDispatcher has -er suffix
public final class MessageDispatcher implements MessageHandler {
    private final Map<String, List<MessageHandler>> handlers = new HashMap<>();

    public void addHandler(final String action, final MessageHandler handler) {
        if (!handlers.containsKey(action)) {
            handlers.put(action, new ArrayList<>());
        }
        handlers.get(action).add(handler);
    }

    public void removeHandler(final String action, final MessageHandler handler) {
        if (!handlers.containsKey(action)) {
            return;
        }
        handlers.get(action).remove(handler);
    }

    @Override
    public void handle(final Message message, final OutputStream outputStream) {
        String action = message.value("action");
        if (!handlers.containsKey(action)) {
            return;
        }
        handlers.get(action).forEach(h -> h.handle(message, outputStream));
    }
}
