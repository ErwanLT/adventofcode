package aoc.day01;

import aoc.Day;
import aoc.parser.ParseUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;

public class Day01 implements Day {
    private static final String DEFAULT_DELIMITER = "\n";

    @Override
    public String part1(List<String> input) {
        return String.valueOf(ParseUtils.castInputToLongStream(input).sum());
    }

    @Override
    public String part2(List<String> input) {
        Set<Long> encountered = new HashSet<>();
        var numbers = ParseUtils.castInputToLongArray(input);

        var frequency = 0L;
        while (true) {
            for (long n : numbers) {
                frequency += n;
                if (!encountered.add(frequency)) return String.valueOf(frequency);
            }
        }
    }

}
