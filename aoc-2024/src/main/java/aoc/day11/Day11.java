package aoc.day11;

import aoc.Day2024;

import java.math.BigInteger;
import java.util.*;

public class Day11 extends Day2024 {

    public Day11() {
        super(11, "Plutonian Pebbles");
    }

    public static void main(String[] args) {
        new Day11().printParts();
    }

    @Override
    public Object part1() {
        return calculateTotalStones(25);
    }

    @Override
    public Object part2() {
        return calculateTotalStones(75);
    }

    private long calculateTotalStones(int blinks) {
        Map<BigInteger, Long> stones = parseInput();
        stones = performBlinks(blinks, stones);
        return stones.values().stream().mapToLong(Long::longValue).sum();
    }

    private Map<BigInteger, Long> parseInput() {
        Map<BigInteger, Long> stones = new HashMap<>();
        for (String num : day().split(" ")) {
            BigInteger stone = new BigInteger(num);
            stones.merge(stone, 1L, Long::sum);
        }
        return stones;
    }

    private static Map<BigInteger, Long> performBlinks(int blinks, Map<BigInteger, Long> stones) {
        for (int i = 0; i < blinks; i++) {
            stones = applyBlinkRules(stones);
        }
        return stones;
    }

    private static Map<BigInteger, Long> applyBlinkRules(Map<BigInteger, Long> stones) {
        Map<BigInteger, Long> newStones = new HashMap<>();

        stones.forEach((stone, count) -> {
            if (stone.equals(BigInteger.ZERO)) {
                handleZeroStone(count, newStones);
            } else if (hasEvenDigitCount(stone)) {
                splitStone(stone, count, newStones);
            } else {
                multiplyStone(stone, count, newStones);
            }
        });

        return newStones;
    }

    private static void handleZeroStone(long count, Map<BigInteger, Long> newStones) {
        newStones.merge(BigInteger.ONE, count, Long::sum);
    }

    private static boolean hasEvenDigitCount(BigInteger stone) {
        return stone.toString().length() % 2 == 0;
    }

    private static void splitStone(BigInteger stone, long count, Map<BigInteger, Long> newStones) {
        String stoneStr = stone.toString();
        int mid = stoneStr.length() / 2;

        BigInteger left = new BigInteger(stoneStr.substring(0, mid));
        BigInteger right = new BigInteger(stoneStr.substring(mid));

        newStones.merge(left, count, Long::sum);
        newStones.merge(right, count, Long::sum);
    }

    private static void multiplyStone(BigInteger stone, long count, Map<BigInteger, Long> newStones) {
        BigInteger multipliedStone = stone.multiply(BigInteger.valueOf(2024));
        newStones.merge(multipliedStone, count, Long::sum);
    }
}
