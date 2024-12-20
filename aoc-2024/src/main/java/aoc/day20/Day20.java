package aoc.day20;

import aoc.Day2024;

import java.util.Arrays;

public class Day20 extends Day2024 {

    public Day20() {
        super(20, "Race Condition");
    }

    public static void main(String[] args) {
        new Day20().printParts();
    }

    @Override
    public Object part1() {
        Grid maze = new Grid(Arrays.asList(dayStrings()));
        return maze.calculateCheats(100);
    }


    @Override
    public Object part2() {
        Grid maze = new Grid(Arrays.asList(dayStrings()));
        return maze.calculateCheatsJumps(20,100);
    }
}
