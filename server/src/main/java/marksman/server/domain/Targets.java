package marksman.server.domain;

import marksman.shared.geometry.Point;

import java.util.List;

public interface Targets {
    List<? extends Target> targets();

    Target add(Point center, double radius, Point direction);
}
