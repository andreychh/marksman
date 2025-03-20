package com.andreychh.marksman.server;

public record Point(int x, int y) {
    public Point translate(Point other) {
        return new Point(this.x + other.x(), this.y + other.y());
    }

    public Point negate() {
        return new Point(-this.x(), -this.y());
    }
}
