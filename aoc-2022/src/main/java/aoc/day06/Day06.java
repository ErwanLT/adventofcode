package aoc.day06;

import aoc.Day;

import java.util.List;
import java.util.Set;

public class Day06 implements Day {
    @Override
    public String part1(List<String> input) {
        return calculateAnswer(4, input.get(0));
    }

    @Override
    public String part2(List<String> input) {
        return calculateAnswer(14, input.get(0));
    }

    private String calculateAnswer(int size, String in) {
        for(int i = 0; i<in.length(); i++){
            Set<Integer> chars = Set.copyOf(in.substring(i, i+size).chars().boxed().toList());
            if(chars.size() == size) return String.valueOf(i+size);
        }
        return String.valueOf(0);
    }
}
