package aoc.day18;

import aoc.Day;
import aoc.day24.HexDirection;
import aoc.location.Loc3D;

import java.util.*;
import java.util.stream.Collectors;

import static aoc.parser.ReadFormatedString.readString;


public class Day18 implements Day {
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
        connecting.removeAll(exterior);
        List<Set<Loc3D>> pockets = connecting.stream().map(l -> new HashSet<>(Set.of(l))).collect(Collectors.toCollection(ArrayList::new));
        int trapped = 0;
        for(int i = 0; i<1000; i++) {
            for(int j = 0; j<pockets.size(); j++) {
                Set<Loc3D> pocket = pockets.get(j);
                Set<Loc3D> s = new HashSet<>(pocket);
                pocket.addAll(pocket.stream().flatMap(l -> Arrays.stream(HexDirection.values()).map(d -> d.move(l, 1))).filter(l -> !locs.contains(l)).collect(Collectors.toSet()));
                if(s.size() == pocket.size()) {
                    trapped++;
                    pockets.remove(j);
                    j--;
                } else if(pocket.size()>2000) {
                    pockets.remove(j);
                    j--;
                }
            }
        }
        var surface = (locs.size()*6L) - exterior.size() - trapped;
        return String.valueOf(surface);
    }
}
