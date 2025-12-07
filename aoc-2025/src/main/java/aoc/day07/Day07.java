package aoc.day07;

import aoc.Day2025;

import java.util.HashSet;
import java.util.Set;

public class Day07 extends Day2025 {

    private final char[][] grid; // Changed from List<String> to char[][]
    private final int startRow;
    private final int startCol;

    public Day07() {
        super(7, "Laboratories");
        this.grid = dayGrid(); // Using dayGrid()

        int tempStartRow = -1;
        int tempStartCol = -1;
        if (this.grid != null && this.grid.length > 0 && this.grid[0].length > 0) {
            for (int r = 0; r < this.grid.length; r++) {
                for (int c = 0; c < this.grid[r].length; c++) { // Iterate through char array row
                    if (this.grid[r][c] == 'S') { // Accessing char[][] directly
                        tempStartRow = r;
                        tempStartCol = c;
                        break;
                    }
                }
                if (tempStartRow != -1) {
                    break;
                }
            }
        }
        this.startRow = tempStartRow;
        this.startCol = tempStartCol;
    }

    @Override
    public Object part1() {
        if (grid == null || grid.length == 0 || startRow == -1) { // Added grid.length check
            return 0;
        }

        Set<Integer> currentBeams = new HashSet<>();
        currentBeams.add(startCol);

        long splitCount = 0;

        for (char[] chars : grid) { // Iterate through rows by index
            Set<Integer> nextBeams = new HashSet<>();
            for (int beamCol : currentBeams) {
                // Ensure beam is within grid bounds before checking character
                if (beamCol < 0 || beamCol >= chars.length) { // Accessing grid[r].length
                    continue;
                }

                char cell = chars[beamCol]; // Accessing char[][] directly

                if (cell == '^') {
                    splitCount++;
                    nextBeams.add(beamCol - 1);
                    nextBeams.add(beamCol + 1);
                } else {
                    // For '.' and 'S', the beam passes straight through
                    nextBeams.add(beamCol);
                }
            }
            currentBeams = nextBeams;
        }

        return splitCount;
    }

    @Override
    public Object part2() {
        if (grid == null || grid.length == 0 || startRow == -1) { // Added grid.length check
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length; // Accessing grid[0].length
        long[][] dp = new long[rows][cols];

        dp[startRow][startCol] = 1;

        for (int r = startRow; r < rows - 1; r++) {
            for (int c = 0; c < cols; c++) {
                if (dp[r][c] == 0) {
                    continue;
                }

                char currentCell = grid[r][c]; // Accessing char[][] directly
                long paths = dp[r][c];

                if (currentCell == '.' || currentCell == 'S') {
                    dp[r + 1][c] += paths;
                } else if (currentCell == '^') {
                    if (c > 0) {
                        dp[r + 1][c - 1] += paths;
                    }
                    if (c < cols - 1) {
                        dp[r + 1][c + 1] += paths;
                    }
                }
            }
        }

        long totalTimelines = 0;
        for (int c = 0; c < cols; c++) {
            totalTimelines += dp[rows - 1][c];
        }

        return totalTimelines;
    }
}

