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

    private record InputData(List<String> towelPatterns, List<String> designs) {}

    private InputData parseInput() {
        String[] split = day().split("\n\n");
        List<String> towelPatterns = Arrays.asList(split[0].split(", "));
        List<String> designs = Arrays.stream(split[1].strip().split("\n"))
                .map(String::trim)
                .toList();
        return new InputData(towelPatterns, designs);
    }

    @Override
    public Object part1() {
        InputData inputData = parseInput();
        return countPossibleDesigns(inputData.towelPatterns(), inputData.designs());
    }

    private static int countPossibleDesigns(List<String> towelPatterns, List<String> designs) {
        return (int) designs.stream()
                .filter(design -> canConstructDesign(design, towelPatterns, createMemoMap()))
                .count();
    }

    private static boolean canConstructDesign(String design, List<String> towelPatterns, Map<String, Boolean> memo) {
        // Check memoized results explicitly
        if (memo.containsKey(design)) {
            return memo.get(design);
        }

        // Base case: empty design
        if (design.isEmpty()) {
            memo.put(design, true);
            return true;
        }

        // Try each towel pattern
        for (String pattern : towelPatterns) {
            if (design.startsWith(pattern)) {
                String remainingDesign = design.substring(pattern.length());
                if (canConstructDesign(remainingDesign, towelPatterns, memo)) {
                    memo.put(design, true); // Memoize result
                    return true;
                }
            }
        }

        // No pattern works
        memo.put(design, false); // Memoize result
        return false;
    }

    private static long countArrangements(String design, List<String> towelPatterns, Map<String, Long> memo) {
        // Check memoized results explicitly
        if (memo.containsKey(design)) {
            return memo.get(design);
        }

        // Base case: empty design
        if (design.isEmpty()) {
            memo.put(design, 1L); // One way to arrange an empty design
            return 1L;
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

    @Override
    public Object part2() {
        InputData inputData = parseInput();
        return countTotalArrangements(inputData.towelPatterns(), inputData.designs());
    }

    private static long countTotalArrangements(List<String> towelPatterns, List<String> designs) {
        return designs.stream()
                .mapToLong(design -> countArrangements(design, towelPatterns, createMemoMapLong()))
                .sum();
    }


    // Helper methods for memoization maps
    private static Map<String, Boolean> createMemoMap() {
        return new HashMap<>();
    }

    private static Map<String, Long> createMemoMapLong() {
        return new HashMap<>();
    }
}
