package aoc.day13;

import aoc.Day2024;


import static aoc.parser.ReadFormatedString.readString;

public class Day13 extends Day2024 {
    public Day13() {
        super(13, "Claw Contraption");
    }

    public static void main(String[] args) {
        new Day13().printParts();
    }

    @Override
    public Object part1() {
        return solve(0);
    }

    @Override
    public Object part2() {
        return solve(10_000_000_000_000L);
    }

    private long solve(long offset) {
        return dayStream("\n\n")
                .map(s -> readString(s, "Button A: X+%n, Y+%n\nButton B: X+%n, Y+%n\nPrize: X=%n, Y=%n", Machine.class))
                .map(m -> m.withPrize(m.prizeX() + offset, m.prizeY() + offset))
                .mapToLong(Machine::fewestTokens)
                .sum();
    }
}
