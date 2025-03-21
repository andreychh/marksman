package com.andreychh.marksman.server.domain.transmittable;

import com.andreychh.marksman.server.domain.Target;
import org.locationtech.jts.geom.Polygon;

import java.io.OutputStream;

public class TransmittableTarget implements Target {
    private final Target origin;
    private final OutputStream outputStream;

    public TransmittableTarget(Target origin, OutputStream outputStream) {
        this.origin = origin;
        this.outputStream = outputStream;
    }

    @Override
    public void move() {
        this.origin.move();
        this.update();
    }

    @Override
    public void onFieldEscape() {
        this.origin.onFieldEscape();
    }

    @Override
    public Polygon polygon() {
        return this.origin.polygon();
    }

    private void update() {
    }
}
