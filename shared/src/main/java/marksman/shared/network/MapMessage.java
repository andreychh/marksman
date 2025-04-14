package marksman.shared.network;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public final class MapMessage implements Message {
    private final Map<String, String> args;

    public MapMessage(final Map<String, String> args) {
        this.args = new HashMap<>(args);
    }

    public MapMessage() {
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

    public MapMessage with(final String name, final String value) {
        Map<String, String> args = new HashMap<>(this.args);
        args.put(name, value);
        return new MapMessage(args);
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
