package aoc.day21;

import aoc.Day2024;

public class Day21 extends Day2024 {


    public Day21() {
        super(21, "Keypad Conundrum");
    }

    public static void main(String[] args) {
        new Day21().printParts();
    }

    @Override
    public Object part1() {
        String[] codes = dayStrings();
        Keypads keyboards = new Keypads();
        int totalComplexity = 0;

        for (String code : codes) {
            int[] result1 = keyboards.calculateComplexity(code, 3);
            totalComplexity += result1[0] * result1[1];
        }
        return totalComplexity;
    }

    @Override
    public Object part2() {
        String[] codes = dayStrings();
        Keypads keyboards = new Keypads();
        int totalComplexity = 0;

        /*for (String code : codes) {
            printer.printInfo(code);
            int[] result1 = keyboards.calculateComplexity(code, 26);
            totalComplexity += result1[0] * result1[1];
        }*/
        return totalComplexity;
    }
}