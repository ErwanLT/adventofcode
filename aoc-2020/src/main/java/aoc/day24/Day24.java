package aoc.day24;

import aoc.DayOld;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Day24 implements DayOld {

    private static Set<Point> visitedTiles = new HashSet<>();

    @Override
    public String part1(List<String> input) {
        List<List<HexDirection>> inputHex = input.stream().map(s -> read(s)).collect(toList());
        for(List<HexDirection> dirs : inputHex){
            Point pos = new Point(0,0);
            for(HexDirection dir : dirs){
                pos = dir.move(pos);
            }
            if(!visitedTiles.add(pos)){
                visitedTiles.remove(pos);
            }
        }

        return String.valueOf(visitedTiles.size());
    }

    @Override
    public String part2(List<String> input) {
        for(int i = 0; i<100; i++){
            Set<Point> newPos = new HashSet<>();
            visitedTiles.forEach(p -> addNeighbors(visitedTiles, newPos, new HashSet<>(), p, true));
            visitedTiles = newPos;
        }

        return String.valueOf(visitedTiles.size());
    }

    private static void addNeighbors(Set<Point> pos, Set<Point> newPos, Set<Point> checkedPos, Point p, boolean active) {
        if (!checkedPos.contains(p)) {
            int neighbours = 0;
            checkedPos.add(p);
            for (HexDirection dir : HexDirection.values()) {
                Point x = dir.move(p);
                if (pos.contains(x)) {
                    neighbours++;
                } else if (active) {
                    addNeighbors(pos, newPos, checkedPos, x, false);
                }
            }
            if ((active && (neighbours == 1 || neighbours == 2)) ||
                    (!active && neighbours == 2)) {
                newPos.add(p);
            }
        }
    }

    private static List<HexDirection> read(String dirs){
        List<HexDirection> res = new ArrayList<>(dirs.length());
        while(dirs.length()>0){
            Optional<HexDirection> direction;
            if(dirs.length()>1 && (direction = HexDirection.get(dirs.substring(0,2))).isPresent()){
                res.add(direction.get());
                dirs = dirs.substring(2);
            } else if ((direction = HexDirection.get(dirs.substring(0,1))).isPresent()){
                res.add(direction.get());
                dirs = dirs.substring(1);
            }
        }
        return res;
    }
}
