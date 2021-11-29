package aoc.day06;

import aoc.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.primitives.Ints.asList;
import static java.util.stream.IntStream.range;

public class Day06 implements Day {

    private static String inputs = "";

    @Override
    public String part1(List<String> input) {
        parseInput(input);
        Long sumOfCount = Arrays.stream(inputs.split("\n\n")).map(s -> s.replace("\n", ""))
                .mapToLong(s -> s.chars().distinct().count()).sum();
        return String.valueOf(sumOfCount);
    }

    @Override
    public String part2(List<String> input) {
        parseInput(input);

        int x = Arrays.stream(inputs.split("\n\n")).mapToInt(group -> {
            String[] people = group.split("\n");
            List<Integer> c = new ArrayList<>(asList(people[0].chars().toArray()));
            range(1, people.length).forEach(i -> c.retainAll(asList(people[i].chars().toArray())));
            return c.size();
        }).sum();

        return String.valueOf(x);
    }

    private void parseInput(List<String> input) {
        inputs = String.join("\n", input);
    }
}
