package marksman.server.domain.memory;

import marksman.shared.geometry.Point;
import marksman.shared.geometry.Size;

/**
 * MemoryFields is typically redundant as only a single game field is usually
 * needed. However, it is kept for consistency when creating objects for the
 * Environment.
 */
public final class MemoryFields {
    private final DataSource dataSource;

    public MemoryFields(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public MemoryField add(final Point center, final Size size) {
        int id = this.dataSource.addField(center, size);
        return new MemoryField(id, dataSource);
    }
}
