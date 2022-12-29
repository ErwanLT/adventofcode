package aoc.day17.shapes;

import java.awt.*;
import java.util.List;

public class Shape4 extends Shape{
    List<Point> deltas = List.of(
            new Point(0,0),
            new Point(0, 1),
            new Point(0, 2),
            new Point(0, 3)
    );

    public Shape4(Point bottomLeft) {
        for (var delta: deltas) {
            points.add(new Point(bottomLeft.x + delta.x, bottomLeft.y + delta.y));
        }
    }
}
