package aoc.day02;

import aoc.Day;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day02 implements Day {

    @Override
    public String part1(List<String> input) {

        AtomicInteger horizontalPosition = new AtomicInteger();
        AtomicInteger depth = new AtomicInteger();

        input.forEach( i -> {
            String[] array = i.split(" ");
            Instruction instruction = new Instruction(array[0], Integer.parseInt(array[1]));
            switch (instruction.getDirection()){
                case "forward" :
                    horizontalPosition.addAndGet(instruction.getValue());
                    break;
                case "up" :
                    depth.addAndGet(-instruction.getValue());
                    break;
                case "down" :
                    depth.addAndGet(instruction.getValue());
                    break;
            }
        });

        return String.valueOf(horizontalPosition.get() * depth.get());
    }

    @Override
    public String part2(List<String> input) {
        AtomicInteger horizontalPosition = new AtomicInteger();
        AtomicInteger depth = new AtomicInteger();
        AtomicInteger aim = new AtomicInteger();

        input.forEach( i -> {
            String[] array = i.split(" ");
            Instruction instruction = new Instruction(array[0], Integer.parseInt(array[1]));
            switch (instruction.getDirection()){
                case "forward" :
                    horizontalPosition.addAndGet(instruction.getValue());
                    depth.addAndGet((aim.get() * instruction.getValue()));
                    break;
                case "up" :
                    aim.addAndGet(-instruction.getValue());
                    break;
                case "down" :
                    aim.addAndGet(instruction.getValue());
                    break;
            }
        });

        return String.valueOf(horizontalPosition.get() * depth.get());
    }
}
