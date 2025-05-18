package marksman.server.domain.game;

public final class Identifiers {
    private int counter;

    public Identifiers(final int counter) {
        this.counter = counter;
    }

    public Identifiers() {
        this(0);
    }

    public int next() {
        return ++counter;
    }
}
