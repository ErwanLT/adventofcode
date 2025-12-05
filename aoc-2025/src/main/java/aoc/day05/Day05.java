package aoc.day05;

import aoc.Day2025;
import aoc.Range;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day05 extends Day2025 {

    private final List<Range> freshRanges = new ArrayList<>();
    private final List<Long> availableIds = new ArrayList<>();

    public Day05() {
        super(5, "Cafeteria");
        parseInput();
    }

    private void parseInput() {
        String[] input = dayStrings();
        boolean parsingRanges = true;
        for (String line : input) {
            if (line.isBlank()) {
                parsingRanges = false;
                continue;
            }

            if (parsingRanges) {
                String[] parts = line.split("-");
                freshRanges.add(new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
            } else {
                // Safeguard against empty lines if dayStrings() produces them
                availableIds.add(Long.parseLong(line));
            }
        }
    }

    @Override
    public Object part1() {
        Set<Long> foundFreshIds = new HashSet<>();
        for (long id : availableIds) {
            for (Range range : freshRanges) {
                if (range.containsInclusif(id)) {
                    foundFreshIds.add(id);
                    break;
                }
            }
        }
        return (long) foundFreshIds.size();
    }

    @Override
    public Object part2() {
        List<Range> mergedRanges = mergeRanges(freshRanges);

        long totalFreshIds = 0L;
        for (Range range : mergedRanges) {
            totalFreshIds += range.length();
        }

        return totalFreshIds;
    }

    private static List<Range> mergeRanges(List<Range> inputRanges) {
        if (inputRanges.isEmpty()) {
            return new ArrayList<>();
        }

        // Create a mutable copy to sort
        List<Range> ranges = new ArrayList<>(inputRanges);
        ranges.sort(Comparator.comparingLong(Range::min));

        List<Range> merged = new ArrayList<>();
        Range currentMerge = ranges.getFirst();

        for (int i = 1; i < ranges.size(); i++) {
            Range nextRange = ranges.get(i);
            if (nextRange.min() <= currentMerge.max() + 1) {
                currentMerge = new Range(currentMerge.min(), Math.max(currentMerge.max(), nextRange.max()));
            } else {
                merged.add(currentMerge);
                currentMerge = nextRange;
            }
        }
        merged.add(currentMerge);
        return merged;
    }
}
