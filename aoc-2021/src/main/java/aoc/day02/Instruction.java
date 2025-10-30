package aoc.day02;

public class Instruction {

    private String direction;

    private int value;

    public Instruction(String direction, int value) {
        this.direction = direction;
        this.value = value;
    }

    public String getDirection() {
        return this.direction;
    }

    public int getValue() {
        return this.value;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
