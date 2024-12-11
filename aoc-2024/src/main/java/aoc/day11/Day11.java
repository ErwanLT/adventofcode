package aoc.day11;

import aoc.Day2024;

import java.math.BigInteger;
import java.util.*;

public class Day11 extends Day2024 {

    public Day11(){
        super(11, "Plutonian Pebbles");
    }

    public static void main(String[] args) {
        new Day11().printParts();
    }

    @Override
    public Object part1() {
        Map<BigInteger, Long> stones = parseInput();

        stones = blinkNumber(25, stones);

        // Calculate the total number of stones

        return stones.values().stream().mapToLong(Long::longValue).sum();
    }

    private static Map<BigInteger, Long> blinkNumber(int blinks, Map<BigInteger, Long> stones) {
        for (int i = 0; i < blinks; i++) {
            stones = blink(stones);
        }
        return stones;
    }

    public static boolean isEvenDigits(BigInteger stone) {
        return stone.toString().length() % 2 == 0;
    }

    @Override
    public Object part2() {
        Map<BigInteger, Long> stones = parseInput();

        stones = blinkNumber(75, stones);

        // Calculate the total number of stones

        return stones.values().stream().mapToLong(Long::longValue).sum();
    }

    private Map<BigInteger, Long> parseInput() {
        Map<BigInteger, Long> stones = new HashMap<>();

        // Parse the input string into a map of BigInteger to counts
        for (String num : day().split(" ")) {
            BigInteger stone = new BigInteger(num);
            stones.put(stone, stones.getOrDefault(stone, 0L) + 1);
        }
        return stones;
    }

    public static Map<BigInteger, Long> blink(Map<BigInteger, Long> stones) {
        Map<BigInteger, Long> newStones = new HashMap<>();

        for (Map.Entry<BigInteger, Long> entry : stones.entrySet()) {
            BigInteger stone = entry.getKey();
            long count = entry.getValue();

            if (stone.equals(BigInteger.ZERO)) {
                // Rule 1: Replace 0 with 1
                newStones.put(BigInteger.ONE, newStones.getOrDefault(BigInteger.ONE, 0L) + count);
            } else if (isEvenDigits(stone)) {
                // Rule 2: Split into two stones (left and right halves)
                splitStone(stone, count, newStones);
            } else {
                // Rule 3: Multiply the stone by 2024
                BigInteger newStone = stone.multiply(BigInteger.valueOf(2024));
                newStones.put(newStone, newStones.getOrDefault(newStone, 0L) + count);
            }
        }

        return newStones;
    }


    public static void splitStone(BigInteger stone, long count, Map<BigInteger, Long> newStones) {
        String stoneStr = stone.toString();
        int mid = stoneStr.length() / 2;

        BigInteger left = new BigInteger(stoneStr.substring(0, mid));
        BigInteger right = new BigInteger(stoneStr.substring(mid));

        newStones.put(left, newStones.getOrDefault(left, 0L) + count);
        newStones.put(right, newStones.getOrDefault(right, 0L) + count);
    }
}
