package aoc.day04;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Card {
    public String cardNumber;

    public List<Integer> winningNumbers;

    public List<Integer> numbers;

    public int calculateCardPoints() {
        int cardPoints = 0;
        Set<Integer> uniqueNumbers = new HashSet<>();

        // Check each number on the card
        for (int number : numbers) {
            if (winningNumbers.contains(number) && !uniqueNumbers.contains(number)) {
                // This number is a winning number and hasn't been counted before
                uniqueNumbers.add(number);
                cardPoints = (cardPoints == 0) ? 1 : cardPoints * 2;
            }
        }

        return cardPoints;
    }

    public long getMatchedNumbers() {
        return numbers.stream().filter(winningNumbers::contains).count();
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", winningNumbers=" + winningNumbers +
                ", numbers=" + numbers +
                '}';
    }
}
