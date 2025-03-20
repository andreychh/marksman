package com.andreychh.marksman.server.domain.memory;

import com.andreychh.marksman.server.Point;

public class MemoryFields {
    private final DataSource dataSource;

    public MemoryFields(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Iterable<MemoryField> fields() {
        return this.dataSource.fieldData()
                .values()
                .stream()
                .map(targetData -> new MemoryField(targetData.id, dataSource))
                .toList();
    }

    public MemoryField add(Point center, Point size) {
        int id = this.dataSource.addField(center, size);
        return new MemoryField(id, dataSource);
    }
}
