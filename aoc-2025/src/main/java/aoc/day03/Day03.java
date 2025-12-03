package aoc.day03;

import aoc.Day2025;

import java.math.BigInteger;

public class Day03 extends Day2025 {


    public Day03() {
        super(3, "Lobby");
    }

    private int calculateMaxJoltageForBank(String bank) {
        int maxJoltage = 0;
        for (int i = 0; i < bank.length(); i++) {
            for (int j = i + 1; j < bank.length(); j++) {
                String joltageStr = "" + bank.charAt(i) + bank.charAt(j);
                int joltage = Integer.parseInt(joltageStr);
                if (joltage > maxJoltage) {
                    maxJoltage = joltage;
                }
            }
        }
        return maxJoltage;
    }

    @Override
    public Object part1() {
        long totalJoltage = 0;
        for (String line : dayStrings()) {
            totalJoltage += calculateMaxJoltageForBank(line);
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

    @Override
    public Object part2() {
        BigInteger totalJoltage = BigInteger.ZERO;
        for (String line : dayStrings()) {
            String joltageStr = findLargestJoltage(line, 12);
            totalJoltage = totalJoltage.add(new BigInteger(joltageStr));
        }
        return totalJoltage;
    }
}
