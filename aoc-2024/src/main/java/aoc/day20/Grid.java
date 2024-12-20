package aoc.day20;

import java.io.*;
import java.util.*;

public class Grid {
    private char[][] grid;
    private int height, width;
    private int[] start, end;
    private final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // N, E, S, W
    private double[][] cheapmap;

    public Grid(List<String> lines) {
        this.height = lines.size();
        this.width = lines.get(0).length();
        this.grid = new char[height][width];
        this.cheapmap = new double[height][width];

        for (int i = 0; i < height; i++) {
            grid[i] = lines.get(i).toCharArray();
            Arrays.fill(cheapmap[i], Double.POSITIVE_INFINITY);
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 'S') {
                    start = new int[]{i, j};
                } else if (grid[i][j] == 'E') {
                    end = new int[]{i, j};
                }
            }
        }
    }

    private boolean isValid(int r, int c) {
        return r >= 0 && r < height && c >= 0 && c < width;
    }

    public void dijkstra() {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.cost));
        int startX = start[0], startY = start[1];
        cheapmap[startX][startY] = 0;
        pq.add(new Node(startX, startY, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int x = current.x, y = current.y;
            double sec = current.cost;

            if (sec > cheapmap[x][y]) continue;

            for (int[] direction : directions) {
                int newX = x + direction[0], newY = y + direction[1];
                double newSec = sec + 1;

                if (isValid(newX, newY) && grid[newX][newY] != '#' && newSec < cheapmap[newX][newY]) {
                    cheapmap[newX][newY] = newSec;
                    pq.add(new Node(newX, newY, newSec));
                }
            }
        }

        saveGridToFile("grid_output.txt");
    }

    private void saveGridToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (double[] row : cheapmap) {
                for (double cell : row) {
                    writer.printf("%5.0f,", cell);
                }
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int calculateCheats(int minCheat) {
        dijkstra();
        int counter = 0;

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (grid[x][y] == '#') {
                    List<Double> adjacent = new ArrayList<>();

                    for (int[] direction : directions) {
                        int newX = x + direction[0];
                        int newY = y + direction[1];
                        if (isValid(newX, newY) && !Double.isInfinite(cheapmap[newX][newY])) {
                            adjacent.add(cheapmap[newX][newY]);
                        }
                    }

                    if (adjacent.size() >= 2) {
                        double maxDiff = Collections.max(adjacent) - Collections.min(adjacent) - 2;
                        if (maxDiff >= minCheat) {
                            counter++;
                        }
                    }
                }
            }
        }
        return counter;
    }

    public int calculateCheatsJumps(int maxJump, int minCheat) {
        dijkstra();
        int counter = 0;

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (grid[x][y] != '#') {
                    for (int dx = -maxJump; dx <= maxJump; dx++) {
                        for (int dy = -maxJump; dy <= maxJump; dy++) {
                            int steps = Math.abs(dx) + Math.abs(dy);
                            if (dx == 0 && dy == 0 || steps > maxJump) continue;

                            int newX = x + dx, newY = y + dy;
                            if (isValid(newX, newY) && !Double.isInfinite(cheapmap[newX][newY])) {
                                double maxDiff = cheapmap[newX][newY] - cheapmap[x][y] - steps;
                                if (maxDiff >= minCheat) {
                                    counter++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return counter;
    }

    private static class Node {
        int x, y;
        double cost;

        Node(int x, int y, double cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
}