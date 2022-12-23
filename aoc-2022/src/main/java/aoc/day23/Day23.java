package aoc.day23;

import aoc.Day;
import aoc.Direction;
import aoc.Pair;
import aoc.grid.InfiniteGrid;
import aoc.location.Loc;
import aoc.parser.ParseUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static aoc.Direction.*;

public class Day23 implements Day {
    @Override
    public String part1(List<String> input) {
        var sol = solution(input, false);
        return String.valueOf(sol);
    }

    @Override
    public String part2(List<String> input) {
        var sol = solution(input, true);
        return String.valueOf(sol);
    }

    public long solution(List<String> input, boolean isPart2) {
        InfiniteGrid g = new InfiniteGrid(dayGrid(input));
        List<Direction> dirs = new ArrayList<>(List.of(NORTH, SOUTH, WEST, EAST));
        for(int i = 0; isPart2 || i<10; i++) {
            InfiniteGrid newGrid = new InfiniteGrid();
            InfiniteGrid finalG = g;
            Map<Loc, Loc> dest = g.grid.keySet().stream()
                    .filter(e -> Arrays.stream(Direction.eightDirections()).anyMatch(d -> finalG.grid.containsKey(d.move(e))))
                    .flatMap(e -> dirs.stream().filter(d -> !finalG.grid.containsKey(d.move(e)) && !finalG.grid.containsKey(d.turn().move(d.move(e)))  && !finalG.grid.containsKey(d.turn(false).move(d.move(e)))).map(d -> new Pair<>(e, d.move(e))).limit(1))
                    .collect(Collectors.toMap(Pair::a, Pair::b));
            dest.entrySet().stream()
                    .map(e -> dest.values().stream().filter(l -> l.equals(e.getValue())).limit(2).count() == 1 ? e.getValue() : e.getKey())
                    .forEach(e -> newGrid.set(e, '#'));
            g.grid.keySet().stream()
                    .filter(e -> !dest.containsKey(e))
                    .forEach(e -> newGrid.set(e, '#'));
            dirs.add(dirs.remove(0));
            if(newGrid.grid.keySet().equals(g.grid.keySet())){
                return i+1;
            }
            g = newGrid;
        }
        return g.toString().chars().filter(c -> c == ' ').count();
    }

    private char[][] dayGrid(List<String> input) {
        return ParseUtils.castInputToBiCharArray(input);
    }
}
