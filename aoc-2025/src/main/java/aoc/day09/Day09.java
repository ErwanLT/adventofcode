package aoc.day09;

import aoc.Day2025;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day09 extends Day2025 {

    public Day09() {
        super(9, "Movie Theater");
    }

    private record Point(long x, long y) {
    }

    private Set<Point> parsePointsSet() {
        return dayStream()
                .map(line -> {
                    String[] parts = line.split(",");
                    return new Point(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
                })
                .collect(Collectors.toSet());
    }

    private List<Point> parsePointsList() {
        return dayStream()
                .map(line -> {
                    String[] parts = line.split(",");
                    return new Point(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
                })
                .collect(Collectors.toList());
    }

    @Override
    public Object part1() {
        Set<Point> points = parsePointsSet();
        List<Point> pointList = List.copyOf(points);
        long maxArea = 0;

        for (int i = 0; i < pointList.size(); i++) {
            for (int j = i + 1; j < pointList.size(); j++) {
                Point p1 = pointList.get(i);
                Point p2 = pointList.get(j);
                long area = (Math.abs(p1.x() - p2.x()) + 1) * (Math.abs(p1.y() - p2.y()) + 1);
                if (area > maxArea)
                    maxArea = area;
            }
        }
        return maxArea;
    }

    @Override
    public Object part2() {
        List<Point> polygon = parsePointsList();
        if (polygon.size() < 4)
            return 0;

        long maxArea = 0;

        int n = polygon.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Point p1 = polygon.get(i);
                Point p2 = polygon.get(j);

                // Candidates must form a rectangle with non-zero area?
                // Problem asks for "largest area". Area = (w+1)*(h+1)?
                // "area of only 6 between 7,3 and 2,3" -> (7-2+1=6) * (3-3+1=1) = 6.
                // "Area 24 between 2,5 and 9,7" -> (9-2+1=8) * (7-5+1=3) = 24.
                // Formula: (abs(dx)+1) * (abs(dy)+1).
                long width = Math.abs(p1.x() - p2.x()) + 1;
                long height = Math.abs(p1.y() - p2.y()) + 1;
                long area = width * height;

                if (area <= maxArea)
                    continue;

                if (isValidRectangle(p1, p2, polygon)) {
                    maxArea = area;
                }
            }
        }
        return maxArea;
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

            // Edge is either horizontal or vertical
            if (a.x() == b.x()) { // Vertical Edge at x = a.x
                long vy1 = Math.min(a.y(), b.y());
                long vy2 = Math.max(a.y(), b.y());
                // Check intersection with Rectangle X range (strictly inside)
                if (a.x() > minX && a.x() < maxX) {
                    // Check Y overlap strictly
                    if (Math.max(vy1, minY) < Math.min(vy2, maxY)) {
                        return false;
                    }
                }
            } else { // Horizontal Edge at y = a.y
                long vx1 = Math.min(a.x(), b.x());
                long vx2 = Math.max(a.x(), b.x());
                // Check intersection with Rectangle Y range (strictly inside)
                if (a.y() > minY && a.y() < maxY) {
                    // Check X overlap strictly
                    if (Math.max(vx1, minX) < Math.min(vx2, maxX)) {
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

            // Ray casting algorithm
            boolean intersect = ((pi.y() > y) != (pj.y() > y)) &&
                    (x < (pj.x() - pi.x()) * (y - pi.y()) / (pj.y() - pi.y()) + pi.x());
            if (intersect) {
                inside = !inside;
            }
        }
        return inside;
    }
}
