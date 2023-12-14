package aoc.day13;

import aoc.Day2023;
import org.apache.commons.text.similarity.LevenshteinDistance;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Day13 extends Day2023 {

    public static void main(String[] args){
        new Day13().printParts();
    }

    public Day13() {
        super(13);
    }

    @Override
    public Object part1() {
        return detectMirrors(day(), 0);
    }

    @Override
    public Object part2() {
        return detectMirrors(day(), 1);
    }

    public int detectMirrors(String input, int allowedErrors) {
        var patterns = input.trim().split("\n\n");
        var sum = 0;
        var ld = LevenshteinDistance.getDefaultInstance();

        for (var pattern: patterns) {
            var lines = pattern.split("\n");
            var columnsList = new ArrayList<List<Character>>();

            for (var line: lines) {
                for (var x = 0; x < line.length(); x++) {
                    if (columnsList.size() < x + 1) {
                        columnsList.add(new ArrayList<>());
                    }
                    columnsList.get(x).add(line.charAt(x));
                }
            }

            var columns = columnsList.stream()
                    .map(column -> column.stream().map(String::valueOf).collect(Collectors.joining()))
                    .toList();

            for (var i = 0; i < lines.length - 1; i++) {
                var lDistance = 0;

                for (int j = i, k = i + 1; j >= 0 && k < lines.length && lDistance <= allowedErrors; j--, k++) {
                    lDistance += ld.apply(lines[j], lines[k]);
                }

                if (lDistance == allowedErrors) {
                    sum += 100 * (i + 1);
                    break;
                }
            }

            for (var i = 0; i < columns.size() - 1; i++) {
                var lDistance = 0;

                for (int j = i, k = i + 1; j >= 0 && k < columns.size() && lDistance <= allowedErrors; j--, k++) {
                    lDistance += ld.apply(columns.get(j), columns.get(k));
                }

                if (lDistance == allowedErrors) {
                    sum += i + 1;
                    break;
                }
            }
        }

        return sum;
    }
}
