package aoc.day01;

import aoc.DayOld;

import java.util.List;

public class Day01 implements DayOld {
    @Override
    public String part1(List<String> input) {
        int sum = 0;
        var str = input.get(0);
        for (int i = 0; i < str.length() - 1; i++) {
            char currentDigit = str.charAt(i);
            char nextDigit = str.charAt(i + 1);

            if (currentDigit == nextDigit) {
                sum += Character.getNumericValue(currentDigit);
            }
        }

        // If the last digit matches the first digit, add it to the sum
        if (str.charAt(str.length() - 1) == str.charAt(0)) {
            sum += Character.getNumericValue(str.charAt(0));
        }
        return String.valueOf(sum);
    }

    @Override
    public String part2(List<String> input) {
        int sum = 0;
        var str = input.get(0);
        int halfway = str.length() / 2;

        for (int i = 0; i < str.length(); i++) {
            char currentDigit = str.charAt(i);
            char halfwayDigit = str.charAt((i + halfway) % str.length());

            if (currentDigit == halfwayDigit) {
                sum += Character.getNumericValue(currentDigit);
            }
        }

        return String.valueOf(sum);
    }
}
