package com.andreychh.marksman.server.domain.memory;

import com.andreychh.marksman.common.geometry.Point;

import java.util.HashMap;
import java.util.Map;

class TargetData {
    int id;
    Point center;
    double radius;
    Point direction;

    TargetData(final int id, final Point center, final double radius, final Point direction) {
        this.id = id;
        this.center = center;
        this.radius = radius;
        this.direction = direction;
    }
}

class FieldData {
    int id;
    Point center;
    Point size;

    FieldData(final int id, final Point center, final Point size) {
        this.id = id;
        this.center = center;
        this.size = size;
    }
}

public final class DataSource {
    private final Map<Integer, TargetData> targetData;
    private final Map<Integer, FieldData> fieldData;
    private int id;

    public DataSource() {
        this.id = 0;
        this.targetData = new HashMap<>();
        this.fieldData = new HashMap<>();
    }

    Map<Integer, TargetData> targetData() {
        return this.targetData;
    }

    int addTarget(final Point center, final double radius, final Point direction) {
        this.id += 1;
        this.targetData.put(id, new TargetData(id, center, radius, direction));
        return id;
    }

    Map<Integer, FieldData> fieldData() {
        return this.fieldData;
    }

    int addField(final Point center, final Point size) {
        this.id += 1;
        this.fieldData.put(id, new FieldData(id, center, size));
        return id;
    }
}
