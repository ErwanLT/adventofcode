package aoc.day01;

import aoc.Day2019;

public class Day01 extends Day2019 {

    public Day01() {
        super(1);
    }

    public static void main(String[] args) {
        new Day01().printParts();
    }

    @Override
    public Object part1() {
        return dayIntStream().map(this::getFuel).sum();
    }

    @Override
    public Object part2() {
        return dayIntStream().map(this::getRequiredFuel).sum();
    }

    private int getRequiredFuel(int mass) {
        int fuel = getFuel(mass);
        return fuel > 0 ? fuel + getRequiredFuel(fuel) : 0;
    }

    private int getFuel(int mass) {
        return (mass / 3) - 2;
    }
}
