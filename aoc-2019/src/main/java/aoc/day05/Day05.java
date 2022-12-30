package aoc.day05;

import aoc.Day2019;
import aoc.intcode.IntcodeComputer;

public class Day05 extends Day2019 {

    public Day05(){
        super(5);
    }

    public static void main(String[] args) {
        new Day05().printParts();
    }

    @Override
    public Object part1() {
        long res;
        IntcodeComputer intcodeComputer = new IntcodeComputer(5, 1);
        while ((res = intcodeComputer.run()) == 0) ;
        return res;
    }

    @Override
    public Object part2() {
        return new IntcodeComputer(5, 5).run();
    }
}
