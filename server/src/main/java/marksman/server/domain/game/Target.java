package marksman.server.domain.game;

import marksman.shared.geometry.Point;

public interface Target extends Geometry {
    void move();

    void changeDirection();

    Point center();

    int id();

    Target withId(int id);
}
