package aoc.day02;

import aoc.Day;

import java.util.ArrayList;
import java.util.List;

public class Day02 implements Day {

    private static final List<Instruction> instructions = new ArrayList<>();

    @Override
    public String part1(List<String> input) {

        parseInput(input);

        int horizontalPosition = 0;
        int depth = 0;

        for (Instruction instruction: instructions) {
            switch (instruction.getDirection()){
                case "forward" :
                    horizontalPosition += instruction.getValue();
                    break;
                case "up" :
                    depth -= instruction.getValue();
                    break;
                case "down" :
                    depth += instruction.getValue();
                    break;
            }
        }

        return String.valueOf(horizontalPosition * depth);
    }

    private void parseInput(List<String> input) {
        input.forEach( s -> {
            String[] array = s.split(" ");
            Instruction i = new Instruction(array[0], Integer.parseInt(array[1]));
            instructions.add(i);
        });
    }

    @Override
    public String part2(List<String> input) {
        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;

        for (Instruction instruction: instructions) {
            switch (instruction.getDirection()){
                case "forward" :
                    horizontalPosition += instruction.getValue();
                    depth += (aim * instruction.getValue());
                    break;
                case "up" :
                    aim -= instruction.getValue();
                    break;
                case "down" :
                    aim += instruction.getValue();
                    break;
            }
        }

        return String.valueOf(horizontalPosition * depth);
    }
}
