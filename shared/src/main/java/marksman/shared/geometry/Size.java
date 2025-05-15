package marksman.shared.geometry;

public final class Size {
    private final double width;
    private final double height;

    public Size(final double width, final double height) {
        this.width = width;
        this.height = height;
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }
}
