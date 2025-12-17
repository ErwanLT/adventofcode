package aoc.day02;

import aoc.Day2019;
import aoc.intcode.IntCodeComputer;

public class Day02 extends Day2019 {

    public Day02() {
        super(2);
    }

    public static void main(String[] args) {
        new Day02().printParts();
    }

    @Override
    public Object part1() {
        String program = day();
        IntCodeComputer computer = new IntCodeComputer(program);
        computer.setMemory(1, 12);
        computer.setMemory(2, 2);
        computer.run();
        return computer.getMemory(0);
    }

    @Override
    public Object part2() {
        String program = day();
        IntCodeComputer computer = new IntCodeComputer(program);
        int targetOutput = 19690720;

        for (int noun = 0; noun <= 99; noun++) {
            for (int verb = 0; verb <= 99; verb++) {
                computer.reset();
                computer.setMemory(1, noun);
                computer.setMemory(2, verb);
                computer.run();
                if (computer.getMemory(0) == targetOutput) {
                    return 100 * noun + verb;
                }
            }
        }
        return "Not found";
    }
}
