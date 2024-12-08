package aoc.day01;

import aoc.Day2024;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static aoc.parser.ReadFormatedString.readString;

public class Day01 extends Day2024 {

    public Day01() {
        super(1, "Historian Hysteria");
    }

    public record Pair(int a, int b) {}

    public static void main(String[] args) {
        new Day01().printParts();
    }

    @Override
    public Object part1() {
        List<Pair> input = parseInput();

        List<Integer> leftList = input.stream().map(Pair::a).sorted().toList();
        List<Integer> rightList = input.stream().map(Pair::b).sorted().toList();

        return calculateTotalDistance(leftList, rightList);
    }

    @Override
    public Object part2() {
        List<Pair> input = parseInput();

        List<Integer> leftList = input.stream().map(Pair::a).toList();
        List<Integer> rightList = input.stream().map(Pair::b).toList();

        return calculateSimilarityScore(leftList, rightList);
    }

    private List<Pair> parseInput() {
        return dayStream().filter(s -> !s.isBlank()).map(s -> readString(s, "%i   %i", Pair.class)).toList();
    }

    private int calculateTotalDistance(List<Integer> leftList, List<Integer> rightList) {
        int totalDistance = 0;
        for (int i = 0; i < leftList.size(); i++) {
            totalDistance += Math.abs(leftList.get(i) - rightList.get(i));
        }
        return totalDistance;
    }

    private int calculateSimilarityScore(List<Integer> leftList, List<Integer> rightList) {
        Map<Integer, Long> rightCountMap = rightList.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return leftList.stream()
                .mapToInt(num -> num * rightCountMap.getOrDefault(num, 0L).intValue())
                .sum();
    }
}
