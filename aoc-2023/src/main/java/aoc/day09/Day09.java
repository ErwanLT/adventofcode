package aoc.day09;

import aoc.Day2023;

import java.util.*;

public class Day09 extends Day2023 {

    private long sum;

    public Day09() {
        super(9);
    }

    public static void main(String[] args){
        new Day09().printParts();
    }

    @Override
    public Object part1() {
        long result = 0;

        for (final String line : dayStrings()) {
            List<Long> values = parseLongValues(line);
            long partResult = calculateSum(values);
            result += partResult;
        }

        return result;
    }

    @Override
    public Object part2() {
        long result = 0;

        for (final String line : dayStrings()) {
            List<Long> values = parseLongValues(line);
            long partResult = calculateLeftmostSum(values);
            result += partResult;
        }

        return result;
    }

    private List<Long> parseLongValues(String line) {
        String[] temp = line.split("\\s+");
        List<Long> values = new ArrayList<>(temp.length);
        for (String s : temp) {
            values.add(Long.parseLong(s));
        }
        return values;
    }

    private long calculateSum(List<Long> values) {
        long partResult = values.get(values.size() - 1);

        boolean finished = false;
        while (!finished) {
            List<Long> newValues = calculateDifferences(values);
            partResult += newValues.get(newValues.size() - 1);
            values = newValues;
            finished = allZeros(newValues);
        }

        return partResult;
    }

    private long calculateLeftmostSum(List<Long> values) {
        long partResult = 0;
        Stack<Long> leftmostValues = new Stack<>();
        leftmostValues.push(values.get(0));

        boolean finished = false;
        while (!finished) {
            List<Long> newValues = calculateDifferences(values);
            leftmostValues.push(newValues.get(0));
            values = newValues;
            finished = allZeros(newValues);
        }

        while (!leftmostValues.empty()) {
            long val = leftmostValues.pop();
            partResult = val - partResult;
        }

        return partResult;
    }

    private List<Long> calculateDifferences(List<Long> values) {
        List<Long> newValues = new ArrayList<>(values.size() - 1);
        boolean allZeros = true;

        for (int i = 0; i < values.size() - 1; i++) {
            long sub = values.get(i + 1) - values.get(i);
            if (sub != 0) {
                allZeros = false;
            }
            newValues.add(sub);
        }

        return newValues;
    }

    private boolean allZeros(List<Long> values) {
        return values.stream().allMatch(value -> value == 0);
    }
}
