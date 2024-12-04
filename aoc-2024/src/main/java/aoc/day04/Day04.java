package aoc.day04;

import aoc.Day2024;

import java.util.stream.IntStream;

public class Day04 extends Day2024 {

    private final char[][] grid;

    public Day04() {
        super(4);
        grid = dayGrid();
    }

    public static void main(String[] args) {
        new Day04().printParts();
    }

    @Override
    public Object part1() {
        return countOccurrences(grid, "XMAS");
    }

    @Override
    public Object part2() {
        return countXMASPatterns(grid);
    }

    public static int countOccurrences(char[][] grid, String target) {
        final int[][] DIRECTIONS = {
                {0, 1},   // Right
                {1, 0},   // Down
                {0, -1},  // Left
                {-1, 0},  // Up
                {1, 1},   // Down-right
                {-1, -1}, // Up-left
                {1, -1},  // Down-left
                {-1, 1}   // Up-right
        };

        return IntStream.range(0, grid.length)
                .map(row -> IntStream.range(0, grid[0].length)
                        .map(col -> (int) IntStream.range(0, DIRECTIONS.length)
                                .filter(dir -> matchesPattern(grid, target, row, col, DIRECTIONS[dir]))
                                .count())
                        .sum())
                .sum();
    }

    private static boolean matchesPattern(char[][] grid, String target, int row, int col, int[] direction) {
        int targetLength = target.length();
        int rows = grid.length;
        int cols = grid[0].length;

        return IntStream.range(0, targetLength)
                .allMatch(k -> {
                    int newRow = row + k * direction[0];
                    int newCol = col + k * direction[1];
                    return newRow >= 0 && newRow < rows &&
                            newCol >= 0 && newCol < cols &&
                            grid[newRow][newCol] == target.charAt(k);
                });
    }

    public static int countXMASPatterns(char[][] grid) {
        return (int) IntStream.range(1, grid.length - 1)
                .flatMap(row -> IntStream.range(1, grid[0].length - 1)
                        .filter(col -> grid[row][col] == 'A' && isXMASPattern(grid, row, col)))
                .count();
    }

    private static boolean isXMASPattern(char[][] grid, int centerRow, int centerCol) {
        // Diagonal letters around 'A'
        char topLeft = grid[centerRow - 1][centerCol - 1];
        char topRight = grid[centerRow - 1][centerCol + 1];
        char bottomLeft = grid[centerRow + 1][centerCol - 1];
        char bottomRight = grid[centerRow + 1][centerCol + 1];

        // Check both diagonals
        return isMAS(topLeft, bottomRight) && isMAS(topRight, bottomLeft);
    }

    private static boolean isMAS(char a, char b) {
        return (a == 'M' && b == 'S') || (a == 'S' && b == 'M');
    }
}
