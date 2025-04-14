package marksman.shared.geometry;

public final class Size {
    private final int width;
    private final int height;

    public Size(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}
