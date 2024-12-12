package aoc.day12;

import aoc.Day2024;

import java.util.LinkedList;
import java.util.Queue;

public class Day12 extends Day2024 {

    public Day12(){
        super(12, "");
    }

    public static void main(String[] args) {
        new Day12().printParts();
    }

    @Override
    public Object part1() {
        return calculateTotalPrice(dayGrid());
    }

    @Override
    public Object part2() {
        return null;
    }

    static class Region {
        char type;
        int area;
        int perimeter;

        Region(char type) {
            this.type = type;
            this.area = 0;
            this.perimeter = 0;
        }
    }

    public static int calculateTotalPrice(char[][] garden) {
        int rows = garden.length;
        int cols = garden[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int totalPrice = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!visited[r][c]) {
                    Region region = new Region(garden[r][c]);
                    floodFill(garden, visited, r, c, region);
                    totalPrice += region.area * region.perimeter;
                }
            }
        }

        return totalPrice;
    }

    private static void floodFill(char[][] garden, boolean[][] visited, int r, int c, Region region) {
        int rows = garden.length;
        int cols = garden[0].length;
        char type = garden[r][c];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{r, c});
        visited[r][c] = true;

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];
            region.area++;

            // Check all 4 directions
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    if (garden[newRow][newCol] == type && !visited[newRow][newCol]) {
                        visited[newRow][newCol] = true;
                        queue.add(new int[]{newRow, newCol});
                    } else if (garden[newRow][newCol] != type) {
                        region.perimeter++;
                    }
                } else {
                    // Out of bounds contributes to the perimeter
                    region.perimeter++;
                }
            }
        }
    }
}
