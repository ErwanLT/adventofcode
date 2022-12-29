package aoc.day02;

import aoc.Day;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Day02 implements Day {


    @Override
    public String part1(List<String> input) {
        int checksum = 0;
        for (String str : input) {
            var numbers = extractIntegersFromString(str);
            int min = numbers[0];
            int max = numbers[0];

            for (int i = 1; i < numbers.length; i++) {
                if (numbers[i] < min) {
                    min = numbers[i];
                }
                if (numbers[i] > max) {
                    max = numbers[i];
                }
            }
            var diff = max - min;
            checksum += diff;
        }
        return String.valueOf(checksum);
    }

    public static Integer[] extractIntegersFromString(String s) {
        var list = new LinkedList<Integer>();

        var p = Pattern.compile("-?\\d+");
        var m = p.matcher(s);

        while (m.find()) {
            list.add(Integer.parseInt(m.group()));
        }

        return list.toArray(new Integer[0]);
    }

    @Override
    public String part2(List<String> input) {

        int checksum = 0;
        for (String str : input) {
            var numbers = extractIntegersFromString(str);

            Arrays.sort(numbers);

            for (int i = 0; i < numbers.length; i++) {
                for (int j = i + 1; j < numbers.length; j++) {
                    if (numbers[i] % numbers[j] == 0) {
                        checksum += numbers[i] / numbers[j];
                    }else if (numbers[j] % numbers[i] == 0){
                        checksum += numbers[j] / numbers[i];
                    }
                }
            }
        }

        return String.valueOf(checksum);
    }
}
