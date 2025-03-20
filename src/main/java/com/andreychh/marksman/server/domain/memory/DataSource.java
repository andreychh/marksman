package com.andreychh.marksman.server.domain.memory;

import com.andreychh.marksman.server.Point;

import java.util.HashMap;
import java.util.Map;

class TargetData {
    public int id;
    public Point center;
    public double radius;
    public Point direction;

    public TargetData(int id, Point center, double radius, Point direction) {
        this.id = id;
        this.center = center;
        this.radius = radius;
        this.direction = direction;
    }
}

class FieldData {
    public int id;
    public Point center;
    public Point size;

    public FieldData(int id, Point center, Point size) {
        this.id = id;
        this.center = center;
        this.size = size;
    }
}

public class DataSource {
    private int id;
    private final Map<Integer, TargetData> targetData;
    private final Map<Integer, FieldData> fieldData;

    public DataSource() {
        this.id = 0;
        this.targetData = new HashMap<>();
        this.fieldData = new HashMap<>();
    }

    public Map<Integer, TargetData> targetData() {
        return this.targetData;
    }

    public int addTarget(Point center, double radius, Point direction) {
        this.id += 1;
        this.targetData.put(id, new TargetData(id, center, radius, direction));
        return id;
    }

    public Map<Integer, FieldData> fieldData() {
        return this.fieldData;
    }

    public int addField(Point center, Point size) {
        this.id += 1;
        this.fieldData.put(id, new FieldData(id, center, size));
        return id;
    }
}
