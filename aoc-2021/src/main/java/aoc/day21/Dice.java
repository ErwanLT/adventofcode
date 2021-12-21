package aoc.day21;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dice {
    int rolls;
    int value;
    private static int maxValue =  100;

    public int roll() {
        rolls++;
        value = value == maxValue ? 1 : value + 1;
        return value;
    }

    public int rollTreeTimes() {
        return roll() + roll() + roll();
    }
}
