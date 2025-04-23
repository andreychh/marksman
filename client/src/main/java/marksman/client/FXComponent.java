package marksman.client;

import javafx.scene.Parent;

import java.io.IOException;

public interface FXComponent {
    Parent parent() throws IOException;
}
