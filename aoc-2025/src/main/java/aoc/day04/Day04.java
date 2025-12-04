package aoc.day04;

import aoc.Day2025;
import aoc.location.Coord; // Import the Coord class

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day04 extends Day2025 {

    private final char[][] grid;

    public Day04() {
        super(4, "Printing Department");
        this.grid = dayGrid();
    }

    @Override
    public Object part1() {
        return findRemovableRolls(this.grid).size();
    }

    @Override
    public Object part2() {
        char[][] gridCopy = new char[this.grid.length][];
        for (int i = 0; i < this.grid.length; i++) {
            gridCopy[i] = Arrays.copyOf(this.grid[i], this.grid[i].length);
        }

        int totalRemovedRolls = 0;
        while (true) {
            List<Coord> removableRolls = findRemovableRolls(gridCopy); // Changed to List<Coord>
            if (removableRolls.isEmpty()) {
                break;
            }

            for (Coord coords : removableRolls) { // Changed to Coord
                gridCopy[coords.x][coords.y] = '.'; // Access x and y fields of Coord
            }
            totalRemovedRolls += removableRolls.size();
        }

        return totalRemovedRolls;
    }

    private List<Coord> findRemovableRolls(char[][] grid) { // Changed to List<Coord>
        List<Coord> removableRolls = new ArrayList<>();
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] == '@') {
                    if (countNeighbors(grid, r, c) < 4) {
                        removableRolls.add(new Coord(r, c)); // Changed to new Coord(r, c)
                    }
                }
            }
        }
        return removableRolls;
    }

    private int countNeighbors(char[][] grid, int r, int c) {
        int count = 0;
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length) {
                if (grid[nr][nc] == '@') {
                    count++;
                }
            }
        }
        return count;
    }
}