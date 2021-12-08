package aoc.day08;

import aoc.Day;

import java.util.List;
import java.util.stream.Stream;

public class Day08 implements Day {
    @Override
    public String part1(List<String> input) {
        long count = input.stream().filter(e -> !e.isBlank())
                .map(e -> e.split("\\|")[1])
                .flatMap(e -> Stream.of(e.split(" ")))
                .filter(e -> e.length() == 2 || e.length() == 3 || e.length() == 4 || e.length() == 7)
                .count();
        return String.valueOf(count);
    }

    @Override
    public String part2(List<String> input) {
        return null;
    }
}
