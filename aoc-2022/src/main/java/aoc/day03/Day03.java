package aoc.day03;

import aoc.DayOld;
import aoc.parser.ParseUtils;

import java.util.List;
import java.util.stream.IntStream;

public class Day03 implements DayOld {
    @Override
    public String part1(List<String> input) {
        var sum = ParseUtils.castInputToStream(input)
                .map(String::trim)
                .map(e -> new String[]{e.substring(0, e.length()/2), e.substring(e.length()/2)})
                .mapToInt(e -> getPriorities(e[0]).filter(i -> getPriorities(e[1]).anyMatch(j -> j == i)).findFirst().getAsInt())
                .sum();
        return String.valueOf(sum);
    }

    @Override
    public String part2(List<String> input) {
        String[] s = ParseUtils.castInputToStream(input).map(String::trim).toArray(String[]::new);
        var sum =  IntStream.range(0, s.length/3)
                .map(x -> x * 3)
                .map(x -> getPriorities(s[x]).filter(i -> getPriorities(s[x+1]).anyMatch(j -> j == i) && getPriorities(s[x+2]).anyMatch(j -> j == i)).findFirst().getAsInt())
                .sum();
        return String.valueOf(sum);
    }

    private IntStream getPriorities(String s) {
        return s.chars().map(i -> i >= 'a' && i <= 'z' ? i - 'a' + 1 : i - 'A' + 1 + 26);
    }
}
