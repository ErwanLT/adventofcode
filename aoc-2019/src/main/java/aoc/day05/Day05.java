package aoc.day05;

import aoc.Day2019;
import aoc.intcode.IntCodeComputer;

public class Day05 extends Day2019 {

    public Day05(){
        super(5);
    }

    public static void main(String[] args) {
        new Day05().printParts();
    }

    @Override
    public Object part1() {
        String program = day();
        IntCodeComputer computer = new IntCodeComputer(program);
        return computer.runWithInput(1L);
    }

    @Override
    public Object part2() {
        String program = day();
        IntCodeComputer computer = new IntCodeComputer(program);
        return computer.runWithInput(5L);
    }
}
