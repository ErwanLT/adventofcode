package aoc.day14;

import aoc.Day2024;
import aoc.parser.ReadFormatedString;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Day14 extends Day2024 {

    private static final int GRID_WIDTH = 101;
    private static final int GRID_HEIGHT = 103;

    public Day14() {
        super(14, "Restroom Redoubt");
    }

    public static void main(String[] args) {
        new Day14().printParts();
    }

    @Override
    public Object part1() {
        int[][] grid = simulateMotion(parseInput(), 100, false);
        return calculateSafetyFactor(grid);
    }

    private List<Robot> parseInput() {
        List<Robot> robots = new ArrayList<>();
        for(String s : dayStrings()) {
            Robot r = ReadFormatedString.readString(s, "p=%i,%i v=%i,%i", Robot.class);
            robots.add(r);
        }

        return robots;
    }

    @Override
    public Object part2() {
        List<Robot> robots = parseInput();
        simulateMotion(robots, 10000, true);
        return null;
    }

    private static void writeGridToFile(List<Robot> robots, int iteration) {
        // Créer le fichier it_XXX.txt
        try (PrintWriter writer = new PrintWriter(new FileWriter("it_" + String.format("%03d", iteration) + ".txt"))) {
            char[][] grid = new char[GRID_HEIGHT][GRID_WIDTH];
            // Initialiser la grille avec des '.'
            for (int y = 0; y < GRID_HEIGHT; y++) {
                for (int x = 0; x < GRID_WIDTH; x++) {
                    grid[y][x] = '.';
                }
            }

            // Placer les robots sur la grille
            for (Robot robot : robots) {
                grid[robot.y][robot.x] = '#';
            }

            // Écrire la grille dans le fichier
            for (char[] row : grid) {
                writer.println(new String(row)); // Convertir chaque ligne en chaîne et l'écrire dans le fichier
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[][] simulateMotion(List<Robot> robots, int seconds, boolean part2) {
        int[][] grid = new int[GRID_HEIGHT][GRID_WIDTH];
        for (int t = 0; t < seconds; t++) {
            for (Robot robot : robots) {
                robot.x = (robot.x + robot.vx + GRID_WIDTH) % GRID_WIDTH;
                robot.y = (robot.y + robot.vy + GRID_HEIGHT) % GRID_HEIGHT;
            }
            if (part2 && t==7131){
                writeGridToFile(robots, t);
            }
        }
        for (Robot robot : robots) {
            grid[robot.y][robot.x]++;
        }
        return grid;
    }

    private static int calculateSafetyFactor(int[][] grid) {
        int midX = GRID_WIDTH / 2;
        int midY = GRID_HEIGHT / 2;

        int q1 = 0, q2 = 0, q3 = 0, q4 = 0;

        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                if (x == midX || y == midY) continue; // Ignore middle line

                if (x < midX && y < midY) q1 += grid[y][x];
                else if (x >= midX && y < midY) q2 += grid[y][x];
                else if (x < midX && y >= midY) q3 += grid[y][x];
                else q4 += grid[y][x];
            }
        }

        return q1 * q2 * q3 * q4;
    }

}
