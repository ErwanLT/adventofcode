package aoc.day12;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class Shape {
    private final Set<Point> originalPoints;
    @Getter
    private final List<Set<Point>> transformations;
    @Getter
    private final int size;

    Shape(Set<Point> points) {
        this.originalPoints = normalize(points);
        this.size = originalPoints.size();
        this.transformations = generateTransformations();
    }

    private Set<Point> normalize(Set<Point> points) {
        if (points.isEmpty()) {
            return Collections.emptySet();
        }
        int minR = points.stream().mapToInt(Point::r).min().getAsInt();
        int minC = points.stream().mapToInt(Point::c).min().getAsInt();
        return points.stream()
                .map(p -> new Point(p.r() - minR, p.c() - minC))
                .collect(Collectors.toSet());
    }

    private List<Set<Point>> generateTransformations() {
        Set<Set<Point>> uniqueTransformations = new HashSet<>();
        Set<Point> current = new HashSet<>(originalPoints);

        for (int i = 0; i < 4; i++) {
            uniqueTransformations.add(normalize(current));
            uniqueTransformations.add(normalize(flip(current)));
            current = rotate(current);
        }

        return new ArrayList<>(uniqueTransformations);
    }

    private Set<Point> rotate(Set<Point> points) {
        return points.stream()
                .map(p -> new Point(p.c(), -p.r()))
                .collect(Collectors.toSet());
    }

    private Set<Point> flip(Set<Point> points) {
        return points.stream()
                .map(p -> new Point(p.r(), -p.c()))
                .collect(Collectors.toSet());
    }

}