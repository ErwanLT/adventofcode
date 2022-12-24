package aoc.day19;

import aoc.Day;
import aoc.TopUniqueElements;
import aoc.customMap.LongCountMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static aoc.parser.ReadFormatedString.readString;
import static java.util.Comparator.comparing;


public class Day19 implements Day {

    public record Blueprint(long n, long oreCost, long clayCost, long obsidianOre, long obsidianClay, long geodeOre, long geodeObsidian){}
    public record State(LongCountMap<String> inventory, LongCountMap<String> perTurn, String target) {}
    @Override
    public String part1(List<String> input) {
        List<Blueprint> blueprints = input.stream().map(s -> readString(s, "Blueprint %n: Each ore robot costs %n ore. Each clay robot costs %n ore. Each obsidian robot costs %n ore and %n clay. Each geode robot costs %n ore and %n obsidian.", " ", Blueprint.class)).toList();
        long quality = getQuality(blueprints, 24, 100000);
        return String.valueOf(quality);
    }

    private long getQuality(List<Blueprint> blueprints, int minutes, int capacity) {
        long totalQuality = 0;
        Collection<State> states = getStates(new LongCountMap<>(), new LongCountMap<>(), "ore", "clay")
                .peek(s -> s.perTurn.increment("ore"))
                .toList();

        for(Blueprint b: blueprints){
            System.out.println("Blueprint " + b.n);
            System.out.println("-----------------------------");
            for (int i = 0; i < minutes; i++) {
                TopUniqueElements<State> newStates = new TopUniqueElements<>(capacity, comparing(state -> state.inventory.sumValues()));
                for (State s : states){
                    LongCountMap<String> perTurn = new LongCountMap<>(s.perTurn);
                    boolean buildGeode = s.inventory.get("ore") >= b.geodeOre && s.inventory.get("obsidian") >= b.geodeObsidian;
                    boolean buildObsidian = s.inventory.get("ore") >= b.obsidianOre && s.inventory.get("clay") >= b.obsidianClay;
                    boolean buildOre = s.target.equals("ore") && s.inventory.get("ore") >= b.oreCost;
                    boolean buildClay = s.target.equals("clay") && s.inventory.get("ore") >= b.clayCost;
                    perTurn.forEach(s.inventory::increment);

                    if (!buildGeode && !buildObsidian && buildOre) {
                        //System.out.println("build ore robot");
                        s.inventory.increment("ore", -b.oreCost);
                        s.perTurn.increment("ore");
                        getStates(s.inventory, s.perTurn, "ore", "clay").forEach(newStates::add);
                    } else if (!buildGeode && !buildObsidian && buildClay) {
                        //System.out.println("build clay robot");
                        s.inventory.increment("ore", -b.clayCost);
                        s.perTurn.increment("clay");
                        getStates(s.inventory, s.perTurn, "ore", "clay", "obsidian").forEach(newStates::add);
                    } else if (!buildGeode && buildObsidian) {
                        //System.out.println("build obsidian robot");
                        s.inventory.increment("ore", -b.obsidianOre);
                        s.inventory.increment("clay", -b.obsidianClay);
                        s.perTurn.increment("obsidian");
                        getStates(s.inventory, s.perTurn, "ore", "clay", "obsidian", "geode").forEach(newStates::add);
                    } else if (buildGeode) {
                        //System.out.println("build geode robot");
                        s.inventory.increment("ore", -b.geodeOre);
                        s.inventory.increment("obsidian", -b.geodeObsidian);
                        s.perTurn.increment("geode");
                        getStates(s.inventory, s.perTurn, "ore", "clay", "obsidian", "geode").forEach(newStates::add);
                    } else {
                        newStates.add(s);
                    }
                }
                states = newStates;
            }
            var quality = b.n * states.stream().mapToLong(e -> e.inventory.get("geode")).max().orElse(0L);
            System.out.println("the blueprint quality is "+ quality);
            totalQuality += quality;

        }
        return totalQuality;
    }

    private static Stream<State> getStates(LongCountMap<String> inventory, LongCountMap<String> perTurn, String...ores) {
        return Arrays.stream(ores).map(ore -> new State(new LongCountMap<>(inventory), new LongCountMap<>(perTurn), ore));
    }

    @Override
    public String part2(List<String> input) {
        return null;
    }

}
