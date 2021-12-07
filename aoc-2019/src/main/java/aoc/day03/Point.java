package aoc.day03;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Point {

    private int x;
    private int y;

    public Point clone() {
        return new Point(x, y);
    }
}
