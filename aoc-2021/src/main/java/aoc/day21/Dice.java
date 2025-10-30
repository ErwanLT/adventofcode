package aoc.day21;

public class Dice {
    int rolls;
    int value;
    private static int maxValue = 100;

    public int roll() {
        rolls++;
        value = value == maxValue ? 1 : value + 1;
        return value;
    }

    public int rollTreeTimes() {
        return roll() + roll() + roll();
    }

    public int getRolls() {
        return this.rolls;
    }

    public int getValue() {
        return this.value;
    }

    public void setRolls(int rolls) {
        this.rolls = rolls;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
