package marksman.shared.geometry;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

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

    public Element serialize() {
        Element element = DocumentHelper.createElement("point");
        element.addElement("x").addText(String.valueOf(x));
        element.addElement("y").addText(String.valueOf(y));
        return element;
    }
}
