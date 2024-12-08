package aoc.day05;

import aoc.Day2024;

import java.util.*;

public class Day05 extends Day2024 {

    private final List<String> lines;

    public Day05() {
        super(5, "Print Queue");
        lines = Arrays.asList(dayStrings());
    }

    public static void main(String[] args) {
        new Day05().printParts();
    }

    @Override
    public Object part1() {
        var rules = parseRules(lines);
        var updates = parseUpdates(lines);

        // Calculate the sum of middle page numbers for correctly ordered updates
        return updates.stream()
                .filter(update -> isUpdateOrdered(update, rules))
                .mapToInt(update -> update.get(update.size() / 2))
                .sum();
    }

    @Override
    public Object part2() {
        var rules = parseRules(lines);
        var updates = parseUpdates(lines);

        // Separate updates into correctly ordered and incorrectly ordered
        int correctMiddleSum = 0;
        List<List<Integer>> incorrectUpdates = new ArrayList<>();

        for (var update : updates) {
            if (isUpdateOrdered(update, rules)) {
                correctMiddleSum += update.get(update.size() / 2);
            } else {
                incorrectUpdates.add(update);
            }
        }

        // Process incorrectly ordered updates

        return incorrectUpdates.stream()
                .map(update -> reorderUpdate(update, rules))
                .mapToInt(corrected -> corrected.get(corrected.size() / 2))
                .sum();
    }

    private static List<Integer> reorderUpdate(List<Integer> update, Map<Integer, Set<Integer>> rules) {
        // Build the graph and calculate in-degrees
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();

        for (int page : update) {
            adjList.put(page, new ArrayList<>());
            inDegree.put(page, 0);
        }

        for (var entry : rules.entrySet()) {
            int before = entry.getKey();
            for (int after : entry.getValue()) {
                if (update.contains(before) && update.contains(after)) {
                    adjList.get(before).add(after);
                    inDegree.put(after, inDegree.get(after) + 1);
                }
            }
        }

        // Topological sort (Kahn's Algorithm)
        Queue<Integer> queue = new ArrayDeque<>();
        for (var entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<Integer> sorted = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            sorted.add(current);
            for (int neighbor : adjList.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return sorted;
    }

    private static Map<Integer, Set<Integer>> parseRules(List<String> lines) {
        var rules = new HashMap<Integer, Set<Integer>>();
        for (String line : lines) {
            if (line.contains("|")) {
                String[] parts = line.split("\\|");
                int before = Integer.parseInt(parts[0].trim());
                int after = Integer.parseInt(parts[1].trim());

                rules.putIfAbsent(before, new HashSet<>());
                rules.get(before).add(after);
            }
        }
        return rules;
    }

    private static List<List<Integer>> parseUpdates(List<String> lines) {
        List<List<Integer>> updates = new ArrayList<>();
        for (String line : lines) {
            if (line.contains(",") && !line.contains("|")) {
                updates.add(Arrays.stream(line.split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .toList());
            }
        }
        return updates;
    }

    private static boolean isUpdateOrdered(List<Integer> update, Map<Integer, Set<Integer>> rules) {
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < update.size(); i++) {
            indexMap.put(update.get(i), i);
        }

        for (var entry : rules.entrySet()) {
            int before = entry.getKey();
            for (int after : entry.getValue()) {
                if (indexMap.containsKey(before) && indexMap.containsKey(after)) {
                    if (indexMap.get(before) >= indexMap.get(after)) {
                        return false; // Rule violated
                    }
                }
            }
        }
        return true;
    }
}
