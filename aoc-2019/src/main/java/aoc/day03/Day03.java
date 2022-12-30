package aoc.day03;

import aoc.DayOld;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day03 implements DayOld {

    private static List<Point> wires = new ArrayList<>();
    private static List<Point> crossings = new ArrayList<>();

    private static Point start = new Point(0, 0);

    @Override
    public String part1(List<String> input) {
        generatePath(input.get(0), false);
        generatePath(input.get(1), true);

        List<Integer> distances = crossings.stream().map(point -> distance(point, start)).collect(Collectors.toList());
        distances.sort(Comparator.comparingInt(a -> a));

        return String.valueOf(distances.get(0));
    }

    @Override
    public String part2(List<String> input) {
        return null;
    }

    private static void generatePath(String wire, boolean crossing) {
        Point current = start.clone();
        String[] parts = wire.split(",");
        for (String part : parts) {
            int amount = Integer.parseInt(part.substring(1));
            switch (part.charAt(0)) {
                case 'U': {
                    for (int i = 0; i < amount; i++) {
                        current.setY(current.getY() - 1);
                        if (!crossing) {
                            wires.add(current.clone());
                        } else {
                            if (isWireCrossing(current)) {
                                crossings.add(current.clone());
                            }
                        }
                    }
                    break;
                }
                case 'D': {
                    for (int i = 0; i < amount; i++) {
                        current.setY(current.getY() + 1);
                        if (!crossing) {
                            wires.add(current.clone());
                        } else {
                            if (isWireCrossing(current)) {
                                crossings.add(current.clone());
                            }
                        }
                    }
                    break;
                }
                case 'L': {
                    for (int i = 0; i < amount; i++) {
                        current.setX(current.getX() - 1);
                        if (!crossing) {
                            wires.add(current.clone());
                        } else {
                            if (isWireCrossing(current)) {
                                crossings.add(current.clone());
                            }
                        }
                    }
                    break;

                }
                case 'R': {
                    for (int i = 0; i < amount; i++) {
                        current.setX(current.getX() + 1);
                        if (!crossing) {
                            wires.add(current.clone());
                        } else {
                            if (isWireCrossing(current)) {
                                crossings.add(current.clone());
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    private static boolean isWireCrossing(Point p) {
        for (Point point : wires) {
            if (point.getX() == p.getX() && point.getY() == p.getY()) {
                return true;
            }
        }
        return false;
    }

    private static int distance(Point a, Point b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
