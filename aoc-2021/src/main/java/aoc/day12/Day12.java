package aoc.day12;

import aoc.customMap.CountMap;
import aoc.DayOld;
import com.google.common.collect.ImmutableListMultimap;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static com.google.common.collect.ImmutableListMultimap.toImmutableListMultimap;

public class Day12 implements DayOld {

    public static final String START = "start";

    @Override
    public String part1(List<String> input) {
        return String.valueOf(findPos(START, parseInput(input), Set.of(START)));
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(findPos2(START, parseInput(input), Map.of(START, 2)));
    }

    private ImmutableListMultimap<String, String> parseInput(List<String> input) {
        return input.stream()
                .map(e -> e.split("-"))
                .flatMap(e -> Stream.of(new String[]{e[0], e[1]}, new String[]{e[1], e[0]}))
                .collect(toImmutableListMultimap(e -> e[0], e -> e[1]));
    }

    private long findPos(String s, ImmutableListMultimap<String, String> in, Set<String> visited) {
        if(s.equals("end")) {
            return 1;
        }
        long n = 0;
        List<String> reachable = in.get(s);
        for(String l : reachable){
            if(!visited.contains(l)) {
                Set<String> newSet = new HashSet<>(visited);
                if(!l.toUpperCase().equals(l)) {
                    newSet.add(l);
                }
                n+=findPos(l, in, newSet);
            }
        }
        return n;
    }

    private long findPos2(String s, ImmutableListMultimap<String, String> in, Map<String, Integer> visited) {
        if(s.equals("end")) {
            return 1;
        }
        long n = 0;
        List<String> reachable = in.get(s);
        for(String l : reachable){
            if((!visited.containsKey(l) || visited.get(l) < 2) && visited.values().stream().filter(e -> e==2).count() <= 2) {
                CountMap<String> newMap = new CountMap(visited);
                if(!l.toUpperCase().equals(l)){
                    newMap.increment(l);
                }
                n+=findPos2(l, in, newMap);
            }
        }
        return n;
    }
}
