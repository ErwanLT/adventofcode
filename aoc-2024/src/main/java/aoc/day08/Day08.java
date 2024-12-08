package aoc.day08;

import aoc.Day2024;
import aoc.ListMap;
import aoc.grid.InfiniteGrid;
import aoc.location.Loc;

import java.util.*;
import java.util.stream.Stream;

import static aoc.AOCUtils.allPairs;
import static aoc.AOCUtils.appendWhile;
import static aoc.ListMap.toListMapReversed;
import static com.google.common.math.LongMath.gcd;
import static java.lang.Character.isLetterOrDigit;

public class Day08 extends Day2024 {

    private final char[][] input;

    public Day08(){
        super(8, "Resonant Collinearity");
        input = dayGrid();
    }

    public static void main(String[] args) {
        new Day08().printParts();
    }

    @Override
    public Object part1() {
        var in = new InfiniteGrid(input);
        return getAntennasByFrequency(in).stream().flatMapToObj((c, locs) ->
                allPairs(locs).flatMap(p -> Stream.of(
                        p.a().translate(l -> l * 2).move(new Loc(-p.b().x, -p.b().y)),
                        p.b().translate(l -> l * 2).move(new Loc(-p.a().x, -p.a().y))
                ).filter(in::contains))
        ).distinct().count();
    }

    @Override
    public Object part2() {
        var in = new InfiniteGrid(dayGrid());
        return getAntennasByFrequency(in).stream().filter((c, locs) -> locs.size() > 1).flatMapToObj((c, locs) -> {
            return allPairs(locs).flatMap(p -> {
                long dx = p.b().x - p.a().x;
                long dy = p.b().y - p.a().y;
                long gcd = gcd(Math.abs(dx), Math.abs(dy));
                long stepX = dx / gcd;
                long stepY = dy / gcd;
                return Stream.concat(
                        appendWhile(l -> l.move(stepX, stepY), in::contains, p.a()),
                        appendWhile(l -> l.move(-stepX, -stepY), in::contains, p.a())
                );
            });
        }).distinct().count();
    }


    private ListMap<Character, Loc> getAntennasByFrequency(InfiniteGrid in) {
        return in.streamChars().filter((loc, ch) -> isLetterOrDigit(ch)).collect(toListMapReversed());
    }
}
