package aoc.day11;

import aoc.Day;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day11 implements Day {

    private final static Set<Integer> forbiddenCharacters = Set.of((int) 'i', (int) 'o', (int) 'l');
    // ascii for 'a'
    private final static int LIMIT_LOW = 97;
    // ascii for 'z'
    private final static int LIMIT_HIGH = 122;

    private static String newPassword;

    @Override
    public String part1(List<String> input) {
        int[] password = input.get(0).chars().toArray();
        newPassword = nextPassword(password);

        return newPassword;
    }

    @Override
    public String part2(List<String> input) {
        int[] password = newPassword.chars().toArray();
        incrementPassword(password, password.length - 1);
        newPassword = nextPassword(password);

        return newPassword;
    }

    private static String nextPassword(int[] password) {
        while(!hasIncreasingTriple(password) || !hasTwoPairs(password)) {
            incrementPassword(password, password.length - 1);
        }

        return Arrays.stream(password).mapToObj(digit -> new String(Character.toString((char) digit))).collect(Collectors.joining());
    }

    private static void incrementPassword(int[] password, int digit) {
        if(digit < 0 || digit >= password.length) {
            return;
        }

        while(forbiddenCharacters.contains(password[digit])) {
            password[digit]++;
        }

        if(password[digit] == LIMIT_HIGH) {
            password[digit] = LIMIT_LOW;
            incrementPassword(password, digit - 1);
        } else {
            password[digit]++;
        }
    }

    private static boolean hasIncreasingTriple(int[] password) {
        for(int i = 0; i < password.length - 2; i++) {
            if(password[i] == password[i + 1] - 1 && password[i] == password[i + 2] - 2) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasTwoPairs(int[] password) {
        int pairCount = 0;
        for(int i = 0; i < password.length - 1; i++) {
            int current = password[i];
            int next = password[i + 1];

            if(current == next) {
                pairCount++;
                i++;
            }
        }
        return pairCount == 2;
    }
}
