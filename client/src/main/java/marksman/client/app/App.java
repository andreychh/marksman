package marksman.client.app;

import javafx.beans.property.StringProperty;

public final class App {

    private final StringProperty screenProperty;

    public App(final StringProperty screenProperty) {
        this.screenProperty = screenProperty;
    }

    public StringProperty screenProperty() {
        return screenProperty;
    }

    public void changeScreen(final String screen) {
        this.screenProperty.set(screen);
    }
}
