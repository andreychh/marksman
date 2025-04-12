package marksman.server.domain;

import marksman.server.domain.memory.MemoryField;

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
        for (Target t : this.targets.targets()) {
            t.move();
        }
    }

    private void checkCollisions() {
        for (Target t : this.targets.targets()) {
            if (!this.field.contains(t)) {
                t.changeDirection();
            }
        }
    }
}
