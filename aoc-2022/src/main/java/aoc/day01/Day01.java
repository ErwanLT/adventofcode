package aoc.day01;

import aoc.Day;
import aoc.parser.ParseUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

public class Day01 implements Day {

    private static String inputs = "";

    @Override
    public String part1(List<String> input) {
        parseInput(input);
        return input().max().toString();
    }

    @Override
    public String part2(List<String> input) {
        parseInput(input);
        long[] nums = input().sorted().toArray();
        return String.valueOf(nums[nums.length-1] + nums[nums.length-2] + nums[nums.length-3]);
    }

    private void parseInput(List<String> input) {
        inputs = ParseUtils.castInputToString("\n", input);
    }

    private LongStream input () {
        return Arrays.stream(inputs.split("\n\n"))
                .mapToLong(s -> Arrays.stream(s.split("\n"))
                        .map(String::trim).mapToLong(Long::parseLong).sum());
    }
}
