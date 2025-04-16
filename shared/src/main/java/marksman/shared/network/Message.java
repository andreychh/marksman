package marksman.shared.network;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class Message {
    private final Map<String, String> args;

    public Message(final Map<String, String> args) {
        this.args = new HashMap<>(args);
    }

    public Message() {
        this(new HashMap<>());
    }

    // todo: value breaks encapsulation
    public String value(final String name) {
        String value = this.args.get(name);
        if (value == null) {
            throw new IllegalArgumentException(String.format("Key '%s' not found", name));
        }
        return value;
    }

    public Message with(final String name, final String value) {
        Map<String, String> args = new HashMap<>(this.args);
        args.put(name, value);
        return new Message(args);
    }

    public void writeTo(final OutputStream stream) throws IOException {
        stream.write(this.content().getBytes());
        stream.flush();
    }

    private String content() {
        return this.args
                .entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(";")) + "\n";
    }
}
