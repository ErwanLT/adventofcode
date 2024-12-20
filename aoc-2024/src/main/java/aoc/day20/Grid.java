package aoc.day20;

import java.io.*;
import java.util.*;

public class Grid {
    private final char[][] grid;
    private final int height, width;
    private final int[] start, end;
    private final double[][] costMap;
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // N, E, S, W

    public Grid(List<String> lines) {
        this.height = lines.size();
        this.width = lines.get(0).length();
        this.grid = new char[height][width];
        this.costMap = new double[height][width];
        this.start = new int[2];
        this.end = new int[2];
        initializeGrid(lines);
    }

    private void initializeGrid(List<String> lines) {
        for (int i = 0; i < height; i++) {
            grid[i] = lines.get(i).toCharArray();
            Arrays.fill(costMap[i], Double.POSITIVE_INFINITY);
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 'S') {
                    start[0] = i;
                    start[1] = j;
                } else if (grid[i][j] == 'E') {
                    end[0] = i;
                    end[1] = j;
                }
            }
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    public void performDijkstra() {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(n -> n.cost));
        costMap[start[0]][start[1]] = 0;
        priorityQueue.add(new Node(start[0], start[1], 0));

        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.poll();
            if (current.cost > costMap[current.x][current.y]) continue;

            for (int[] direction : DIRECTIONS) {
                int newRow = current.x + direction[0];
                int newCol = current.y + direction[1];
                double newCost = current.cost + 1;

                if (isValid(newRow, newCol) && grid[newRow][newCol] != '#' && newCost < costMap[newRow][newCol]) {
                    costMap[newRow][newCol] = newCost;
                    priorityQueue.add(new Node(newRow, newCol, newCost));
                }
            }
        }
    }

    public int calculateCheats(int minCheat) {
        performDijkstra();
        int count = 0;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (grid[row][col] == '#') {
                    List<Double> adjacentCosts = getAdjacentCosts(row, col);
                    if (adjacentCosts.size() >= 2) {
                        double maxDiff = Collections.max(adjacentCosts) - Collections.min(adjacentCosts) - 2;
                        if (maxDiff >= minCheat) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    public int calculateCheatsJumps(int maxJump, int minCheat) {
        performDijkstra();
        int count = 0;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (grid[row][col] != '#') {
                    count += calculateCheatJumpsForCell(row, col, maxJump, minCheat);
                }
            }
        }
        return count;
    }

    private int calculateCheatJumpsForCell(int row, int col, int maxJump, int minCheat) {
        int count = 0;

        for (int dx = -maxJump; dx <= maxJump; dx++) {
            for (int dy = -maxJump; dy <= maxJump; dy++) {
                int steps = Math.abs(dx) + Math.abs(dy);
                if ((dx == 0 && dy == 0) || steps > maxJump) continue;

                int newRow = row + dx;
                int newCol = col + dy;

                if (isValid(newRow, newCol) && !Double.isInfinite(costMap[newRow][newCol])) {
                    double maxDiff = costMap[newRow][newCol] - costMap[row][col] - steps;
                    if (maxDiff >= minCheat) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private List<Double> getAdjacentCosts(int row, int col) {
        List<Double> costs = new ArrayList<>();

        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            if (isValid(newRow, newCol) && !Double.isInfinite(costMap[newRow][newCol])) {
                costs.add(costMap[newRow][newCol]);
            }
        }
        return costs;
    }

    private static class Node {
        final int x, y;
        final double cost;

        Node(int x, int y, double cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
}