package aoc.day17.shapes;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public abstract class Shape {
    List<Point> points = new LinkedList<>();

    public java.util.List<Point> getPoints() {
        return this.points;
    }

    public void moveLeft(HashSet<Point> grid) {
        for (Point point: this.points) {
            if (point.x - 1 == 0 || grid.contains(new Point(point.x - 1, point.y))) {
                return;
            }
        }

        for (Point point: this.points) {
            point.x--;
        }
    }

    public void moveRight(HashSet<Point> grid) {
        for (Point point: this.points) {
            if (point.x + 1 == 8 || grid.contains(new Point(point.x + 1, point.y))) {
                return;
            }
        }

        for (Point point: this.points) {
            point.x++;
        }
    }

    public boolean moveDown(HashSet<Point> grid) {
        for (Point point: this.points) {
            if (point.y - 1 == 0 || grid.contains(new Point(point.x, point.y - 1))) {
                return false;
            }
        }

        for (Point point: this.points) {
            point.y--;
        }

        return true;
    }
}
