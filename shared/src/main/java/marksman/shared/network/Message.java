package marksman.shared.network;

import java.io.IOException;
import java.io.OutputStream;

public interface Message {
    String value(String name);

    Message with(String name, String value);

    void writeTo(OutputStream stream) throws IOException;
}
