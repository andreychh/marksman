package com.andreychh.marksman.server;

import com.andreychh.marksman.server.domain.Target;
import com.andreychh.marksman.server.domain.Targets;
import com.andreychh.marksman.server.domain.memory.MemoryField;

public class Environment {
    private final Targets targets;
    private final MemoryField memoryField;

    public Environment(Targets targets, MemoryField memoryField) {
        this.targets = targets;
        this.memoryField = memoryField;
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
            if (!this.memoryField.contains(t)) {
                t.onFieldEscape();
            }
        }
    }
}
