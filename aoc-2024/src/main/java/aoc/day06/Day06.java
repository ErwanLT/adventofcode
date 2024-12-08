package aoc.day06;

import aoc.Day2024;
import aoc.location.Loc;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Day06 extends Day2024 {

    private final char[][] grid;

    public Day06() {
        super(6, "Guard Gallivant");
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

    public int countVisitedPositions(char[][] map) {
        GuardState guardState = findGuard(map);
        if (guardState == null) {
            printer.printError("No guard found on the map.");
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

    public int countObstructionPositions(char[][] map) {
        GuardState guardState = findGuard(map);
        if (guardState == null) {
            printer.printError("No guard found on the map.");
            throw new IllegalArgumentException("No guard found on the map.");
        }

        // Find reachable cells
        Set<Loc> reachableCells = findReachableCells(map, guardState);

        // Multi-threaded computation
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Integer>> futures = new ArrayList<>();
        for (Loc cell : reachableCells) {
            futures.add(executor.submit(() -> {
                if (createsLoopWithObstruction(map, guardState, cell)) {
                    return 1;
                }
                return 0;
            }));
        }

        executor.shutdown();

        return futures.stream()
                .mapToInt(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
    }

    // Helper method to find reachable cells using BFS
    private Set<Loc> findReachableCells(char[][] map, GuardState guardState) {
        Set<Loc> reachable = new HashSet<>();
        Queue<Loc> queue = new LinkedList<>();
        queue.add(guardState.position);

        while (!queue.isEmpty()) {
            Loc current = queue.poll();
            if (reachable.contains(current) || isOutOfBounds(current, map)) {
                continue;
            }

            char cell = map[(int) current.y][(int) current.x];
            if (cell == '#') {
                continue; // Skip walls
            }

            reachable.add(current);
            for (Loc direction : DIRECTIONS) {
                queue.add(current.move(direction));
            }
        }

        return reachable;
    }

    // Updated loop check method remains similar, but caching can be added if necessary
    private static boolean createsLoopWithObstruction(char[][] map, GuardState guardState, Loc obstruction) {
        Set<String> visited = new HashSet<>();
        Loc position = guardState.position;
        int direction = guardState.direction;

        while (true) {
            String key = position + "-" + direction;
            if (visited.contains(key)) {
                return true; // Loop detected
            }
            visited.add(key);

            Loc nextPosition = position.move(DIRECTIONS[direction]);

            // Check if the next position is the simulated obstruction
            if (nextPosition.equals(obstruction)) {
                direction = (direction + 1) % 4; // Turn right at obstruction
                continue;
            }

            // Stop if out of bounds
            if (isOutOfBounds(nextPosition, map)) {
                break;
            }

            // Navigate based on the next cell
            char nextCell = map[(int) nextPosition.y][(int) nextPosition.x];
            if (nextCell == '#') {
                direction = (direction + 1) % 4; // Turn right at wall
            } else {
                position = nextPosition; // Move forward
            }
        }

        return false; // No loop detected
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
