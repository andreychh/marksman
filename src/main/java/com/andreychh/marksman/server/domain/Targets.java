package com.andreychh.marksman.server.domain;

import com.andreychh.marksman.common.geometry.Point;

import java.util.List;

public interface Targets {
    List<? extends Target> targets();

    Target add(Point center, double radius, Point direction);
}
