package aoc.day07;

import aoc.StringUtil;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class Hand {
    private final String totalValue;
    private static final Map<String, Integer> typeValue = Map.of(
            "5", 7,
            "41", 6,
            "32", 5,
            "311", 4,
            "221", 3,
            "2111", 2,
            "11111", 1
    );
    final static String weights = "23456789TJQKA";
    final static String weights2 = "J23456789TQKA";
    final static String replacements = "23456789TQKA";

    public Hand(String cards, boolean adjustForPart2) {
        var cardsValue = cards.chars()
                .map(c -> adjustForPart2 ? weights2.indexOf(c) : weights.indexOf(c))
                .mapToObj(Integer::toHexString)
                .collect(Collectors.joining());

        if (adjustForPart2 && cards.indexOf('J') != -1) {
            var maxTypeValue = replacements.chars()
                    .mapToObj(r -> (char) r)
                    .map(r -> cards.replace('J', r))
                    .map(Hand::getTypeValue)
                    .max(Comparator.comparingInt(a -> a))
                    .orElseThrow();

            totalValue = maxTypeValue + cardsValue;
            return;
        }

        totalValue = getTypeValue(cards) + cardsValue;
    }

    public static int compare(Hand hand1, Hand hand2) {
        return hand1.totalValue.compareTo(hand2.totalValue);
    }

    public static int getTypeValue(String cards) {
        return typeValue.get(
                StringUtil.tally(cards).values().stream()
                        .sorted(Comparator.reverseOrder())
                        .map(String::valueOf)
                        .collect(Collectors.joining())
        );
    }
}
