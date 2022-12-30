package aoc.day19;

import aoc.DayOld;

import java.util.*;
import java.util.regex.Pattern;


public class Day19 implements DayOld {

    @Override
    public String part1(List<String> input) {
        var blueprints = getBlueprints(input);
        var qualityLevel = 0;

        for (var blueprint: blueprints) {
            qualityLevel += blueprint.id * findMaxGeodeForBlueprint(blueprint, 24);
        }

        return String.valueOf(qualityLevel);
    }

    private int findMaxGeodeForBlueprint(Blueprint blueprint, int minutes) {
        var maxGeode = 0;
        var bestMinutePerRobotDistribution = new HashMap<RobotsCount, Integer>();
        var bestGeodePerMinute = new HashMap<Integer, Integer>();
        var queue = new PriorityQueue<State>((a, b) -> b.minutes - a.minutes);
        queue.add(new State(0, new Inventory(0, 0, 0, 0), new RobotsCount(1, 0, 0, 0)));

        while (!queue.isEmpty()) {
            var state = queue.poll();

            if (state.minutes == minutes) {
                if (state.inventory.geode > maxGeode) {
                    maxGeode = state.inventory.geode;
                }
                continue;
            }

            for (Type type: Type.values()) {
                var newRobots = new RobotsCount(state.robots, type);
                var cost = blueprint.robotCost.get(type);

                if (
                        newRobots.ore > blueprint.max.ore
                                || newRobots.clay > blueprint.max.clay
                                || newRobots.obsidian > blueprint.max.obsidian
                                || (state.robots.clay == 0 && cost.clay > 0)
                                || (state.robots.obsidian == 0 && cost.obsidian > 0)
                ) {
                    continue;
                }
                var neededMinutes = (int) Math.ceil(Math.max(0, cost.ore - state.inventory.ore) / (double) state.robots.ore);

                if (state.robots.clay > 0 && cost.clay > 0) {
                    neededMinutes = Math.max(
                            neededMinutes,
                            (int) Math.ceil(Math.max(0, cost.clay - state.inventory.clay) / (double) state.robots.clay)
                    );
                }

                if (state.robots.obsidian > 0 && cost.obsidian > 0) {
                    neededMinutes = Math.max(
                            neededMinutes,
                            (int) Math.ceil(Math.max(0, cost.obsidian - state.inventory.obsidian) / (double) state.robots.obsidian)
                    );
                }

                neededMinutes++;

                if (state.minutes + neededMinutes > minutes) {
                    continue;
                }

                var newInventory = new Inventory(
                        state.inventory.ore - cost.ore + (state.robots.ore * neededMinutes),
                        state.inventory.clay - cost.clay + (state.robots.clay * neededMinutes),
                        state.inventory.obsidian - cost.obsidian + (state.robots.obsidian * neededMinutes),
                        state.inventory.geode + (state.robots.geode * neededMinutes)
                );

                var newState = new State(state.minutes + neededMinutes, newInventory, newRobots);

                if (
                        (
                                bestGeodePerMinute.containsKey(newState.minutes)
                                        && bestGeodePerMinute.get(newState.minutes) > newState.robots.geode
                        ) || (
                                bestMinutePerRobotDistribution.containsKey(newState.robots)
                                        && bestMinutePerRobotDistribution.get(newState.robots) < newState.minutes
                        )
                ) {
                    continue;
                }

                bestGeodePerMinute.put(newState.minutes, newState.robots.geode);
                bestMinutePerRobotDistribution.put(newState.robots, newState.minutes);

                queue.add(newState);
            }

            var totalGeode = state.inventory.geode + state.robots.geode * (minutes - state.minutes);

            if (totalGeode > maxGeode) {
                maxGeode = totalGeode;
            }
        }

        return maxGeode;
    }

    private List<Blueprint> getBlueprints(List<String> input) {
        var blueprints = new ArrayList<Blueprint>();

        for (var blueprint : input) {
            var inputNum = extractIntegersFromString(blueprint);
            blueprints.add(new Blueprint(
                    inputNum[0],
                    Map.of(
                            Type.ORE, new Cost(inputNum[1], 0, 0, 0),
                            Type.CLAY, new Cost(inputNum[2], 0, 0, 0),
                            Type.OBSIDIAN, new Cost(inputNum[3], inputNum[4], 0, 0),
                            Type.GEODE, new Cost(inputNum[5], 0, inputNum[6], 0)
                    )
            ));
        }

        return blueprints;
    }

    public static Integer[] extractIntegersFromString(String s) {
        var list = new LinkedList<Integer>();

        var p = Pattern.compile("-?\\d+");
        var m = p.matcher(s);

        while (m.find()) {
            list.add(Integer.parseInt(m.group()));
        }

        return list.toArray(new Integer[0]);
    }

    @Override
    public String part2(List<String> input) {
        var blueprints = getBlueprints(input);

        if (blueprints.size() > 2) {
            blueprints = blueprints.subList(0, 3);
        }

        var result = 1;

        for (var blueprint: blueprints) {
            result *= findMaxGeodeForBlueprint(blueprint, 32);
        }

        return String.valueOf(result);
    }

}
