package aoc.day02;

import aoc.Day;

import java.util.List;

public class Day02 implements Day {
    @Override
    public String part1(List<String> input) {
        int horizontalPosition = 0;
        int depth = 0;

        for (String command: input) {
            String[] inputCommand = command.split(" ");
            switch (inputCommand[0]){
                case "forward" :
                    horizontalPosition += Integer.parseInt(inputCommand[1]);
                    break;
                case "up" :
                    depth -= Integer.parseInt(inputCommand[1]);
                    break;
                case "down" :
                    depth += Integer.parseInt(inputCommand[1]);
                    break;
            }
        }

        return String.valueOf(horizontalPosition * depth);
    }

    @Override
    public String part2(List<String> input) {
        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;

        for (String command: input) {
            String[] inputCommand = command.split(" ");
            switch (inputCommand[0]){
                case "forward" :
                    horizontalPosition += Integer.parseInt(inputCommand[1]);
                    depth += (aim * Integer.parseInt(inputCommand[1]));
                    break;
                case "up" :
                    aim -= Integer.parseInt(inputCommand[1]);
                    break;
                case "down" :
                    aim += Integer.parseInt(inputCommand[1]);
                    break;
            }
        }

        return String.valueOf(horizontalPosition * depth);
    }
}
