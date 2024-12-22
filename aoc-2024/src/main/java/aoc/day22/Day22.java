package aoc.day22;

import aoc.Day2024;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day22 extends Day2024 {
    public Day22() {
        super(22, "Monkey Market");
    }

    public static void main(String[] args) {
        new Day22().printParts();
    }

    @Override
    public Object part1() {

        List<Long> secrets = dayStream()
                .map(Long::parseLong)
                .toList();

        return secrets.stream()
                .mapToLong(this::generateSecret)
                .sum();
    }

    @Override
    public Object part2() {
        List<Long> secrets = dayStream()
                .map(Long::parseLong)
                .toList();

        Pair<List<Integer>, Integer> bestBuy = findBestBuy(secrets);
        return bestBuy.getValue();
    }

    private long generateSecret(long secret) {
        for (int i = 0; i < 2000; i++) {
            secret ^= (secret * 64) % 16777216L; // multiplied by 64, reduced by modulo XORed with the current secret
            secret ^= (secret / 32) % 16777216L; // divided by 32, reduced by modulo XORed with the current secret
            secret ^= (secret * 2048) % 16777216L; // multiplied by 2048, reduced by modulo XORed with the current secret
        }
        return secret;
    }

    private Pair<List<Integer>, List<Integer>> generatePriceChanges(long secret) {
        List<Integer> prices = new ArrayList<>();
        List<Integer> changes = new ArrayList<>();

        // Calculate prices
        for (int i = 0; i <= 2000; i++) {
            int price = (int) (secret % 10); // get last number
            prices.add(price);

            secret ^= (secret * 64) % 16777216L;
            secret ^= (secret / 32) % 16777216L;
            secret ^= (secret * 2048) % 16777216L;
            secret %= 16777216L;
        }

        // Calculate changes between consecutive prices
        for (int i = 1; i < prices.size(); i++) {
            changes.add(prices.get(i) - prices.get(i - 1));
        }

        return new Pair<>(prices, changes);
    }

    private Pair<List<Integer>, Integer> findBestBuy(List<Long> secrets) {
        Map<List<Integer>, Integer> sequenceScores = new HashMap<>();

        for (long secret : secrets) {
            Pair<List<Integer>, List<Integer>> result = generatePriceChanges(secret);
            List<Integer> prices = result.getKey();
            List<Integer> changes = result.getValue();
            Set<List<Integer>> visited = new HashSet<>();

            for (int i = 0; i < changes.size() - 3; i++) {
                List<Integer> seq = changes.subList(i, i + 4);
                if (!visited.contains(seq)) {
                    visited.add(seq);
                    sequenceScores.put(seq, sequenceScores.getOrDefault(seq, 0) + (prices.get(i + 4) % 10));
                }
            }
        }

        List<Integer> bestSequence = Collections.max(sequenceScores.entrySet(), Map.Entry.comparingByValue()).getKey();
        int bananas = sequenceScores.get(bestSequence);
        return new Pair<>(bestSequence, bananas);
    }

    @Getter
    static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }
}
