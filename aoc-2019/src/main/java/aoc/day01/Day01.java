package aoc.day01;

import aoc.Day;
import aoc.parser.ParseUtils;

import java.util.List;

public class Day01 implements Day {

    private static int[] inputs;

    @Override
    public String part1(List<String> input) {
        inputs = ParseUtils.castInputToIntArray(input);

        int necessaryFuel = 0;
        for (int moduleMasse: inputs) {
            necessaryFuel += Math.floor(moduleMasse/3)-2;
        }

        return String.valueOf(necessaryFuel);
    }

    @Override
    public String part2(List<String> input) {
        int necessaryFuel = 0;
        for (int number : inputs) {
            int temp = number / 3 - 2;
            necessaryFuel += temp;
            while (temp != 0) {
                temp = temp / 3 - 2;
                if (temp <= 0) {
                    temp = 0;
                }
                necessaryFuel += temp;
            }
        }
        return String.valueOf(necessaryFuel);
    }
}
