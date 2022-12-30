package aoc.day04;

import aoc.DayOld;
import aoc.parser.ParseUtils;

import java.util.List;
import java.util.stream.Collectors;

import static aoc.parser.ReadFormatedString.readString;

public class Day04 implements DayOld {

    private List<Assignment> assignments;

    @Override
    public String part1(List<String> input) {
        assignments = ParseUtils.castInputToStream(input).map(String::trim)
                .map(s -> readString(s, "%n-%n,%n-%n", Assignment.class))
                .collect(Collectors.toList());
        var count = assignments.stream()
                .filter(Assignment::contained).count();
        return String.valueOf(count);
    }

    @Override
    public String part2(List<String> input) {
        var count = assignments.stream().filter(Assignment::overlap).count();
        return String.valueOf(count);
    }
}
