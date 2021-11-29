package aoc.day12;

import aoc.Day;

import java.awt.*;
import java.util.List;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;

public class Day12 implements Day {

    @Override
    public String part1(List<String> input) {
        List<Flight> flightInput = convertInput(input);
        Direction face = Direction.EAST;
        Point location = new Point(0, 0);
        for(Flight f : flightInput){
            switch(f.dir){
                case 'L':case 'R':
                {
                    int num = f.distance;
                    while(num>0){
                        face = face.turn(f.dir == 'R');
                        num-=90;
                    }
                    break;
                }
                case 'F': {
                    location = face.move(location, f.distance);
                    break;
                }
                default: {
                    location = Direction.getByDir(f.dir).move(location, f.distance);
                    break;
                }
            }
        }
        return String.valueOf(abs(location.x) + abs(location.y));
    }

    private List<Flight> convertInput(List<String> input) {
        return input.stream().map(e -> new Flight(e.charAt(0), Integer.parseInt(e.substring(1)))).collect(toList());
    }

    @Override
    public String part2(List<String> input) {
        List<Flight> flightList = convertInput(input);
        Point waypoint = new Point(10, -1);
        Point location = new Point(0, 0);
        for(Flight f : flightList){
            switch(f.dir){
                case 'L':case 'R':
                {
                    int num = f.distance;
                    while(num>0){
                        waypoint = turn(waypoint, f.dir == 'R');
                        num-=90;
                    }
                    break;
                }
                case 'F': {
                    location = new Point(location.x+(waypoint.x*f.distance), location.y+(waypoint.y*f.distance));
                    break;
                }
                default: {
                    waypoint = Direction.getByDir(f.dir).move(waypoint, f.distance);
                    break;
                }
            }
        }
        return String.valueOf(abs(location.x) + abs(location.y));
    }

    private static Point turn(Point w, boolean b) {
        return b ? new Point(-w.y, w.x) : new Point(w.y, -w.x);
    }

}
