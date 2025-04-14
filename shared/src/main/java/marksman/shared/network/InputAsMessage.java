package marksman.shared.network;

import java.util.InputMismatchException;

public final class InputAsMessage {
    private final String input;

    public InputAsMessage(final String input) {
        this.input = input;
    }

    public Message message() {
        MapMessage message = new MapMessage();
        String[] pairs = this.input.split(";");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length != 2) {
                throw new InputMismatchException(String.format("Invalid pair format: '%s'", pair));
            }
            message = message.with(keyValue[0], keyValue[1]);
        }
        return message;
    }
}
