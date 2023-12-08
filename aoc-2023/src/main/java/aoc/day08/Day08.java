package aoc.day08;

import aoc.Day2023;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static aoc.day12.Day12.lcm;
import static aoc.parser.DataMapper.readString;

public class Day08 extends Day2023 {

    private String[] input;
    private Map<String, Instruction> inst;

    public Day08() {
        super(8);
        input = day().split("\n\n");
        inst = Arrays.stream(input[1].split("\n")).map(s -> readString(s, "%s = (%s, %s)", Instruction.class)).collect(Collectors.toMap(e -> e.from, e -> e));
    }

    public static void main(String[] args){
        new Day08().printParts();
    }

    @Override
    public Object part1() {
        return walk(input, inst, "AAA", "ZZZ");
    }

    private static int walk(String[] input, Map<String, Instruction> inst, String start, String end) {
        int i = 0;
        String current = start;
        while(true) {
            char dir = input[0].charAt(i % input[0].length());
            if(dir == 'L') {
                current = inst.get(current).left;
            } else {
                current = inst.get(current).right;
            }
            i++;
            if(end.isEmpty() ? current.endsWith("Z") : current.equals(end)) {
                return i;
            }
        }
    }

    @Override
    public Object part2() {
        return lcm(inst.keySet().stream().filter(e -> e.endsWith("A")).mapToLong(e -> walk(input, inst, e, "")).toArray());
    }
}
