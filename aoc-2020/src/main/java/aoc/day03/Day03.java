package aoc.day03;

import aoc.DayOld;

import java.util.List;

public class Day03 implements DayOld {

    private static char[][] grid;

    @Override
    public String part1(List<String> input) {

        grid = input.stream().map(String::toCharArray).toArray(char[][]::new);

        int trees = findEncounteredTree(3, 1);

        return String.valueOf(trees);
    }

    @Override
    public String part2(List<String> input) {

        grid = input.stream().map(String::toCharArray).toArray(char[][]::new);
        int trees = findEncounteredTree(1, 1) * findEncounteredTree(3, 1) * findEncounteredTree(5, 1) * findEncounteredTree(7, 1) * findEncounteredTree(1, 2);

        return String.valueOf(trees);
    }

    private static int findEncounteredTree(int x, int y) {
        System.out.println("------ Counting Encoutered Tree : Start ------");

        int trees = 0;
        for (int i = 0; i * y < grid.length; i++) {
            if (grid[i * y][i * x % grid[0].length] == '#') {
                trees++;
            }
        }
        System.out.println("------ Counting Encoutered Tree : End ------");
        return trees;
    }
}
