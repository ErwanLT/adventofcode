package aoc.day02;

import aoc.Computer;
import aoc.Day;

import java.util.List;

public class Day02 implements Day {
    @Override
    public String part1(List<String> input) {
        Computer computer = new Computer(input.get(0));
        computer.getParts()[1] = 12;
        computer.getParts()[2] = 2;
        computer.compute();
        return String.valueOf(computer.getParts()[0]);
    }

    @Override
    public String part2(List<String> input) {
        long wantedOutput = 19690720;
        for (int i = 0; i <= 99; i++) {
            for (int j = 0; j <= 99; j++) {
                Computer computer = new Computer(input.get(0));
                computer.getParts()[1] = i;
                computer.getParts()[2] = j;
                computer.compute();
                long noun = computer.getParts()[1];
                long verb = computer.getParts()[2];
                if (computer.getParts()[0] == wantedOutput) {
                    return String.valueOf(100 * noun + verb);
                }
            }
        }
        return null;
    }
}
