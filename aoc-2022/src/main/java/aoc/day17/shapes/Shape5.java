package aoc.day17.shapes;

import java.awt.*;
import java.util.List;

public class Shape5 extends Shape{
    List<Point> deltas = List.of(
            new Point(0,0),
            new Point(1, 0),
            new Point(0, 1),
            new Point(1, 1)
    );

    public Shape5(Point bottomLeft) {
        for (var delta: deltas) {
            points.add(new Point(bottomLeft.x + delta.x, bottomLeft.y + delta.y));
        }
    }
}
