package aoc.day17.shapes;

import java.awt.*;
import java.util.List;

public class Shape1 extends Shape{
    List<Point> deltas = List.of(
            new Point(0,0),
            new Point(1, 0),
            new Point(2, 0),
            new Point(3, 0)
    );

    public Shape1(Point bottomLeft) {
        for (var delta: deltas) {
            points.add(new Point(bottomLeft.x + delta.x, bottomLeft.y + delta.y));
        }
    }
}
