package aoc.day12;

import aoc.DayOld;
import aoc.grid.NumGrid;
import aoc.parser.ParseUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12 implements DayOld {

    private char[][] dayGrid;
    private NumGrid grid;

    @Override
    public String part1(List<String> input) {
        dayGrid = ParseUtils.castInputToBiCharArray(input);
        grid =  new NumGrid(Arrays.stream(dayGrid).map(e -> new String(e).chars().mapToLong(f -> f).toArray()).toArray(long[][]::new));
        var val =  findExit(grid.find('S'), grid);
        return String.valueOf(val);
    }

    @Override
    public String part2(List<String> input) {
        var val = grid.findAll('a').filter(p -> p.y == 0).mapToLong(p -> findExit(p, grid)).min().getAsLong();
        return String.valueOf(val);
    }

    private long findExit(Point p9, NumGrid g) {
        Set<Point> visited = new HashSet<>();
        Set<Point> currentLevel = new HashSet<>();
        currentLevel.add(p9);
        visited.add(p9);

        long steps = 0;
        while(!currentLevel.isEmpty()){
            Set<Point> level = new HashSet<>(currentLevel);
            currentLevel.clear();
            for(Point p : level) {
                long current = g.get(p);
                if(current == 'S') current = 'a';
                for(Point p2 : g.streamDirs(p).toList()) {
                    if((current == 'y' || current == 'z') && g.get(p2) == 'E') return steps+1;
                    if(g.get(p2) <= current+1 && visited.add(p2)) {
                        currentLevel.add(p2);
                    }
                }
            }
            steps++;
        }
        return Long.MAX_VALUE;
    }
}
