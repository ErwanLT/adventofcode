package aoc.day08;

public class Instruction {
    public String from;
    public String left;
    public String right;

    public Instruction(String from, String left, String right) {
        this.from = from;
        this.left = left;
        this.right = right;
    }
}
