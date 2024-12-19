package aoc.day19;

import aoc.Day2024;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 extends Day2024 {

    public Day19() {
        super(19, "Linen Layout");
    }

    public static void main(String[] args) {
        new Day19().printParts();
    }

    private List<List<String>> parseInput() {
        var split = day().split("\n\n");
        List<String> towelPatterns = Arrays.asList(split[0].split(", "));
        List<String> designs = Arrays.stream(split[1].strip().split("\n")).map(String::trim).toList();
        return List.of(towelPatterns, designs);
    }

    @Override
    public Object part1() {
        var split = parseInput();

        List<String> towelPatterns = split.getFirst();
        List<String> designs = split.getLast();

        return countPossibleDesigns(towelPatterns, designs);
    }

    private static int countPossibleDesigns(List<String> towelPatterns, List<String> designs) {
        int possibleCount = 0;
        for (String design : designs) {
            if (canConstructDesign(design, towelPatterns, new HashMap<>())) {
                possibleCount++;
            }
        }
        return possibleCount;
    }

    private static boolean canConstructDesign(String design, List<String> towelPatterns, Map<String, Boolean> memo) {
        // Check memoized results
        if (memo.containsKey(design)) {
            return memo.get(design);
        }

        // Base case: empty design
        if (design.isEmpty()) {
            return true;
        }

        // Try each towel pattern
        for (String pattern : towelPatterns) {
            if (design.startsWith(pattern)) {
                String remainingDesign = design.substring(pattern.length());
                if (canConstructDesign(remainingDesign, towelPatterns, memo)) {
                    memo.put(design, true);
                    return true;
                }
            }
        }

        // If no pattern works, mark as impossible
        memo.put(design, false);
        return false;
    }

    @Override
    public Object part2() {
        var split = parseInput();

        List<String> towelPatterns = split.getFirst();
        List<String> designs = split.getLast();

        return countTotalArrangements(towelPatterns, designs);
    }

    private static long countTotalArrangements(List<String> towelPatterns, List<String> designs) {
        long totalArrangements = 0;
        for (String design : designs) {
            totalArrangements += countArrangements(design, towelPatterns, new HashMap<>());
        }
        return totalArrangements;
    }

    private static long countArrangements(String design, List<String> towelPatterns, Map<String, Long> memo) {
        // Check memoized results
        if (memo.containsKey(design)) {
            return memo.get(design);
        }

        // Base case: empty design
        if (design.isEmpty()) {
            return 1; // One way to arrange an empty design
        }

        long arrangements = 0;

        // Try each towel pattern
        for (String pattern : towelPatterns) {
            if (design.startsWith(pattern)) {
                String remainingDesign = design.substring(pattern.length());
                arrangements += countArrangements(remainingDesign, towelPatterns, memo);
            }
        }

        // Memoize the result
        memo.put(design, arrangements);
        return arrangements;
    }
}
