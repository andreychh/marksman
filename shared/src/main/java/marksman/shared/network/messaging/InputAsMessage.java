package marksman.shared.network.messaging;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

public final class InputAsMessage implements ReceivedMessage {
    private final String input;

    public InputAsMessage(final String input) {
        this.input = input;
    }

    @Override
    public String value(final String key) {
        try {
            System.out.println("Parsing input '" + this.input + "' with key '" + key + "'");
            Node node = DocumentHelper.parseText(this.input).selectSingleNode(key);
            if (node == null) {
                throw new IllegalArgumentException("Key '%s' not found".formatted(key));
            }
            String value;
            if (node.getNodeType() == Node.ELEMENT_NODE && !node.getText().isEmpty()) {
                value = node.getText();
            } else {
                value = node.asXML();
            }
            System.out.println("Parsed value: " + value);
            return value;
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
