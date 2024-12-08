package aoc.day03;

import aoc.Day2024;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 extends Day2024 {

    private final String input;

    private static final Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)");

    public Day03() {
        super(3, "Mull It Over");
        input = day();
    }

    public static void main(String[] args) {
        new Day03().printParts();
    }

    @Override
    public Object part1() {
        Matcher matcher = pattern.matcher(input);
        int totalSum = 0;

        while (matcher.find()) {
            if (matcher.group(1) != null && matcher.group(2) != null) { // mul(X,Y) match
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                totalSum += x * y;
            }
        }

        return totalSum;
    }

    @Override
    public Object part2() {
        Matcher matcher = pattern.matcher(input);
        boolean isEnabled = true; // `mul` is enabled by default
        int totalSum = 0;

        while (matcher.find()) {
            if (matcher.group(1) != null && matcher.group(2) != null && isEnabled) { // mul(X,Y) match
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                totalSum += x * y;
            } else if ("do()".equals(matcher.group())) { // do() match
                isEnabled = true;
            } else if ("don't()".equals(matcher.group())) { // don't() match
                isEnabled = false;
            }
        }

        return totalSum;
    }
}
