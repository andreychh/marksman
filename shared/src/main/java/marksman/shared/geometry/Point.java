package marksman.shared.geometry;

public final class Point {
    private final int x;
    private final int y;

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public Point translate(final Point other) {
        return new Point(this.x + other.x(), this.y + other.y());
    }

    public Point negate() {
        return new Point(-this.x(), -this.y());
    }
}
