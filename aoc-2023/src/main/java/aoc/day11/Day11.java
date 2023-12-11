package aoc.day11;

import aoc.Day2023;
import aoc.Pair;
import aoc.grid.InfiniteGrid;
import aoc.location.Loc;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.LongStream.rangeClosed;

import static aoc.AOCUtils.allPairs;

public class Day11 extends Day2023 {

    public static void main(String[] args){
        new Day11().printParts();
    }

    public Day11() {
        super(11);
    }

    @Override
    public Object part1() {
        var grid = new InfiniteGrid(dayGrid());
        Set<Long> emptyRows = countEmpty(grid.rows(), grid);
        Set<Long> emptyCols = countEmpty(grid.columns(), grid);
        return allPairs(grid.findAll('#').toList())
                .mapToLong(p -> p.a().distance(p.b()) + countEmpty(p, Loc::getX, emptyCols, true) + countEmpty(p, Loc::getY, emptyRows, true))
                .sum();
    }

    private static Set<Long> countEmpty(Map<Long, List<Loc>> rowsOrCols, InfiniteGrid grid) {
        return rowsOrCols.entrySet().stream().filter(e -> e.getValue().stream().allMatch(c -> grid.getOptimistic(c) == '.')).map(Map.Entry::getKey).collect(toSet());
    }

    private long countEmpty(Pair<Loc, Loc> p, Function<Loc, Long> func, Set<Long> emptyRows, boolean part1) {
        long coord1 = func.apply(p.a());
        long coord2 = func.apply(p.b());
        return rangeClosed(min(coord1, coord2), max(coord1, coord2)).filter(emptyRows::contains).count() * (part1 ? 1 : (1000000 - 1));
    }

    @Override
    public Object part2() {
        var grid = new InfiniteGrid(dayGrid());
        Set<Long> emptyRows = countEmpty(grid.rows(), grid);
        Set<Long> emptyCols = countEmpty(grid.columns(), grid);
        return allPairs(grid.findAll('#').toList())
                .mapToLong(p -> p.a().distance(p.b()) + countEmpty(p, Loc::getX, emptyCols, false) + countEmpty(p, Loc::getY, emptyRows, false))
                .sum();
    }
}
