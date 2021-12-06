package aoc.day09;

import aoc.Day;
import aoc.parser.ParseUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;

public class Day09 implements Day {

    private static long[] inputs;

    @Override
    public String part1(List<String> input) {
        castInput(input);
        return String.valueOf(findNumber());
    }

    private void castInput(List<String> input) {
        inputs = ParseUtils.castInputToLongArray(input);
    }

    @Override
    public String part2(List<String> input) {
        castInput(input);
        return String.valueOf(findWeakness(findNumber()));
    }

    private static long findNumber() {
        for (int k = 0; k < inputs.length - 25; k++) {
            Set<Long> sums = new HashSet<>();
            for (int i = k; i < k + 25; i++) {
                for (int j = i + 1; j < k + 25; j++) {
                    sums.add(inputs[i] + inputs[j]);
                }
            }
            if (!sums.contains(inputs[k + 25])) {
                return inputs[k + 25];
            }
        }
        return 0;
    }

    private static long findWeakness(long invalidNumber) {
        for (int i = 2; i < inputs.length; i++) {
            for (int j = 0; j <= inputs.length - i; j++) {
                if (stream(inputs, j, j + i).sum() == invalidNumber) {
                    long[] window = copyOfRange(inputs, j, j + i + 1);
                    return stream(window).max().getAsLong() + stream(window).min().getAsLong();
                }
            }
        }
        return 0;
    }
}
