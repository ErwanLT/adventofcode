package aoc.day19;

public class State {
    public int minutes;
    public Inventory inventory;
    public RobotsCount robots;

    State(int minutes, Inventory inventory, RobotsCount robots) {
        this.minutes = minutes;
        this.inventory = inventory;
        this.robots = robots;
    }
}
