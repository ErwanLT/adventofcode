package aoc.day10;

import aoc.Day2024;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Day10 extends Day2024 {

    private final int[][] input;

    public Day10() {
        super(10, "Hoof It");
        input = convertCharArrayToIntArray(dayGrid());
    }

    public static void main(String[] args) {
        new Day10().printParts();
    }

    @Override
    public Object part1() {
        return calculateTrailheadScores(input, true);
    }

    @Override
    public Object part2() {
        return calculateTrailheadScores(input, false);
    }

    public static int[][] convertCharArrayToIntArray(char[][] charMap) {
        int rows = charMap.length;
        int cols = charMap[0].length;
        int[][] intMap = new int[rows][cols];

        StringBuilder mapVisualization = new StringBuilder("Topographic Map:\n");

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                intMap[r][c] = Character.getNumericValue(charMap[r][c]);

                // Ajouter la version colorée
                mapVisualization.append(getColoredTopographySymbol(intMap[r][c]));
            }
            mapVisualization.append("\n"); // Nouvelle ligne pour chaque rangée
        }

        System.out.println(mapVisualization.toString());
        return intMap;
    }

    private static String getColoredTopographySymbol(int height) {
        // Définir des couleurs selon les niveaux de hauteur
        String color;
        if (height >= 9) {
            color = "\u001B[31m"; // Rouge
        } else if (height >= 6) {
            color = "\u001B[33m"; // Jaune
        } else if (height >= 3) {
            color = "\u001B[32m"; // Vert
        } else {
            color = "\u001B[34m"; // Bleu
        }

        // Ajouter le symbole et remettre la couleur par défaut
        return color + "#" + "\u001B[0m"; // # représente la "topographie", ajustable
    }

    public static int calculateTrailheadScores(int[][] map, boolean part1) {
        int rows = map.length;
        int cols = map[0].length;
        int totalScore = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (map[r][c] == 0) { // Found a trailhead
                    if(part1) {
                        totalScore += calculateTrailheadScore(map, r, c);
                    } else {
                        totalScore += countDistinctTrails(map, r, c, new HashSet<>());
                    }
                }
            }
        }

        return totalScore;
    }

    public static int calculateTrailheadScore(int[][] map, int startRow, int startCol) {
        int rows = map.length;
        int cols = map[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        Set<String> reachableNines = new HashSet<>();

        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        int[] directions = {-1, 0, 1, 0, -1}; // to move up, right, down, left

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];

            // If we reach a 9, add its position to the set of reachable 9s
            if (map[row][col] == 9) {
                reachableNines.add(row + "," + col);
            }

            // Explore neighbors
            for (int i = 0; i < 4; i++) {
                int newRow = row + directions[i];
                int newCol = col + directions[i + 1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && !visited[newRow][newCol]) {
                    // Check if the next step is a valid hike (height difference is +1)
                    if (map[newRow][newCol] == map[row][col] + 1) {
                        queue.add(new int[]{newRow, newCol});
                        visited[newRow][newCol] = true;
                    }
                }
            }
        }

        // The score is the number of unique 9s reachable from this trailhead
        return reachableNines.size();
    }

    private static int countDistinctTrails(int[][] map, int r, int c, Set<String> visitedPaths) {
        int rows = map.length;
        int cols = map[0].length;

        // Use a set to store distinct trails
        Set<String> trails = new HashSet<>();

        // DFS stack
        class State {
            int x, y, height;
            String path;

            State(int x, int y, int height, String path) {
                this.x = x;
                this.y = y;
                this.height = height;
                this.path = path;
            }
        }

        // Stack for DFS
        java.util.Stack<State> stack = new java.util.Stack<>();
        stack.push(new State(r, c, 0, r + "," + c));

        while (!stack.isEmpty()) {
            State current = stack.pop();

            // If we've reached height 9, record this trail
            if (current.height == 9) {
                trails.add(current.path);
                continue;
            }

            // Explore neighbors
            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            for (int[] dir : directions) {
                int newRow = current.x + dir[0];
                int newCol = current.y + dir[1];

                // Check bounds and valid step
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    int nextHeight = map[newRow][newCol];
                    if (nextHeight == current.height + 1) {
                        String nextPath = current.path + "->" + newRow + "," + newCol;
                        if (!visitedPaths.contains(nextPath)) {
                            visitedPaths.add(nextPath);
                            stack.push(new State(newRow, newCol, nextHeight, nextPath));
                        }
                    }
                }
            }
        }

        return trails.size();
    }
}
