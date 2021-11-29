package aoc.day07;

import aoc.Day;

import java.util.*;
import java.util.stream.IntStream;

import static java.lang.StrictMath.toIntExact;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;

public class Day07 implements Day {

    private static Trade[] trades;

    @Override
    public String part1(List<String> input) {
        trades = input.stream().filter(s -> !s.contains("no other bags")).map(Trade::new).toArray(Trade[]::new);
        int bagType = findBagTypes(new Item(1, "shiny gold"), new HashSet<>()).size() - 1;
        return String.valueOf(bagType);
    }

    @Override
    public String part2(List<String> input) {
        trades = input.stream().filter(s -> !s.contains("no other bags")).map(Trade::new).toArray(Trade[]::new);
        int numberOfBags = (int) (findNumberOfBags(new LinkedList<>(singletonList(new Item(1, "shiny gold")))) - 1);
        return String.valueOf(numberOfBags);
    }

    private static Set<String> findBagTypes(Item buyingItem, Set<String> visitedColors) {
        visitedColors.add(buyingItem.item);
        Trade[] possibleTrades = getTrades(buyingItem);
        stream(possibleTrades).forEach(t -> findBagTypes(t.input, visitedColors));
        return visitedColors;
    }

    private static Trade[] getTrades(Item i) {
        return stream(trades).filter(e -> stream(e.output).anyMatch(t -> t.item.equals(i.item) && t.amount >= i.amount)).toArray(Trade[]::new);
    }

    private static Optional<Trade> getTrade(Item i) {
        return stream(trades).filter(e -> e.input.item.equals(i.item)).findAny();
    }

    private static long findNumberOfBags(Deque<Item> leftOver) {
        long total = 0;
        while (!leftOver.isEmpty()) total += findBagsInside(leftOver, leftOver.pop());
        return total;
    }

    private static long findBagsInside(Deque<Item> leftOver, Item buyingItem) {
        Optional<Trade> fuelTrade = getTrade(buyingItem);
        fuelTrade.ifPresent(trade -> stream(trade.output).flatMap(i -> IntStream.rangeClosed(1, toIntExact(i.amount)).mapToObj(e -> new Item(1, i.item))).forEach(leftOver::add));
        return buyingItem.amount;
    }
}
