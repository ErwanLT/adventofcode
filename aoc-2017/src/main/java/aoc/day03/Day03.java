package aoc.day03;

import aoc.DayOld;

import java.util.List;

public class Day03 implements DayOld {

    @Override
    public String part1(List<String> input) {
        return String.valueOf(calculateManhattanDistance(Integer.parseInt(input.get(0))));
    }

    @Override
    public String part2(List<String> input) {
        return "";
    }

    public static int calculateManhattanDistance(int target) {
        // Calculate grid size to accommodate the target square
        int gridSize = (int) Math.ceil(Math.sqrt(target));
        if (gridSize % 2 == 0) {
            gridSize += 1; // Ensure gridSize is odd to center square 1
        }

        // Center of the grid
        int center = gridSize / 2;
        int[][] grid = new int[gridSize][gridSize];

        // Populate the grid in a spiral pattern
        int x = center;
        int y = center;
        int num = 1;
        grid[x][y] = num;

        int step = 1;
        while (num < target) {
            // Move right and fill
            for (int i = 0; i < step && num < target; i++) {
                y++;
                num++;
                grid[x][y] = num;
            }
            // Move up and fill
            for (int i = 0; i < step && num < target; i++) {
                x--;
                num++;
                grid[x][y] = num;
            }
            step++;
            // Move left and fill
            for (int i = 0; i < step && num < target; i++) {
                y--;
                num++;
                grid[x][y] = num;
            }
            // Move down and fill
            for (int i = 0; i < step && num < target; i++) {
                x++;
                num++;
                grid[x][y] = num;
            }
            step++;
        }

        // Print the spiral grid
        System.out.println("Spiral Grid:");
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (grid[i][j] != 0) {
                    System.out.printf("%4d ", grid[i][j]);
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }

        // Locate the target coordinates in the grid and calculate Manhattan Distance
        int targetX = 0;
        int targetY = 0;
        outer:
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (grid[i][j] == target) {
                    targetX = i - center;
                    targetY = j - center;
                    break outer;
                }
            }
        }

        int distance = Math.abs(targetX) + Math.abs(targetY);
        return distance;
    }
}
