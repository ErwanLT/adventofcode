package aoc.day03;

import aoc.Day2025;

import java.math.BigInteger;

public class Day03 extends Day2025 {

    private final String[] input;

    public Day03() {
        super(3, "Lobby");
        this.input = dayStrings();
    }

    @Override
    public Object part1() {
        return solve(2);
    }

    @Override
    public Object part2() {
        return solve(12);
    }

    private BigInteger solve(int k) {
        BigInteger totalJoltage = BigInteger.ZERO;
        for (String line : input) {
            String joltageStr = findLargestJoltage(line, k);
            totalJoltage = totalJoltage.add(new BigInteger(joltageStr));
        }
        return totalJoltage;
    }

    private String findLargestJoltage(String bank, int k) {
        if (k == 0) {
            return "0";
        }
        if (k > bank.length()) {
            return bank;
        }

        StringBuilder result = new StringBuilder();
        int start = 0;

        for (int i = 0; i < k; i++) {
            int end = bank.length() - k + i;
            char maxChar = '0';
            int maxIndex = -1;

            for (int j = start; j <= end; j++) {
                if (bank.charAt(j) > maxChar) {
                    maxChar = bank.charAt(j);
                    maxIndex = j;
                }
            }

            result.append(maxChar);
            start = maxIndex + 1;
        }

        return result.toString();
    }
}
