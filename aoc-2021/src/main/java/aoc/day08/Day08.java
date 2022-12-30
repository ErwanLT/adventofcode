package aoc.day08;

import aoc.DayOld;

import java.util.List;
import java.util.function.BiFunction;
import static java.util.Arrays.stream;

public class Day08 implements DayOld {
    @Override
    public String part1(List<String> input) {

        long count = input.stream()
                .map(e -> e.split("\\|"))
                .map(e -> new String[][]{e[0].split(" "), stream(e[1].split(" ")).filter(f -> !f.isBlank()).toArray(String[]::new)})
                .flatMap(e -> stream(e[1]))
                .filter(e -> e.length() == 2 || e.length() == 3 || e.length() == 4 || e.length() == 7)
                .count();

        return String.valueOf(count);
    }

    @Override
    public String part2(List<String> input) {
        return solve(input, DisplayOutputDecoder::decode);
    }

    private String solve(List<String> rawInput, BiFunction<DisplayOutputDecoder, String, Integer> decodeFunction) {
        DisplayOutputDecoder decoder = new DisplayOutputDecoder();

        int result = rawInput.stream()
                .mapToInt(value -> decodeFunction.apply(decoder, value))
                .sum();

        return String.valueOf(result);
    }
}
