package aoc.day12;

import java.awt.*;
import java.util.Arrays;

public enum Direction {

    NORTH(1, 'U'), EAST(4, 'R'), SOUTH(2, 'D'), WEST(3, 'L'),
    NORTHEAST(4, 'E'), EASTSOUTH(5, 'E'), SOUTHWEST(6, 'E'), WESTNORTH(7, 'E');

    public final int num;
    public final int code;

    Direction(int num, char code) {
        this.num = num;
        this.code = code;
    }

    public static Direction getByDir(char code) {
        return Arrays.stream(values()).filter(e -> e.name().charAt(0) == code).findAny().get();
    }

    public Direction turn(boolean right) {
        int cur = ordinal() + (right ? 1 : -1);
        if(cur == Direction.fourDirections().length) cur = 0;
        else if(cur == -1) cur = 3;
        return Direction.fourDirections()[cur];
    }

    public Point move(Point currentLocation, int amount) {
        switch (this) {
            case SOUTH: return new Point(currentLocation.x, currentLocation.y+amount);
            case NORTH: return new Point(currentLocation.x, currentLocation.y-amount);
            case EAST: return new Point(currentLocation.x+amount, currentLocation.y);
            case WEST: return new Point(currentLocation.x-amount, currentLocation.y);
            case SOUTHWEST: return new Point(currentLocation.x-amount, currentLocation.y+amount);
            case NORTHEAST: return new Point(currentLocation.x+amount, currentLocation.y-amount);
            case EASTSOUTH: return new Point(currentLocation.x+amount, currentLocation.y+amount);
            case WESTNORTH: return new Point(currentLocation.x-amount, currentLocation.y-amount);
        }
        throw new IllegalStateException("Non-existent Direction: "+this);
    }

    public static Direction[] fourDirections(){
        return new Direction[]{NORTH, EAST, SOUTH, WEST};
    }
}
