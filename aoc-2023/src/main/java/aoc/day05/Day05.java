package aoc.day05;

import aoc.Day2023;
import aoc.ListUtils;
import com.google.common.collect.Range;

import java.util.ArrayList;
import java.util.List;

public class Day05 extends Day2023 {

    private String[] categories;
    private List<Long> seeds;

    public Day05() {
        super(5);
    }

    public static void main(String[] args){
        new Day05().printParts();
    }

    @Override
    public Object part1() {
        categories = day().trim().split("\n\n");
        seeds = ListUtils.extractUnsignedLongs(categories[0]);
        var categoriesMaps = new ArrayList<List<ValueMap>>();

        for (var i = 1; i < categories.length; i++) {
            var lines = categories[i].split("\n");
            var categoryMaps = new ArrayList<ValueMap>();

            for (var j = 1; j < lines.length; j++) {
                var parts = ListUtils.extractUnsignedLongs(lines[j]);
                categoryMaps.add(new ValueMap(parts.get(0), parts.get(1), parts.get(2)));
            }

            categoriesMaps.add(categoryMaps);
        }

        var min = Long.MAX_VALUE;

        for (var seed: seeds) {
            var value = seed;

            for (var categoryMaps: categoriesMaps) {
                for (var map: categoryMaps) {
                    if (value >= map.source() && value <= map.source() + map.count()) {
                        value = map.destination() + (value - map.source());
                        break;
                    }
                }
            }

            if (value < min) {
                min = value;
            }
        }

        return String.valueOf(min);
    }

    @Override
    public Object part2() {
        var categoriesMaps = new ArrayList<List<ValueMap>>();

        for (var i = 1; i < categories.length; i++) {
            var lines = categories[i].split("\n");
            var categoryMaps = new ArrayList<ValueMap>();

            for (var j = 1; j < lines.length; j++) {
                var parts = ListUtils.extractUnsignedLongs(lines[j]);
                categoryMaps.add(new ValueMap(parts.get(0), parts.get(1), parts.get(2)));
            }

            categoriesMaps.add(categoryMaps);
        }

        var min = Long.MAX_VALUE;

        for (var i = 0; i < seeds.size(); i += 2) {
            var seedRange = Range.closed(seeds.get(i), seeds.get(i) + seeds.get(i + 1) - 1);

            var valueRanges = List.of(seedRange);

            for (var categoryMaps: categoriesMaps) {
                valueRanges = applyCategoryMaps(valueRanges, categoryMaps);
            }

            for (var valueRange: valueRanges) {
                if (valueRange.lowerEndpoint() < min) {
                    min = valueRange.lowerEndpoint();
                }
            }
        }

        return String.valueOf(min);
    }

    private ArrayList<Range<Long>> applyCategoryMaps(List<Range<Long>> ranges, List<ValueMap> categoryMaps) {
        var unmappedRanges = List.copyOf(ranges);
        var mappedRanges = new ArrayList<Range<Long>>();

        for (var map: categoryMaps) {
            var newUnmappedRanges = new ArrayList<Range<Long>>();

            for (var unmappedRange: unmappedRanges) {
                var mapRange = Range.closed(map.source(), map.source() + map.count() - 1);

                if (!unmappedRange.isConnected(mapRange)) {
                    newUnmappedRanges.add(unmappedRange);
                    continue;
                }

                var overlapping = unmappedRange.intersection(mapRange);

                // Overlapping range is mapped to the destination range.
                mappedRanges.add(Range.closed(
                        map.destination() + (overlapping.lowerEndpoint() - map.source()),
                        map.destination() + (overlapping.upperEndpoint() - map.source())
                ));

                // If there is a range before the overlapping range, add it to the unmapped ranges.
                if (unmappedRange.lowerEndpoint() < overlapping.lowerEndpoint()) {
                    newUnmappedRanges.add(Range.closed(unmappedRange.lowerEndpoint(), overlapping.lowerEndpoint() - 1));
                }

                // If there is a range after the overlapping range, add it to the unmapped ranges.
                if (unmappedRange.upperEndpoint() > overlapping.upperEndpoint()) {
                    newUnmappedRanges.add(Range.closed(overlapping.upperEndpoint() + 1, unmappedRange.upperEndpoint()));
                }
            }

            unmappedRanges = newUnmappedRanges;
        }

        mappedRanges.addAll(unmappedRanges);

        return mappedRanges;
    }

}
