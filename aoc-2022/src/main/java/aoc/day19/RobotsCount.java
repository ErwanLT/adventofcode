package aoc.day19;

public class RobotsCount {
    public int ore;
    public int clay;
    public int obsidian;
    public int geode;

    RobotsCount(int ore, int clay, int obsidian, int geode) {
        this.ore = ore;
        this.clay = clay;
        this.obsidian = obsidian;
        this.geode = geode;
    }

    public RobotsCount(RobotsCount base, Type type) {
        this(
                base.ore + (type == Type.ORE ? 1 : 0),
                base.clay + (type == Type.CLAY ? 1 : 0),
                base.obsidian + (type == Type.OBSIDIAN ? 1 : 0),
                base.geode + (type == Type.GEODE ? 1 : 0)
        );
    }
}
