package marksman.shared.geometry;

public final class Point {
    private final double x;
    private final double y;

    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public Point translate(final Point other) {
        return new Point(this.x + other.x(), this.y + other.y());
    }

    public Point negate() {
        return new Point(-this.x(), -this.y());
    }

    @Override
    public String toString() {
        return "%f~%f".formatted(this.x, this.y);
    }
}
