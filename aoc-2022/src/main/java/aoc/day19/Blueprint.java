package aoc.day19;

import java.util.Map;

public class Blueprint {
    public int id;
    public Map<Type, Cost> robotCost;
    public Cost max;

    Blueprint(int id, Map<Type, Cost> robotCost) {
        this.id = id;
        this.robotCost = robotCost;
        this.max = new Cost(0, 0, 0, 0);

        for (var cost: robotCost.values()) {
            this.max.ore = Math.max(cost.ore, this.max.ore);
            this.max.clay = Math.max(cost.clay, this.max.clay);
            this.max.obsidian = Math.max(cost.obsidian, this.max.obsidian);
        }
    }
}
