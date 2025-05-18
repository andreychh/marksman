package marksman.server.domain.game;

public interface Target extends Geometry {
    void move();

    void changeDirection();

    Target withId(int id);
}
