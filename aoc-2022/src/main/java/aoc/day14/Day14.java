package aoc.day14;

import aoc.AOCUtils;
import aoc.Day;
import aoc.grid.InfiniteGrid;
import aoc.location.Loc;
import aoc.location.MutableLoc;
import aoc.location.Range;
import aoc.parser.ParseUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static aoc.Direction.*;
import static aoc.grid.InfiniteGrid.toInfiniteGrid;
import static aoc.parser.ReadFormatedString.readString;

public class Day14 implements Day {
    @Override
    public String part1(List<String> input) {
        return String.valueOf(amountOfSand(true, input));
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(amountOfSand(false, input));
    }

    public int amountOfSand(boolean part1, List<String> input) {
        List<List<Loc>> in = ParseUtils.castInputToStream(input).map(s -> Arrays.asList(s.split(" -> ")).stream().map(s2 -> readString(s2, "%n,%n", Loc.class)).toList()).collect(Collectors.toCollection(ArrayList::new));
        if(!part1) {
            long maxY = in.stream().flatMapToLong(e -> e.stream().mapToLong(f -> f.y)).max().getAsLong() + 2;
            in.add(List.of(new Loc(0, maxY), new Loc(999, maxY)));
        }
        InfiniteGrid g = constructWalls(in);
        return simulateSand(part1, g);
    }

    private InfiniteGrid constructWalls(List<List<Loc>> in) {
        return in.stream().flatMap(AOCUtils::connectedPairs).flatMap(p -> new Range(p.a(), p.b()).stream()).collect(toInfiniteGrid('#'));
    }

    private static int simulateSand(boolean part1, InfiniteGrid g) {
        Loc sandOrigin = new Loc(500, 0);
        MutableLoc fallingSand = new MutableLoc(sandOrigin);
        while(part1 ? fallingSand.get().y<950 : g.get(sandOrigin).isEmpty()) {
            Loc moveTo = Stream.of(SOUTH, SOUTHWEST, SOUTHEAST, CENTER).map(d -> d.move(fallingSand.get())).filter(p -> g.get(p).isEmpty()).findFirst().get();
            if(moveTo.equals(fallingSand.get())) {
                g.set(fallingSand.get(), 'o');
                fallingSand.set(sandOrigin);
            } else {
                fallingSand.set(moveTo);
            }
        }
        return g.countChar('o');
    }
}
