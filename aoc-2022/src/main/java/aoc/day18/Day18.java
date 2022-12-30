package aoc.day18;

import aoc.DayOld;
import aoc.customMap.LongCountMap;
import aoc.day24.HexDirection;
import aoc.location.Loc3D;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static aoc.customMap.LongCountMap.toCountMap;
import static aoc.parser.ReadFormatedString.readString;


public class Day18 implements DayOld {
    @Override
    public String part1(List<String> input) {
        List<Loc3D> locs = parseInput(input);
        long connecting = locs.stream()
                .flatMap(l -> Arrays.stream(HexDirection.values()).map(d -> d.move(l, 1)))
                .filter(locs::contains)
                .count();
        var faces = (locs.size() * 6L) - connecting;
        return String.valueOf(faces);
    }

    private List<Loc3D> parseInput(List<String> input) {
        List<Loc3D> locs = new ArrayList<>();
        for (String s:input) {
            String[] vals = s.split(",");
            int x = Integer.parseInt(vals[0]);
            int y =Integer.parseInt(vals[1]);
            int z =Integer.parseInt(vals[2]);
            Loc3D loc3D = new Loc3D(x, y, z);
            locs.add(loc3D);
        }

        return locs;
    }

    @Override
    public String part2(List<String> input) {
        List<Loc3D> locs = parseInput(input);
        List<Loc3D> connecting = locs.stream().flatMap(l -> Arrays.stream(HexDirection.values()).map(d -> d.move(l, 1))).collect(Collectors.toCollection(ArrayList::new));
        List<Loc3D> exterior = connecting.stream().filter(locs::contains).toList();
        LongCountMap<Set<Loc3D>> pockets = connecting.stream().filter(l -> !locs.contains(l)).map(l -> new HashSet<>(Set.of(l))).collect(toCountMap());
        var trapped = new AtomicLong();
        for(int i = 0; i<100; i++) {
            LongCountMap<Set<Loc3D>> newPockets = new LongCountMap<>();
            pockets.forEach((pocket, n) -> {
                Set<Loc3D> spread = pocket.stream().flatMap(l -> Arrays.stream(HexDirection.values()).map(d -> d.move(l, 1))).filter(l -> !locs.contains(l)).collect(Collectors.toSet());
                spread.addAll(pocket);
                if(spread.size() == pocket.size()) {
                    trapped.addAndGet(n);
                } else if(spread.size()<=connecting.size()) {
                    var matching = newPockets.keySet().stream().filter(e -> e.stream().anyMatch(spread::contains)).findAny();
                    matching.ifPresentOrElse(c -> {
                        spread.addAll(c);
                        newPockets.increment(spread, n + newPockets.remove(c));
                    }, () -> newPockets.increment(spread, n));
                }
            });
            pockets = newPockets;
        }
        var surface = (locs.size()*6L) - exterior.size() - trapped.get();
        return String.valueOf(surface);
    }
}
