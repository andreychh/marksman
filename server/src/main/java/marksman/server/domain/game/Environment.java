package marksman.server.domain.game;

import marksman.server.domain.game.memory.MemoryField;

public final class Environment {
    private final Targets targets;
    private final MemoryField field;

    public Environment(final Targets targets, final MemoryField field) {
        this.targets = targets;
        this.field = field;
    }

    public void next() {
        this.move();
        this.checkCollisions();
    }

    private void move() {
        for (Target t : this.targets) {
            t.move();
        }
    }

    private void checkCollisions() {
        for (Target t : this.targets) {
            if (!this.field.contains(t)) {
                t.changeDirection();
            }
        }
    }
}
