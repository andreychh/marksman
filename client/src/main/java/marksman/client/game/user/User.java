package marksman.client.game.user;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import marksman.shared.network.connecting.StringSender;

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

    public void fire() {
//        this.sender.sendMessage(new Message()
//                .with("action", "user.fire")
//                .with("user.name", this.nameProperty.get()));
    }

    public void pauseGame() {
//        this.sender.sendMessage(new Message()
//                .with("action", "user.pauseGame")
//                .with("user.name", this.nameProperty.get()));
    }
}
