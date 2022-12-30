package aoc.day08;

import aoc.DayOld;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.stream;

public class Day08 implements DayOld {

    private static int accumulator;

    @Override
    public String part1(List<String> inputs) {
        Instruction[] input = inputs.stream().map(s -> s.replace("+", "")).map(s -> s.split(" ")).map(s -> new Instruction(s[0], Long.parseLong(s[1]))).toArray(Instruction[]::new);
        Set<Integer> alreadyVisitedIndex = new HashSet<>();
        int op = 0;
        while(alreadyVisitedIndex.add(op)){
            String operation = input[op].operation;
            long number = input[op].number;
            switch(operation){
                case "acc":
                    accumulator+=number;
                    op++;
                    break;
                case "jmp":
                    op+=number;
                    break;
                case "nop":
                    op++;
                    break;
                default: throw new IllegalStateException();
            }
        }

        return String.valueOf(accumulator);
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(compute(input));
    }

    private static Object compute(List<String> inputs) {
        for(int i = 0; i<223; i++) {
            Instruction[] instructions = inputs.stream().map(s -> s.replace("+", "")).map(s -> s.split(" ")).map(s -> new Instruction(s[0], Long.parseLong(s[1]))).toArray(Instruction[]::new);
            replace(instructions, "jmp", "nop", i);
            long acc = 0L;
            Set<Integer> visited = new HashSet<>();
            int op = 0;
            while (visited.add(op)) {
                String operation = instructions[op].operation;
                long number = instructions[op].number;
                switch (operation) {
                    case "acc":
                        acc += number;
                        op++;
                        break;
                    case "jmp":
                        op += number;
                        break;
                    case "nop":
                        op++;
                        break;
                    default:
                        throw new IllegalStateException();
                }
                if(op == instructions.length) {
                    return acc;
                }
                if(op > instructions.length) {
                    break;
                }
            }

        }
        return "FAIL";
    }

    public static void replace(Instruction[] input, String instruction, String replacement, int occurrence){
        Instruction[] those = stream(input).filter(e -> e.operation.equals(instruction)).toArray(Instruction[]::new);
        those[occurrence].operation = replacement;
    }
}
