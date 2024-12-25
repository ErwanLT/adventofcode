package aoc.day24;

import aoc.Day2024;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static aoc.parser.DataMapper.readString;

public class Day24 extends Day2024 {

    private final Map<String, String> wireValues = new TreeMap<>();
    private final List<Operation> instructions = new ArrayList<>();

    public Day24() {
        super(24, "Crossed Wires");
        var input = day().split("\n\n");
        getInitalValues(input[0]);
        getInstructions(input[1]);
    }

    public static void main(String[] args) {
        new Day24().printParts();
    }

    @Override
    public Object part1() {
        resolveInstruction();
        var binaryOutput = wireValues.entrySet().stream()
                .filter(e -> e.getKey().startsWith("z"))
                .map(e -> String.valueOf(e.getValue()))
                .collect(Collectors.joining());
        printer.printInfo(binaryOutput);
        binaryOutput = reverseBinary(binaryOutput);
        printer.printInfo(binaryOutput);
        return Long.parseLong(binaryOutput, 2);
    }

    private String reverseBinary(String binaryOutput) {
        return new StringBuilder(binaryOutput).reverse().toString();
    }

    private void resolveInstruction() {
        List<Operation> instructionsCopy = new ArrayList<>(instructions);
        while (!instructionsCopy.isEmpty()){
            var iterator = instructionsCopy.iterator();
            while (iterator.hasNext()) {
                Operation operation = iterator.next();
                if (wireValues.containsKey(operation.wire1()) && wireValues.containsKey(operation.wire2())) {
                    wireValues.put(operation.wireOutput(), operation.evaluate(wireValues));
                    iterator.remove();
                }
            }
        }
    }

    private void getInstructions(String s) {
        var lines = s.split("\n");
        for(String l : lines){
            instructions.add(readString(l, "%s %s %s -> %s", Operation.class));
        }
    }

    private void getInitalValues(String s) {
        var lines = s.split("\n");
        for(String l : lines){
            String[] parts = l.split(": ");
            wireValues.put(parts[0], parts[1]);
        }
    }

    @Override
    public Object part2() {
        var input = Arrays.asList(dayStrings());
        Map<String, String> registers = getRegisters(input);
        List<String> swaps = new ArrayList<>();
        int index = 0;
        String current = "";
        while (registers.containsKey(String.format("x%02d", index))) {
            String x = String.format("x%02d", index);
            String y = String.format("y%02d", index);
            String z = String.format("z%02d", index);
            if (index == 0) {
                current = findExpression(registers, x, "AND", y);
            } else {
                String xor = findExpression(registers, x, "XOR", y);
                String and = findExpression(registers, x, "AND", y);
                String next = findExpression(registers, xor, "XOR", current);
                if (next == null) {
                    swaps.addAll(List.of(xor, and));
                    swapRegisters(registers, xor, and);
                    index = 0;
                    continue;
                }
                if (!next.equals(z)) {
                    swaps.addAll(List.of(next, z));
                    swapRegisters(registers, next, z);
                    index = 0;
                    continue;
                }
                next = findExpression(registers, xor, "AND", current);
                current = findExpression(registers, and, "OR", next);
            }
            index++;
        }
        return swaps.stream().sorted().collect(Collectors.joining(","));
    }

    private static String findExpression(Map<String, String> registers, String op1, String op, String op2) {
        return registers.entrySet().stream()
                .filter(entry -> {
                    String value = entry.getValue();
                    return value.equals(op1 + " " + op + " " + op2) || value.equals(op2 + " " + op + " " + op1);
                })
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    private static void swapRegisters(Map<String, String> registers, String register1, String register2) {
        String temp = registers.put(register1, registers.get(register2));
        registers.put(register2, temp);
    }

    private static Map<String, String> getRegisters(List<String> input) {
        Map<String, String> registers = new HashMap<>();
        for (String line : input.subList(0, input.indexOf(""))) {
            String[] parts = line.split(": ");
            registers.put(parts[0], parts[1]);
        }
        for (String line : input.subList(input.indexOf("") + 1, input.size())) {
            String[] parts = line.split(" -> ");
            registers.put(parts[1], parts[0]);
        }
        return registers;
    }
}
