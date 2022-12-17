package aoc.day16;

import aoc.Day;

import java.util.*;
import java.util.stream.Collectors;

import static aoc.AOCUtils.allPairs;
import static aoc.parser.ReadFormatedString.readString;
import static java.util.List.of;

public class Day16 implements Day {


    public record Valve(String name, long flow, String others) {}
    public record State(Map<String, Long> open, Valve valve, long totalFlow) {}
    public record State2(Map<String, Long> open, Valve me, Valve elephant, long totalFlow) {}

    @Override
    public String part1(List<String> input) {
        Map<String, Valve> valves = input(input);
        Set<State> states = new HashSet<>();
        states.add(new State(new HashMap<>(), valves.get("AA"), 0));
        for(int minutes = 0; minutes<30; minutes++) {
            Set<State> newStates = new HashSet<>();
            for(State s : states) {
                long flow = s.open.values().stream().mapToLong(e -> e).sum() + s.totalFlow;
                if(s.valve.flow > 0 && !s.open.containsKey(s.valve.name)) {
                    Map<String, Long> newOpen = new HashMap<>(s.open);
                    newOpen.put(s.valve.name, s.valve.flow);
                    newStates.add(new State(newOpen, s.valve, flow));
                }
                Arrays.stream(s.valve.others.split(", ")).forEach(name -> newStates.add(new State(s.open, valves.get(name), flow)));
            }
            states = newStates;
        }

        var pressure = states.stream().mapToLong(State::totalFlow).max().getAsLong();

        return String.valueOf(pressure);
    }

    private Map<String, Valve> input(List<String> input) {
        return input.stream().map(s -> {
            try {
                return readString(s, "Valve %s has flow rate=%n; tunnels lead to valves %s", Valve.class);
            } catch (IllegalStateException e) {
                return readString(s, "Valve %s has flow rate=%n; tunnel leads to valve %s", Valve.class);
            }
        }).collect(Collectors.toMap(v -> v.name, v -> v));
    }

    @Override
    public String part2(List<String> input) {
        Map<String, Valve> valves = input(input);
        Set<String> openable = valves.values().stream().filter(s -> s.flow > 0).map(Valve::name).collect(Collectors.toSet());
        Set<State2> states = new HashSet<>();
        states.add(new State2(new HashMap<>(), valves.get("AA"), valves.get("AA"), 0));
        Map<Integer, Long> kpis = Map.of(5, 25L, 10, 50L, 15, 100L, 20, 140L, 25, 160L);
        for(int minutes = 0; minutes<26; minutes++) {
            Set<State2> newStates = new HashSet<>();
            for(State2 s : states) {
                long flow = s.open.values().stream().mapToLong(e -> e).sum() + s.totalFlow;
                if(s.open.size() == openable.size()) { // All valves are open, time to chill
                    newStates.add(new State2(s.open, valves.get("AA"), valves.get("AA"), flow));
                }
                int nStates = newStates.size();
                newStates.addAll(openValve(s.me, s.elephant, false, valves, s, flow));
                newStates.addAll(openValve(s.elephant, s.me, false, valves, s, flow));
                newStates.addAll(openValve(s.me, s.elephant, true, valves, s, flow));
                if(newStates.size() == nStates) { // If there are no valves to be opened, we walk
                    allPairs(of(s.me.others.split(", ")), of(s.elephant.others.split(", ")))
                            .forEach(p -> newStates.add(new State2(s.open, valves.get(p.getA()), valves.get(p.getB()), flow)));
                }
            }
            states = newStates;
            if(kpis.containsKey(minutes)){
                long kpi = kpis.get(minutes);
                states = states.stream().filter(e -> e.open.values().stream().mapToLong(f -> f).sum()>=kpi).collect(Collectors.toSet());
            }
        }
        var pressure = states.stream().mapToLong(State2::totalFlow).max().getAsLong();
        return String.valueOf(pressure);
    }

    private List<State2> openValve(Valve v1, Valve v2, boolean both, Map<String, Valve> valves, State2 s, long flow) {
        if(v1.flow > 0 && !s.open.containsKey(v1.name) && (!both || (v2.flow > 0 && !s.open.containsKey(v2.name)))) {
            Map<String, Long> newOpen = new HashMap<>(s.open);
            newOpen.put(v1.name, v1.flow);
            if(both) {
                newOpen.put(v2.name, v2.flow);
                return of(new State2(newOpen, v1, v2, flow));
            }
            return Arrays.stream(v2.others.split(", ")).map(name -> new State2(newOpen, v1, valves.get(name), flow)).toList();
        }
        return of();
    }
}
