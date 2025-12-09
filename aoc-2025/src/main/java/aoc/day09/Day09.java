package aoc.day09;

import aoc.Day2025;

import java.util.List;
import java.util.stream.Collectors;

public class Day09 extends Day2025 {

    private final List<Point> polygon;

    public Day09() {
        super(9, "Movie Theater");
        this.polygon = dayStream()
                .map(line -> {
                    String[] parts = line.split(",");
                    return new Point(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
                })
                .collect(Collectors.toList());
    }

    private record Point(long x, long y) {
    }

    @Override
    public Object part1() {
        long maxArea = 0;
        for (int i = 0; i < polygon.size(); i++) {
            for (int j = i + 1; j < polygon.size(); j++) {
                Point p1 = polygon.get(i);
                Point p2 = polygon.get(j);
                long area = calculateArea(p1, p2);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }
        return maxArea;
    }

    @Override
    public Object part2() {
        if (polygon.size() < 4) {
            return 0;
        }

        long maxArea = 0;
        int n = polygon.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Point p1 = polygon.get(i);
                Point p2 = polygon.get(j);

                long area = calculateArea(p1, p2);

                if (area <= maxArea) {
                    continue;
                }

                if (isValidRectangle(p1, p2, polygon)) {
                    maxArea = area;
                }
            }
        }
        return maxArea;
    }

    private long calculateArea(Point p1, Point p2) {
        return (Math.abs(p1.x() - p2.x()) + 1) * (Math.abs(p1.y() - p2.y()) + 1);
    }

    private boolean isValidRectangle(Point p1, Point p2, List<Point> polygon) {
        long minX = Math.min(p1.x(), p2.x());
        long maxX = Math.max(p1.x(), p2.x());
        long minY = Math.min(p1.y(), p2.y());
        long maxY = Math.max(p1.y(), p2.y());

        // 1. Check if any polygon vertex is strictly inside the rectangle
        for (Point v : polygon) {
            if (v.x() > minX && v.x() < maxX && v.y() > minY && v.y() < maxY) {
                return false;
            }
        }

        // 2. Check if any polygon edge intersects the rectangle interior
        int n = polygon.size();
        for (int i = 0; i < n; i++) {
            Point a = polygon.get(i);
            Point b = polygon.get((i + 1) % n);

            if (a.x() == b.x()) { // Vertical Edge
                if (a.x() > minX && a.x() < maxX) {
                    if (Math.max(Math.min(a.y(), b.y()), minY) < Math.min(Math.max(a.y(), b.y()), maxY)) {
                        return false;
                    }
                }
            } else { // Horizontal Edge
                if (a.y() > minY && a.y() < maxY) {
                    if (Math.max(Math.min(a.x(), b.x()), minX) < Math.min(Math.max(a.x(), b.x()), maxX)) {
                        return false;
                    }
                }
            }
        }

        // 3. Check if center of rectangle is inside the polygon
        double midX = (minX + maxX) / 2.0;
        double midY = (minY + maxY) / 2.0;
        return isPointInPolygon(midX, midY, polygon);
    }

    private boolean isPointInPolygon(double x, double y, List<Point> polygon) {
        boolean inside = false;
        int n = polygon.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            Point pi = polygon.get(i);
            Point pj = polygon.get(j);

            boolean intersect = ((pi.y() > y) != (pj.y() > y)) &&
                    (x < (pj.x() - pi.x()) * (y - pi.y()) / (pj.y() - pi.y()) + pi.x());
            if (intersect) {
                inside = !inside;
            }
        }
        return inside;
    }
}
