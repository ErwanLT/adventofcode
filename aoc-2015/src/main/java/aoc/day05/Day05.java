package aoc.day05;

import aoc.Day;

import java.util.List;
import java.util.regex.Pattern;

public class Day05 implements Day {

    private static final String VOWELS_REGEX = ".*(.*[aeiou]){3}.*";
    private static final String SAME_LETTER_ROW_TWICE = "(.)\\1";
    private static final String BAD_STRING = "ab|cd|pq|xy";

    @Override
    public String part1(List<String> input) {

        Pattern badString = Pattern.compile(BAD_STRING);
        Pattern vowel = Pattern.compile(VOWELS_REGEX);
        Pattern doubleLetter = Pattern.compile(SAME_LETTER_ROW_TWICE);

        int count = 0;
        for (String s: input) {
            if(vowel.matcher(s).find() &&
                    doubleLetter.matcher(s).find() &&
                    !badString.matcher(s).find()){
                count++;
            }
        }

        return String.valueOf(count);
    }

    @Override
    public String part2(List<String> input) {
        int count = 0;
        Pattern p2_p1 = Pattern.compile("(..).*\\1");
        Pattern p2_p2 = Pattern.compile("(.).\\1");

        for (String s: input) {
            if(p2_p1.matcher(s).find() &&
                    p2_p2.matcher(s).find()) {
                count++;
            }
        }
        return String.valueOf(count);
    }
}
