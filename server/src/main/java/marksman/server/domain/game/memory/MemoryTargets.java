package marksman.server.domain.game.memory;

import marksman.server.domain.game.Identifiers;
import marksman.server.domain.game.Target;
import marksman.server.domain.game.Targets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class MemoryTargets implements Targets {
    private final List<Target> targets;
    private final Identifiers identifiers;

    public MemoryTargets(final List<Target> targets, final Identifiers identifiers) {
        this.targets = targets;
        this.identifiers = identifiers;
    }

    public MemoryTargets(final Identifiers identifiers) {
        this(new ArrayList<>(), identifiers);
    }

    @Override
    public Target add(final Target target) {
        Target added = target.withId(this.identifiers.next());
        this.targets.add(added);
        return added;
    }

    @Override
    public Iterator<Target> iterator() {
        return this.targets.iterator();
    }
}
