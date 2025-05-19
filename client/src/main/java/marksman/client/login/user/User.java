package marksman.client.login.user;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import marksman.client.login.events.UserJoinLobbyEvent;
import marksman.shared.network.connecting.StringSender;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class User {
    private final StringSender sender;
    private final StringProperty nameProperty;

    public User(final StringSender sender, final StringProperty nameProperty) {
        this.sender = sender;
        this.nameProperty = nameProperty;
    }

    public User(final StringSender sender, final String name) {
        this(sender, new SimpleStringProperty(name));
    }

    public void rename(final String name) {
        // todo: move validation to server side
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.nameProperty.set(name);
    }

    public void joinLobby() {
        new UserJoinLobbyEvent(this).sendTo(this.sender);
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public Element serialize() {
        Element userElement = DocumentHelper.createElement("user");
        userElement.addElement("name").addText(this.nameProperty.get());
        return userElement;
    }
}
