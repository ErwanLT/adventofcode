package aoc.day02;

import aoc.Day2019;
import aoc.intcode.IntcodeComputer;


public class Day02 extends Day2019 {

    protected Day02() {
        super(2);
    }

    public static void main(String[] args) {
        new Day02().printParts();
    }

    @Override
    public Object part1() {
        return execute(12, 2);
    }

    @Override
    public Object part2() {
        return bruteForceFindingNumber(19690720, 99);
    }

    private long execute(int x, int y) {
        IntcodeComputer computer = new IntcodeComputer(2, x, y);
        computer.run();
        return computer.firstElement();
    }

    private int bruteForceFindingNumber(int number, int bound) {
        for (int i = 0; i < bound; i++) {
            for (int j = 0; j < bound; j++) {
                if (execute(i, j) == number) {
                    return 100 * i + j;
                }
            }
        }
        return -1;
    }
}
