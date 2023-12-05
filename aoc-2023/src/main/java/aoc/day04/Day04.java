package aoc.day04;

import aoc.Day2023;
import aoc.IntUtil;
import aoc.ListUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day04 extends Day2023 {

    public Day04(){
        super(4);
    }

    public static void main(String[] args){
        new Day04().printParts();
    }

    @Override
    public Object part1() {
        List<Card> scratchCards = parse(dayStrings());

        return  scratchCards.stream()
                .map(Card::calculateCardPoints)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private List<Card> parse(String[] strings) {
        List<Card> cards = new ArrayList<>();
        for (String s : strings){
            String[] split = s.split(":");
            String cardNumber = split[0];
            String[] split2 = split[1].split("\\|");
            String number1 = split2[0].trim();
            String number2 = split2[1].trim();

            var winningNumbers = ListUtils.extractUnsignedIntegers(number1);
            var myNumbers = ListUtils.extractUnsignedIntegers(number2);


            cards.add(new Card(cardNumber, winningNumbers, myNumbers));

        }
        return cards;
    }

    @Override
    public Object part2() {
        var sum = 0;
        var numberOfCards = new HashMap<Integer, Integer>();

        for (var line: dayStrings()) {
            var parts = line.split(": ");
            int cardNumber = IntUtil.parseUnsignedInteger(parts[0]);
            String cardId = parts[0];
            numberOfCards.put(cardNumber, numberOfCards.getOrDefault(cardNumber, 0) + 1);

            var numberList = parts[1].split(" \\| ");
            var winningNumbers = ListUtils.extractUnsignedIntegers(numberList[0]);
            var myNumbers = ListUtils.extractUnsignedIntegers(numberList[1]);

            Card card = new Card(cardId, winningNumbers, myNumbers);

            var matchedNumbersCount = card.getMatchedNumbers();

            var numberOfWonCards = numberOfCards.getOrDefault(cardNumber, 1);

            for (var cardOffset = 1; cardOffset <= matchedNumbersCount; cardOffset++) {
                numberOfCards.put(
                        cardNumber + cardOffset,
                        numberOfCards.getOrDefault(cardNumber + cardOffset, 0) + numberOfWonCards
                );
            }

            sum += numberOfCards.get(cardNumber);
        }

        return sum;
    }

}
