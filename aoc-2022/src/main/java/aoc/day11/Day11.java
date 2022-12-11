package aoc.day11;

import aoc.Day;
import aoc.customMap.LongCountMap;
import aoc.parser.ParseUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static aoc.parser.ReadFormatedString.readString;

public class Day11 implements Day {
    @Override
    public String part1(List<String> input) {
        return String.valueOf(solution(20, true, input));
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(solution(10000, false, input));
    }

    private long solution(int cycles, boolean decreasingWorry, List<String> input) {
        var in = ParseUtils.castInputToString("\n", input);
        List<Monkey> monkeys = Arrays.stream(in.split("\n\n")).map(String::trim).map(s -> readString(s, "Monkey %n:\n" +
                "  Starting items: %s\n" +
                "  Operation: new = old %c %s\n" +
                "  Test: divisible by %n\n" +
                "    If true: throw to monkey %n\n" +
                "    If false: throw to monkey %n", Monkey.class)).toList();
        Map<Long, List<Long>> items = monkeys.stream().collect(Collectors.toMap(Monkey::getN, m -> Arrays.stream(m.getItems().split(", ")).map(Long::parseLong).collect(Collectors.toCollection(ArrayList::new))));
        LongCountMap<Long> times = new LongCountMap<>();
        long gcd = monkeys.stream().mapToLong(Monkey::getDivisible).reduce((a, b) -> a*b).getAsLong();
        for(int i = 0; i<cycles; i++) {
            for(Monkey m : monkeys) {
                while(!items.get(m.getN()).isEmpty()) {
                    long item = items.get(m.getN()).remove(0);
                    long worryLevel = applyOperator(item, m.getOp(), m.getAdd()) / (decreasingWorry ? 3 : 1);
                    boolean test = worryLevel % m.getDivisible() == 0;
                    items.get(test ? m.getIfTrue() : m.getIfFalse()).add(worryLevel % gcd);
                    times.increment(m.getN());
                }
            }
        }
        long[] sorted = times.values().stream().mapToLong(e -> e).sorted().toArray();
        return sorted[sorted.length-1] * sorted[sorted.length-2];
    }

    private long applyOperator(long item, char op, String add) {
        long itemValue = add.equals("old") ? item : Long.parseLong(add);
        return switch (op) {
            case '*' -> item * itemValue;
            case '+' -> item + itemValue;
            default -> throw new IllegalStateException();
        };
    }
}
