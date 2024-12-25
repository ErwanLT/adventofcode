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
        parseInitialValues(input[0]);
        parseInstructions(input[1]);
    }

    public static void main(String[] args) {
        new Day24().printParts();
    }

    @Override
    public Object part1() {
        resolveInstructions();
        String binaryOutput = computeBinaryOutput();
        printer.printInfo(binaryOutput);
        String reversedOutput = reverseBinary(binaryOutput);
        printer.printInfo(reversedOutput);
        return Long.parseLong(reversedOutput, 2);
    }

    private String computeBinaryOutput() {
        return wireValues.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("z"))
                .map(Map.Entry::getValue)
                .collect(Collectors.joining());
    }

    private String reverseBinary(String binaryOutput) {
        return new StringBuilder(binaryOutput).reverse().toString();
    }

    private void resolveInstructions() {
        List<Operation> unresolvedInstructions = new ArrayList<>(instructions);
        while (!unresolvedInstructions.isEmpty()) {
            unresolvedInstructions.removeIf(operation -> {
                if (wireValues.containsKey(operation.wire1()) && wireValues.containsKey(operation.wire2())) {
                    wireValues.put(operation.wireOutput(), operation.evaluate(wireValues));
                    return true;
                }
                return false;
            });
        }
    }

    private void parseInstructions(String input) {
        Arrays.stream(input.split("\n"))
                .map(line -> readString(line, "%s %s %s -> %s", Operation.class))
                .forEach(instructions::add);
    }

    private void parseInitialValues(String input) {
        Arrays.stream(input.split("\n"))
                .map(line -> line.split(": "))
                .forEach(parts -> wireValues.put(parts[0], parts[1]));
    }

    @Override
    public Object part2() {
        List<String> input = Arrays.asList(dayStrings());
        Map<String, String> registers = parseRegisters(input);
        List<String> swaps = processRegisters(registers);
        return swaps.stream().sorted().collect(Collectors.joining(","));
    }

    private List<String> processRegisters(Map<String, String> registers) {
        List<String> swaps = new ArrayList<>();
        int index = 0;
        String current = "";

        while (registers.containsKey(formatRegister("x", index))) {
            String x = formatRegister("x", index);
            String y = formatRegister("y", index);
            String z = formatRegister("z", index);

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

        return swaps;
    }

    private String formatRegister(String prefix, int index) {
        return String.format("%s%02d", prefix, index);
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

    private static Map<String, String> parseRegisters(List<String> input) {
        Map<String, String> registers = new HashMap<>();
        int emptyLineIndex = input.indexOf("");

        input.subList(0, emptyLineIndex).forEach(line -> {
            String[] parts = line.split(": ");
            registers.put(parts[0], parts[1]);
        });

        input.subList(emptyLineIndex + 1, input.size()).forEach(line -> {
            String[] parts = line.split(" -> ");
            registers.put(parts[1], parts[0]);
        });

        return registers;
    }
}
