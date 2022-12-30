package aoc.day09;

import aoc.DayOld;
import aoc.Direction;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static aoc.parser.ReadFormatedString.readString;

public class Day09 implements DayOld {
    @Override
    public String part1(List<String> input) {
        return String.valueOf(simulateRope(1, input));
    }

    private int simulateRope(int size, List<String> input) {
        List<Move> moves = input.stream().map(s -> readString(s, "%c %n", Move.class)).toList();
        Point head = new Point();
        List<Point> tail = IntStream.range(0, size).mapToObj(i -> new Point()).collect(Collectors.toList());
        HashSet<Point> visited = new HashSet<>();
        visited.add(head);

        for(Move m : moves) {
            Direction dir = Direction.getByDirCode(m.getDir());
            for(int i = 0; i< m.getN(); i++) {
                head = dir.move(head);
                for(int j = 0; j<tail.size(); j++) {
                    Point t = j == 0 ? head : tail.get(j - 1);
                    tail.set(j, moveRope(t, tail.get(j)));
                    if(j == tail.size()-1) visited.add(tail.get(j));
                }
            }
        }
        return visited.size();
    }

    private static Point moveRope(Point head, Point tail) {
        if (Arrays.stream(Direction.values()).noneMatch(d -> d.move(tail).equals(head))) {
            if (head.x > tail.x && head.y == tail.y) {
                return Direction.EAST.move(tail);
            } else if (head.x < tail.x && head.y == tail.y) {
                return Direction.WEST.move(tail);
            } else if (head.x == tail.x && head.y > tail.y) {
                return Direction.SOUTH.move(tail);
            } else if (head.x == tail.x && head.y < tail.y) {
                return Direction.NORTH.move(tail);
            } else if (head.x > tail.x && head.y > tail.y) {
                return Direction.SOUTHEAST.move(tail);
            } else if (head.x < tail.x && head.y < tail.y) {
                return Direction.NORTHWEST.move(tail);
            } else if (head.x < tail.x && head.y > tail.y) {
                return Direction.SOUTHWEST.move(tail);
            } else if (head.x > tail.x && head.y < tail.y) {
                return Direction.NORTHEAST.move(tail);
            } else throw new IllegalStateException();
        }
        return tail;
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(simulateRope(9, input));
    }
}
