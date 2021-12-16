package aoc.day10;

import aoc.Day;

import java.util.List;

public class Day10 implements Day {
    @Override
    public String part1(List<String> input) {

        return String.valueOf(generateSequences(input.get(0), 40).length());
    }

    @Override
    public String part2(List<String> input) {

        return String.valueOf(generateSequences(input.get(0), 50).length());
    }

    private static String generateSequences(String input, int amount) {
        String out = input;
        for(int i = 0; i < amount; i++) {
            out = generateNextSequence(out);
        }
        return out;
    }

    private static String generateNextSequence(String in) {
        StringBuffer out = new StringBuffer();
        for(int i = 0; i < in.length(); i++) {
            int duplicates = 1;

            for(int j = i + 1; j < in.length(); j++) {
                if(in.charAt(j) == in.charAt(i)) {
                    duplicates++;
                } else {
                    break;
                }
            }
            i += duplicates - 1;
            out.append(duplicates).append(in.charAt(i));
        }

        return out.toString();
    }
}
