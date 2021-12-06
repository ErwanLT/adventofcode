package aoc.day06;

import aoc.Day;
import aoc.LongCountMap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day06 implements Day {
    @Override
    public String part1(List<String> input) {

        List<Long> lanternfishs = Arrays.stream(input.get(0).split(",")).map(Long::parseLong).collect(Collectors.toList());
        for (int day = 0; day < 80; day++){
            int shoalSize = lanternfishs.size();
            for (int i = 0; i<shoalSize; i++){
                if(lanternfishs.get(i) == 0){
                    lanternfishs.add(8L);
                }
                lanternfishs.set(i, lanternfishs.get(i) > 0 ? lanternfishs.get(i) - 1 : 6);
            }
        }

        return String.valueOf(lanternfishs.size());
    }

    @Override
    public String part2(List<String> input) {
        List<Long> lanternfishs = Arrays.stream(input.get(0).split(",")).map(Long::parseLong).collect(Collectors.toList());

        LongCountMap<Long> cm = new LongCountMap<>();
        for(var i : lanternfishs) {
            cm.increment(i);
        }
        LongCountMap<Long> nc = new LongCountMap<>();
        for(int j = 0; j<256; j++) {
            for(var e : cm.entrySet()) {
                if(e.getKey() == 0){
                    nc.increment(8L, e.getValue());
                    nc.increment(6L, e.getValue());
                }else {
                    nc.increment(e.getKey()-1, e.getValue());
                }
            }
            cm = nc;
            nc = new LongCountMap<>();
        }

        return String.valueOf(cm.values().stream().mapToLong(e -> e).sum());
    }
}
