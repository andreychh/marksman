package com.andreychh.marksman.server.domain.transmittable;

import com.andreychh.marksman.server.Point;
import com.andreychh.marksman.server.domain.Targets;

import java.io.OutputStream;
import java.util.List;

public class TransmittableTargets implements Targets {
    private final Targets origin;
    private final OutputStream outputStream;

    public TransmittableTargets(Targets origin, OutputStream outputStream) {
        this.origin = origin;
        this.outputStream = outputStream;
    }

    @Override
    public List<TransmittableTarget> targets() {
        return origin.targets()
                .stream()
                .map(t -> new TransmittableTarget(t, this.outputStream))
                .toList();
    }

    @Override
    public TransmittableTarget add(Point center, double radius, Point direction) {
        return new TransmittableTarget(this.origin.add(center, radius, direction), this.outputStream);
    }
}
