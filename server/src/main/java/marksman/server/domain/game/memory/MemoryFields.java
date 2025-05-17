package marksman.server.domain.game.memory;

import marksman.server.domain.game.Identifiers;

/**
 * MemoryFields is typically redundant as only a single game field is usually
 * needed. However, it is kept for consistency when creating objects for the
 * Environment.
 */
public final class MemoryFields {
    private final Identifiers identifiers;

    public MemoryFields(final Identifiers identifiers) {
        this.identifiers = identifiers;
    }

    public MemoryField add(final MemoryField field) {
        return field.withID(this.identifiers.next());
    }
}
