package aoc.day17;

import aoc.Day2024;
import aoc.Pair;

import java.util.*;

import static java.util.stream.LongStream.range;

import static aoc.AOCUtils.recurse;

public class Day17 extends Day2024 {

    public Day17() {
        super(17, "Chronospatial Computer");
    }

    public static void main(String[] args) {
        new Day17().printParts();
    }

    public Program readProgram() {
        String PROGRAM = "2,4,1,6,7,5,4,4,1,7,0,3,5,5,3,0";
        String[] programSplit = PROGRAM.split(",");
        List<Integer> program = new ArrayList<>();
        for (String s : programSplit) {
            program.add(Integer.parseInt(s));
        }
        long c = 0;
        long b = 0;
        return new Program(37293246L, b, c, program);
    }

    @Override
    public Object part1() {
        return readProgram().simulateComputer();
    }

    @Override
    public Object part2() {
        return check(readProgram()).stream().min(Long::compareTo).orElse(0L);
    }

    private static Set<Long> check(Program p) {
        List<Integer> program = p.program();
        return recurse(new HashSet<>(), new Pair<>(0, 0L), (valids, stack, state) -> {
            int depth = state.a();
            long score = state.b();

            if (depth == program.size()) {
                valids.add(score);
            } else {
                range(0, 8)
                        .map(i -> i + 8 * score)
                        .filter(newScore -> Objects.equals(new Program(newScore, p.B(), p.C(), program).simulateComputer().getFirst(), program.get(program.size() - 1 - depth)))
                        .mapToObj(newScore -> new Pair<>(depth + 1, newScore))
                        .forEach(stack::add);
            }
            return valids;
        });
    }
}
