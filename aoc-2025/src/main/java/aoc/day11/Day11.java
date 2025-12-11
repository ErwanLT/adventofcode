package aoc.day11;

import aoc.Day2025;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class Day11 extends Day2025 {

    private Map<String, List<String>> graph;
    private Map<String, Long> memoPart1;
    private Map<String, Long> memoPart2;
    private Set<String> recursionStack;

    public Day11() {
        super(11, "Reactor");
        parseInput();
    }

    private void parseInput() {
        graph = new HashMap<>();
        for (String line : dayStrings()) {
            String[] parts = line.split(": ");
            String from = parts[0];
            List<String> to = Arrays.asList(parts[1].split(" "));
            graph.put(from, to);
        }
    }

    private long countPathsToOut(String currentNode) {
        if (recursionStack.contains(currentNode)) {
            // Cycle detected
            return 0L;
        }
        if (memoPart1.containsKey(currentNode)) {
            return memoPart1.get(currentNode);
        }
        if ("out".equals(currentNode)) {
            return 1L;
        }
        if (!graph.containsKey(currentNode)) {
            // Dead end
            return 0L;
        }

        recursionStack.add(currentNode);

        long pathCount = 0;
        for (String neighbor : graph.get(currentNode)) {
            pathCount += countPathsToOut(neighbor);
        }

        recursionStack.remove(currentNode);
        memoPart1.put(currentNode, pathCount);

        return pathCount;
    }

    private long countPaths(String from, String to, Set<String> forbidden) {
        if (recursionStack.contains(from)) {
            return 0L;
        }

        if (from.equals(to)) {
            return 1L;
        }
        
        StringJoiner sj = new StringJoiner(",");
        forbidden.stream().sorted().forEach(sj::add);
        String memoKey = from + "->" + to + ":" + sj.toString();
        if (memoPart2.containsKey(memoKey)) {
            return memoPart2.get(memoKey);
        }

        if (!graph.containsKey(from)) {
            return 0L;
        }

        recursionStack.add(from);

        long pathCount = 0;
        for (String neighbor : graph.get(from)) {
            if (neighbor.equals(to) || !forbidden.contains(neighbor)) {
                pathCount += countPaths(neighbor, to, forbidden);
            }
        }

        recursionStack.remove(from);
        memoPart2.put(memoKey, pathCount);

        return pathCount;
    }

    @Override
    public Object part1() {
        this.memoPart1 = new HashMap<>();
        this.recursionStack = new HashSet<>();
        return countPathsToOut("you");
    }

    @Override
    public Object part2() {
        memoPart2 = new HashMap<>();

        // Case 1: svr -> ... -> dac -> ... -> fft -> ... -> out
        recursionStack = new HashSet<>();
        long svrToDac = countPaths("svr", "dac", Set.of("fft", "out"));
        recursionStack = new HashSet<>();
        long dacToFft = countPaths("dac", "fft", Set.of("out"));
        recursionStack = new HashSet<>();
        long fftToOut = countPaths("fft", "out", Collections.emptySet());
        long dacFirst = svrToDac * dacToFft * fftToOut;

        // Case 2: svr -> ... -> fft -> ... -> dac -> ... -> out
        recursionStack = new HashSet<>();
        long svrToFft = countPaths("svr", "fft", Set.of("dac", "out"));
        recursionStack = new HashSet<>();
        long fftToDac = countPaths("fft", "dac", Set.of("out"));
        recursionStack = new HashSet<>();
        long dacToOut = countPaths("dac", "out", Collections.emptySet());
        long fftFirst = svrToFft * fftToDac * dacToOut;

        return dacFirst + fftFirst;
    }
}
