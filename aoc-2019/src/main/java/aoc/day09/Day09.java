package aoc.day09;

import aoc.Day2019;
import aoc.day08.Day08;
import aoc.intcode.IntcodeComputer;

public class Day09 extends Day2019 {

    public Day09(){
        super(9);
    }

    public static void main(String[] args) {
        new Day09().printParts();
    }

    @Override
    public Object part1() {
        return new IntcodeComputer(9, 1).run();
    }

    @Override
    public Object part2() {
        return new IntcodeComputer(9, 2).run();
    }
}
