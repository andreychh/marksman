package com.andreychh.marksman.server.domain.memory;

import com.andreychh.marksman.common.geometry.Point;

/**
 * This is redundant as a single game field is usually used. It is utilized for
 * consistency when creating objects for Environment.
 */
public final class MemoryFields {
    private final DataSource dataSource;

    public MemoryFields(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public MemoryField add(final Point center, final Point size) {
        int id = this.dataSource.addField(center, size);
        return new MemoryField(id, dataSource);
    }
}
