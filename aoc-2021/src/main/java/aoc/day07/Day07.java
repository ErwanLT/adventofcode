package aoc.day07;

import aoc.Day;

import java.util.Arrays;
import java.util.List;

public class Day07 implements Day {

    private static int[] crabPositions;
    private static int minPosition;
    private static int maxPosition;

    @Override
    public String part1(List<String> input) {
        crabPositions = Arrays.stream(input.get(0).trim().split(",")).mapToInt(Integer::parseInt).toArray();
        minPosition = Arrays.stream(crabPositions).min().orElseThrow();
        maxPosition = Arrays.stream(crabPositions).max().orElseThrow();

        int minCost = Integer.MAX_VALUE;
        for (int i = minPosition; i <= maxPosition; i++) {
            int cost = 0;
            for (int position : crabPositions) {
                cost += Math.abs(position - i);
            }
            minCost = Math.min(cost, minCost);
        }
        return String.valueOf(minCost);
    }

    @Override
    public String part2(List<String> input) {
        int minCost = Integer.MAX_VALUE;
        for (int i = minPosition; i <= maxPosition; i++) {
            var cost = 0;
            for (int position : crabPositions) {
                int distance = Math.abs(position - i);
                cost += distance * (distance + 1) / 2;
            }
            minCost = Math.min(cost, minCost);
        }
        return String.valueOf(minCost);
    }
}
