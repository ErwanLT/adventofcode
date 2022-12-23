package aoc.day19;

import aoc.Day;
import aoc.customMap.LongCountMap;

import java.util.List;

import static aoc.parser.ReadFormatedString.readString;


public class Day19 implements Day {

    public record Blueprint(long n, long oreCost, long clayCost, long obsidianOre, long obsidianClay, long geodeOre, long geodeObsidian){}

    @Override
    public String part1(List<String> input) {
        List<Blueprint> blueprints = input.stream().map(s -> readString(s, "Blueprint %n: Each ore robot costs %n ore. Each clay robot costs %n ore. Each obsidian robot costs %n ore and %n clay. Each geode robot costs %n ore and %n obsidian.", " ", Blueprint.class)).toList();
        long quality = getQuality(blueprints, 24);
        return String.valueOf(quality);
    }

    private long getQuality(List<Blueprint> blueprints, int minutes) {
        long totalQuality = 0;
        for(Blueprint b: blueprints){
            LongCountMap<String> inventory = new LongCountMap<>();
            inventory.put("ore", 0L);
            inventory.put("clay", 0L);
            inventory.put("obsidian", 0L);
            inventory.put("geode", 0L);
            int oreRobot = 1;
            int clayRobot = 0;
            int obsidianRobot = 0;
            int geodeRobot = 0;
            for (int i = 0; i < minutes; i++) {
                boolean buildGeode = inventory.get("ore") >= b.geodeOre && inventory.get("obsidian") >= b.geodeObsidian;
                boolean buildObsidian = inventory.get("ore") >= b.obsidianOre && inventory.get("clay") >= b.obsidianClay;
                boolean buildOre = inventory.get("ore") >= b.oreCost;
                boolean buildClay = inventory.get("ore") >= b.clayCost;

                if (!buildGeode && !buildObsidian && buildOre) {
                    inventory.increment("ore", -b.oreCost);
                    oreRobot++;
                } else if (!buildGeode && !buildObsidian && buildClay) {
                    inventory.increment("clay", -b.clayCost);
                    clayRobot++;
                } else if (!buildGeode && buildObsidian) {
                    inventory.increment("ore", -b.obsidianOre);
                    inventory.increment("clay", -b.obsidianClay);
                    obsidianRobot++;
                } else if (buildGeode) {
                    inventory.increment("ore", -b.geodeOre);
                    inventory.increment("obsidian", -b.geodeObsidian);
                    geodeRobot++;
                }

                inventory.increment("ore", oreRobot);
                inventory.increment("clay", clayRobot);
                inventory.increment("obsidian", obsidianRobot);
                inventory.increment("geode", geodeRobot);

            }
            totalQuality += b.n * inventory.get("geode");
        }
        return totalQuality;
    }

    @Override
    public String part2(List<String> input) {
        return null;
    }

}
