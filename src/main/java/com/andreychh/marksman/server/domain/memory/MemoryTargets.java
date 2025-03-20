package com.andreychh.marksman.server.domain.memory;

import com.andreychh.marksman.server.Point;
import com.andreychh.marksman.server.domain.Targets;

import java.util.List;

public class MemoryTargets implements Targets {
    private final DataSource dataSource;

    public MemoryTargets(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<MemoryTarget> targets() {
        return this.dataSource.targetData()
                .values()
                .stream()
                .map(targetData -> new MemoryTarget(targetData.id, dataSource))
                .toList();
    }

    @Override
    public MemoryTarget add(Point center, double radius, Point direction) {
        int id = this.dataSource.addTarget(center, radius, direction);
        return new MemoryTarget(id, dataSource);
    }
}
