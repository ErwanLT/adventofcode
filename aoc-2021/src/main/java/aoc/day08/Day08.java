package aoc.day08;

import aoc.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.ObjectArrays.concat;
import static java.util.Arrays.asList;

public class Day08 implements Day {
    @Override
    public String part1(List<String> input) {
        return solve(input, DisplayOutputDecoder::countUniqueLengthOutputValues);
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
