package aoc.day02;

import aoc.Day2025;

import java.util.ArrayList;
import java.util.List;

public class Day02 extends Day2025 {

    private record Range(long start, long end) {
    }

    private final List<Range> ranges;

    public Day02() {
        super(2, "Gift Shop");
        this.ranges = new ArrayList<>();
        String line = day();
        String[] rangeStrings = line.split(",");
        for (String rangeStr : rangeStrings) {
            String[] parts = rangeStr.split("-");
            this.ranges.add(new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
        }
    }

    @Override
    public Object part1() {
        long totalInvalidSum = 0;
        for (Range range : this.ranges) {
            for (long i = range.start(); i <= range.end(); i++) {
                if (isInvalid(i)) {
                    totalInvalidSum += i;
                }
            }
        }
        return totalInvalidSum;
    }

    private boolean isInvalid(long number) {
        String s = String.valueOf(number);
        int len = s.length();
        if (len % 2 != 0) {
            return false;
        }
        int half = len / 2;
        String firstHalf = s.substring(0, half);
        String secondHalf = s.substring(half);
        return firstHalf.equals(secondHalf);
    }

    @Override
    public Object part2() {
        long totalInvalidSum = 0;
        for (Range range : this.ranges) {
            for (long i = range.start(); i <= range.end(); i++) {
                if (isInvalidPart2(i)) {
                    totalInvalidSum += i;
                }
            }
        }
        return totalInvalidSum;
    }

    private boolean isInvalidPart2(long number) {
        String s = String.valueOf(number);
        int len = s.length();
        if (len < 2) { // A sequence repeated at least twice must have length >= 2
            return false;
        }

        // The length of the repeating sub-sequence
        for (int subLen = 1; subLen <= len / 2; subLen++) {
            if (len % subLen == 0) { // The string must be composed of a whole number of repetitions
                String sequence = s.substring(0, subLen);
                if (s.equals(sequence.repeat(len / subLen))) {
                    return true;
                }
            }
        }

        return false;
    }
}
