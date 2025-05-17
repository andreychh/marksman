package marksman.server.domain.game;

public interface Targets extends Iterable<Target> {
    Target add(Target target);
}
