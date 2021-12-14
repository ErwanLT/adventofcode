package aoc.day14;

import aoc.Day;
import aoc.customMap.LongCountMap;
import aoc.parser.ParseUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day14 implements Day {

    private static String polymerTemplate;
    private static LongCountMap<String> pairs;
    private static Map<String, String> strs;

    @Override
    public String part1(List<String> input) {
        castInput(input);
        return String.valueOf(simulateSteps(10));
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(simulateSteps(40));
    }

    private void castInput(List<String> input) {
        String[] in = ParseUtils.castInputToString("\n", input).split("\n\n");
        polymerTemplate = in[0];
        strs = Arrays.stream(in[1].split("\n")).map(e -> e.split(" -> ")).collect(Collectors.toMap(e -> e[0], e -> e[1]));
    }


    private long simulateSteps(int steps) {
        pairs = new LongCountMap<>();
        for(int i = 0; i<polymerTemplate.length()-1; i++) {
            pairs.increment(polymerTemplate.substring(i, i+2));
        }
        LongCountMap<Character> chCounts = new LongCountMap<>();
        polymerTemplate.chars().forEach(e -> chCounts.increment((char)e));
        for(int step = 1; step <= steps; step++){
            LongCountMap<String> newPairs = new LongCountMap<>();
            for(String pair : pairs.keySet()){
                long increment = pairs.get(pair);
                if(strs.containsKey(pair)) {
                    String key = strs.get(pair);
                    String n1 = pair.charAt(0) + key;
                    String n2 = key + pair.charAt(1);
                    newPairs.increment(n1, increment);
                    newPairs.increment(n2, increment);
                    chCounts.increment(key.charAt(0), increment);
                } else {
                    newPairs.increment(pair, increment);
                }
            }
            pairs = newPairs;
        }
        return chCounts.values().stream().mapToLong(e -> e).max().getAsLong() - chCounts.values().stream().mapToLong(e -> e).min().getAsLong();
    }
}
