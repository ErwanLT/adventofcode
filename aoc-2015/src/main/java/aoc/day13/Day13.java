package aoc.day13;

import aoc.Day;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day13 implements Day {

    private static final String LOVES = "gain";
    private static final String HATES = "lose";
    private static final Pattern LOVE_PATTERN = Pattern
            .compile("(\\S+) would (lose|gain) (\\d+) happiness units by sitting next to (.+)\\.");
    private static Arrengement table;
    private static LoveGraph loveGraph;

    @Override
    public String part1(List<String> input) {
        loveGraph = readLoveGraph(input);
        table = bestArrengement(loveGraph);
        return String.valueOf(table.loveScore(loveGraph));
    }

    @Override
    public String part2(List<String> input) {
        loveGraph.extend("ME", 0);
        Arrengement bestWithMe = insertBestPosition(table, "ME", loveGraph);
        return String.valueOf(bestWithMe.loveScore(loveGraph));
    }

    private static LoveGraph readLoveGraph(List<String> input) {
        LoveGraph loveGraph = new LoveGraph();
        input.forEach(line -> {
            var m = LOVE_PATTERN.matcher(line);
            if (!m.matches()) {
                throw new IllegalArgumentException("can not match: \"" + line + "\"");
            }
            String lover = m.group(1);
            String loved = m.group(4);
            int love = calculateLove(m.group(3), m.group(2));
            loveGraph.computeIfAbsent(lover, k -> new HashMap<>()).put(loved, love);
        });
        return loveGraph;
    }

    private static int calculateLove(String love, String relationship) {
        switch (relationship) {
            case LOVES :
                return Integer.parseInt(love);
            case HATES :
                return -Integer.parseInt(love);
            default :
                throw new IllegalArgumentException("unkown relationship: " + relationship);
        }
    }

    private static Arrengement bestArrengement(LoveGraph graph) {
        var names = new ArrayList<>(graph.keySet());
        Arrengement current = new Arrengement(names.get(0));
        for (int i = 1; i < names.size(); ++i) {
            current = findBestPartner(current, graph);
        }
        return current;
    }

    private static Arrengement findBestPartner(Arrengement start, LoveGraph graph) {
        return graph.keySet().stream().filter(k -> !start.sitters.contains(k))
                .map(k -> insertBestPosition(start, k, graph)).max(Comparator.comparingInt(a -> a.loveScore(graph)))
                .orElseThrow();
    }

    private static Arrengement insertBestPosition(Arrengement start, String name, LoveGraph graph) {
        return IntStream.range(0, start.size()).mapToObj(i -> new Arrengement(start, name, i))
                .max(Comparator.comparingInt(a -> a.loveScore(graph))).orElseThrow();
    }
}
