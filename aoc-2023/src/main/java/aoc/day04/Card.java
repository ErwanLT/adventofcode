package aoc.day04;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Card {
    public String cardNumber;

    public List<Integer> winningNumbers;

    public List<Integer> numbers;

    public Card(String cardNumber, List<Integer> winningNumbers, List<Integer> myNumbers) {
        this.cardNumber = cardNumber;
        this.winningNumbers = winningNumbers;
        this.numbers = myNumbers;
    }

    public void addWinningNumber(String num){
        if(winningNumbers == null) {
            winningNumbers = new ArrayList<>();
        }
        winningNumbers.add(Integer.valueOf(num));
    }

    public void addMyNumber(String num){
        if(numbers == null) {
            numbers = new ArrayList<>();
        }
        numbers.add(Integer.valueOf(num));
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

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", winningNumbers=" + winningNumbers +
                ", numbers=" + numbers +
                '}';
    }
}
