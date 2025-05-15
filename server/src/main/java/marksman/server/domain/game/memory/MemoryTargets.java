package marksman.server.domain.game.memory;

import marksman.server.domain.game.Targets;
import marksman.shared.geometry.Point;

import java.util.List;

public final class MemoryTargets implements Targets {
    private final DataSource dataSource;

    public MemoryTargets(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<MemoryTarget> targets() {
        return this.dataSource.targetData()
                .values()
                .stream()
                .map(t -> new MemoryTarget(t.id, dataSource))
                .toList();
    }

    @Override
    public MemoryTarget add(final Point center, final double radius, final Point direction) {
        int id = this.dataSource.addTarget(center, radius, direction);
        return new MemoryTarget(id, dataSource);
    }
}
