package aoc.day10;

import aoc.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 implements Day {

    private List<String> in = new ArrayList<>();

    @Override
    public String part1(List<String> input) {
        in.addAll(input);
        var sum = performOp(this::signalStrength).mapToLong(e -> e).sum();
        return String.valueOf(sum);
    }

    @Override
    public String part2(List<String> input) {
        return performOp(this::getPixel).collect(Collectors.joining());
    }

    private long signalStrength(long cycle, long x) {
        return (cycle+20) % 40 == 0 ? cycle*x : 0;
    }

    private String getPixel(long cycle, long x) {
        long i = (cycle -1) % 40;
        return (i == 0 ? "\n" : "") + (List.of(x -1, x, x +1).contains(i) ? "██" : "  ");
    }

    private<T> Stream<T> performOp(BiFunction<Long, Long, T> func) {
        long cycle = 1;
        long x  = 1;
        List<T> res = new ArrayList<>();
        for(String op : in) {
            res.add(func.apply(cycle, x));
            if(op.startsWith("addx")) {
                cycle++;
                res.add(func.apply(cycle, x));
                x+=Long.parseLong(op.substring(5));
            }
            cycle++;
        }
        return res.stream();
    }
}
