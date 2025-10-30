package aoc.day04;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Card {
    public String cardNumber;

    public List<Integer> winningNumbers;

    public List<Integer> numbers;

    public Card(String cardNumber, List<Integer> winningNumbers, List<Integer> numbers) {
        this.cardNumber = cardNumber;
        this.winningNumbers = winningNumbers;
        this.numbers = numbers;
    }

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

    public String getCardNumber() {
        return this.cardNumber;
    }

    public List<Integer> getWinningNumbers() {
        return this.winningNumbers;
    }

    public List<Integer> getNumbers() {
        return this.numbers;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setWinningNumbers(List<Integer> winningNumbers) {
        this.winningNumbers = winningNumbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }
}
