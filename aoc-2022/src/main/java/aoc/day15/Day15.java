package aoc.day15;

import aoc.DayOld;
import aoc.grid.InfiniteGrid;
import aoc.location.Loc;
import aoc.location.Range;

import java.util.List;
import java.util.stream.IntStream;

import static aoc.Direction.fourDirections;
import static aoc.grid.InfiniteGrid.toInfiniteGrid;
import static aoc.parser.ReadFormatedString.readString;
import static java.util.Arrays.stream;

public class Day15 implements DayOld {

    @Override
    public String part1(List<String> input) {
        List<Range> posList = input(input);
        InfiniteGrid g = posList.stream().flatMap(Range::flatten).collect(toInfiniteGrid('X'));
        var count = IntStream.range(-1000000, 5000000)
                .mapToObj(i -> new Loc(i, 2000000))
                .filter(l -> posList.stream().anyMatch(p -> l.distance(p.getStart()) <= p.distance() && g.get(l).isEmpty()))
                .count();
        return String.valueOf(count);
    }

    @Override
    public String part2(List<String> input) {
        List<Range> posList = input(input);
        Range target = new Range(new Loc(0, 0), new Loc(4000000, 4000000));
        var frequency = input(input).stream()
                .flatMap(p -> stream(fourDirections()).flatMap(d -> d.move(p.start, (int) (p.distance() + 1)).walk(d.turnSteps(3), p.distance() + 1)))
                .filter(target::inRange)
                .filter(l -> posList.stream().allMatch(p -> l.distance(p.start) > p.distance()))
                .mapToLong(l -> l.x * 4000000 + l.y)
                .findAny()
                .getAsLong();


        return String.valueOf(frequency);
    }

    private List<Range> input(List<String> input) {
        return input.stream().map(s -> readString(s, "Sensor at x=%n, y=%n: closest beacon is at x=%n, y=%n", Range.class)).toList();
    }
}
