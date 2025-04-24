package marksman.server.domain;

import marksman.shared.network.Message;

public interface Sender {
    void send(Message message);
}
