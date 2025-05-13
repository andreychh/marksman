package marksman.server.domain;

import marksman.shared.geometry.Point;

public interface Target extends Geometry {
    void move();

    void changeDirection();

    Point center();

    int id();
}
