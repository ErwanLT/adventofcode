package aoc.day04;

import aoc.Day;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.google.common.collect.ImmutableSet.toImmutableSet;

public class Day04 implements Day {

    private static String[][] passports;

    private static final Map<String, String> expected = Map.of("byr", "^(200[0-2]|19[2-9][0-9])$",
            "iyr", "^(2020|201[0-9])$",
            "eyr", "^(2030|202[0-9])$",
            "hgt", "^((1([5-8][0-9]|9[0-3])cm)|((59|6[0-9]|7[0-6])in))$",
            "hcl", "^(#[0-9a-f]{6})$",
            "ecl", "^(amb|blu|brn|gry|grn|hzl|oth)$",
            "pid", "^[0-9]{9}$");


    @Override
    public String part1(List<String> input) {
        parseInput(input);

        long validPasseport = verifyPassport(Day04::valid1);

        return String.valueOf(validPasseport);
    }

    private void parseInput(List<String> input) {
        String inputs = String.join("\n", input);
        passports = Arrays.stream(inputs.split("\n\n")).map(str -> str.replace("\n", " ")).map(str -> str.split(" ")).toArray(String[][]::new);
    }

    @Override
    public String part2(List<String> input) {
        parseInput(input);

        long validPasseport = verifyPassport(Day04::valid2);

        return String.valueOf(validPasseport);
    }

    private static long verifyPassport(Predicate<String[]> verifyFunction) {
        return Arrays.stream(passports).filter(verifyFunction).count();
    }

    public static boolean valid1(String[] passport) {
        return Arrays.stream(passport).map(s -> s.substring(0, 3)).collect(toImmutableSet()).containsAll(expected.keySet());
    }

    public static boolean valid2(String[] passport) {
        return valid1(passport) && Arrays.stream(passport).map(s -> s.split(":")).allMatch(s -> matchesRegex(s[0], s[1]));
    }

    public static boolean matchesRegex(String key, String validate) {
        if (!expected.containsKey(key)) return true;
        final Pattern pattern = Pattern.compile(expected.get(key));
        return pattern.matcher(validate).matches();
    }
}
