package aoc.day04;

import aoc.Day2019;
import aoc.day03.Day03;

import java.util.stream.IntStream;

public class Day04 extends Day2019 {

    public Day04(){
        super(4);
    }

    public static void main(String[] args) {
        new Day04().printParts();
    }

    @Override
    public Object part1() {
        return checkPasswords(false);
    }

    @Override
    public Object part2() {
        return checkPasswords(true);
    }

    public long meetsCriteria(int lowerBound, int higherBound, boolean checkGroup) {
        return IntStream.range(lowerBound, higherBound + 1).filter(e -> meetsCriteria(e, checkGroup)).count();
    }

    public boolean meetsCriteria(int input, boolean checkGroup) {
        int lastSeen = 10, current, adjacentTheSame = -2, skip = 10;

        for (int i = 0; input > 0; i++) {
            current = input % 10;
            if (skip != current && current == lastSeen) {
                if (checkGroup && adjacentTheSame + 1 == i) {
                    adjacentTheSame = -2;
                    skip = current;
                } else if (adjacentTheSame == -2) {
                    adjacentTheSame = i;
                }
            }
            if (lastSeen < current)
                return false;
            lastSeen = current;
            input /= 10;
        }

        return adjacentTheSame != -2;
    }

    private int checkPasswords(boolean checkGroup) {
        return Math.toIntExact(meetsCriteria(231832, 767346, checkGroup));
    }

}
