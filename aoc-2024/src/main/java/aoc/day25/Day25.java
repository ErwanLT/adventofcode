package aoc.day25;

import aoc.Day2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day25 extends Day2024 {
    public Day25() {
        super(25, "Code Chronicle");
    }

    public static void main(String[] args) {
        new Day25().printParts();
    }

    @Override
    public Object part1() {
        List<char[][]> locks = new ArrayList<>();
        List<char[][]> keys = new ArrayList<>();

        // Parse input into locks and keys
        String[] schematics = day().split("\n\n");
        for (String schematic : schematics) {
            String[] lines = schematic.split("\n");
            char[][] grid = new char[lines.length][lines[0].length()];

            for (int i = 0; i < lines.length; i++) {
                grid[i] = lines[i].toCharArray();
            }

            if (lines[0].contains("#")) {
                locks.add(grid);
            } else {
                keys.add(grid);
            }
        }

        // Check compatibility and print grids
        int compatiblePairs = 0;

        for (char[][] lock : locks) {
            for (char[][] key : keys) {
                char[][] resultGrid = createIntermediaryGrid(lock, key);

                if (!containsOverlap(resultGrid)) {
                    compatiblePairs++;
                }
            }
        }
        return compatiblePairs;
    }

    private static char[][] createIntermediaryGrid(char[][] lock, char[][] key) {
        int rows = lock.length;
        int cols = lock[0].length;
        char[][] grid = new char[rows][cols];

        // Initialize grid with '.'
        for (int i = 0; i < rows; i++) {
            Arrays.fill(grid[i], '.');
        }

        // Fill grid with the lock
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (lock[i][j] == '#') {
                    grid[i][j] = '#';
                }
            }
        }

        // Overlay the key
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (key[i][j] == '#') {
                    if (grid[i][j] == '#') {
                        grid[i][j] = '@'; // Mark overlap
                    } else {
                        grid[i][j] = '#'; // Fill with key
                    }
                }
            }
        }

        return grid;
    }

    // Check if the grid contains an overlap
    private static boolean containsOverlap(char[][] grid) {
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell == '@') {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Object part2() {
        return "That's all folk!";
    }
}
