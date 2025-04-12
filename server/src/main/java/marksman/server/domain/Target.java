package marksman.server.domain;

public interface Target extends Geometry {
    void move();

    void changeDirection();
}
