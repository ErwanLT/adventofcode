package aoc.day06;

import aoc.Day2024;
import aoc.location.Loc;

import java.util.HashSet;
import java.util.Set;

public class Day06 extends Day2024 {

    private final char[][] grid;

    public Day06() {
        super(6);
        this.grid = dayGrid();
    }

    public static void main(String[] args) {
        new Day06().printParts();
    }

    @Override
    public Object part1() {
        return countVisitedPositions(grid);
    }

    @Override
    public Object part2() {
        return countObstructionPositions(grid);
    }

    private static final Loc[] DIRECTIONS = {
            new Loc(0, -1), // UP
            new Loc(1, 0),  // RIGHT
            new Loc(0, 1),  // DOWN
            new Loc(-1, 0)  // LEFT
    };

    public static int countVisitedPositions(char[][] map) {
        GuardState guardState = findGuard(map);
        if (guardState == null) {
            throw new IllegalArgumentException("No guard found on the map.");
        }

        Set<Loc> visited = new HashSet<>();
        visited.add(guardState.position);

        while (true) {
            Loc nextPosition = guardState.position.move(DIRECTIONS[guardState.direction]);

            if (isOutOfBounds(nextPosition, map)) {
                break;
            }

            char nextCell = map[(int)nextPosition.y][(int)nextPosition.x];
            if (nextCell == '#') {
                guardState.turnRight();
            } else {
                guardState.move(nextPosition);
                visited.add(nextPosition);
            }
        }

        return visited.size();
    }

    public static int countObstructionPositions(char[][] map) {
        GuardState guardState = findGuard(map);
        if (guardState == null) {
            throw new IllegalArgumentException("No guard found on the map.");
        }

        int rows = map.length;
        int cols = map[0].length;
        int validObstructions = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (map[r][c] == '.' && !(r == guardState.position.y && c == guardState.position.x)) {
                    map[r][c] = '#';
                    if (createsLoop(map, guardState)) {
                        validObstructions++;
                    }
                    map[r][c] = '.';
                }
            }
        }

        return validObstructions;
    }

    private static boolean createsLoop(char[][] map, GuardState guardState) {
        Set<String> visited = new HashSet<>();
        Loc position = guardState.position;
        int direction = guardState.direction;

        while (true) {
            String key = position + "-" + direction;
            if (visited.contains(key)) {
                return true;
            }
            visited.add(key);

            Loc nextPosition = position.move(DIRECTIONS[direction]);
            if (isOutOfBounds(nextPosition, map)) {
                break;
            }

            char nextCell = map[(int)nextPosition.y][(int)nextPosition.x];
            if (nextCell == '#') {
                direction = (direction + 1) % 4;
            } else {
                position = nextPosition;
            }
        }

        return false;
    }

    private static GuardState findGuard(char[][] map) {
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {
                char cell = map[r][c];
                int direction = switch (cell) {
                    case '^' -> 0; // UP
                    case '>' -> 1; // RIGHT
                    case 'v' -> 2; // DOWN
                    case '<' -> 3; // LEFT
                    default -> -1;
                };
                if (direction != -1) {
                    return new GuardState(new Loc(c, r), direction);
                }
            }
        }
        return null;
    }

    private static boolean isOutOfBounds(Loc loc, char[][] map) {
        return loc.y < 0 || loc.y >= map.length || loc.x < 0 || loc.x >= map[0].length;
    }

    private static class GuardState {
        Loc position;
        int direction;

        GuardState(Loc position, int direction) {
            this.position = position;
            this.direction = direction;
        }

        void turnRight() {
            this.direction = (this.direction + 1) % 4;
        }

        void move(Loc newPosition) {
            this.position = newPosition;
        }
    }
}
