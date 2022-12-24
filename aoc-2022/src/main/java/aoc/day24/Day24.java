package aoc.day24;

import aoc.Day;
import aoc.Direction;
import aoc.grid.InfiniteGrid;
import aoc.location.Loc;
import aoc.parser.ParseUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static aoc.Direction.*;

public class Day24 implements Day {

    Map<Character, Direction> dirs = Map.of('^', NORTH, 'v', SOUTH, '>', EAST, '<', WEST);
    public record Blizzard(Loc l, Direction dir) {}

    @Override
    public String part1(List<String> input) {
        return String.valueOf(solution(input, false));
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(solution(input, true));
    }

    private long solution(List<String> input,boolean part2) {
        Set<Loc> states = new HashSet<>();
        InfiniteGrid in = new InfiniteGrid(ParseUtils.castInputToBiCharArray(input), '.');
        List<Blizzard> blizzards = in.grid.entrySet().stream()
                .filter(e -> dirs.containsKey(e.getValue()))
                .map(e -> new Blizzard(e.getKey(), dirs.get(e.getValue())))
                .toList();
        in.removeIf((l, c) -> dirs.containsKey(c));
        states.add(new Loc(1, 0));
        Loc dest = new Loc(in.maxX()-1, in.maxY());
        boolean realEnd = false;
        for(long i = 1; true; i++) {
            InfiniteGrid g = new InfiniteGrid(in.grid);
            blizzards = blizzards.stream()
                    .map(b -> new Blizzard(b.dir.move(b.l), b.dir))
                    .map(b -> in.get(b.l).isPresent() ?
                            new Blizzard(new Loc(b.dir == WEST ? in.maxX()-1 : b.dir == EAST ? 1 :
                                    b.l.x, b.dir == NORTH ? in.maxY()-1 : b.dir == SOUTH ? 1 : b.l.y), b.dir) : b)
                    .toList();
            blizzards.forEach(b -> g.set(b.l, 'X'));
            states = states.stream()
                    .flatMap(s -> Direction.five().map(d -> d.move(s)).filter(l -> l.x>=0 && l.y>=0 && !g.contains(l)))
                    .collect(Collectors.toSet());
            if(states.contains(dest)) {
                if(!part2 || realEnd) return i;
                states = Set.of(dest);
                if(dest.equals(new Loc(1, 0))) {
                    dest = new Loc(in.maxX()-1, in.maxY());
                    realEnd = true;
                } else {
                    dest = new Loc(1, 0);
                }
            }
        }
    }
}
