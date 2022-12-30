package aoc.day07;

import aoc.DayOld;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day07 implements DayOld {

    static Function<String, Integer> notOperation = s -> (~getValue(s));

    static BiFunction<String, String, Integer> andOperation = (arg1, arg2) -> (getValue(arg1) & getValue(arg2));

    static BiFunction<String, String, Integer> orOperation = (arg1, arg2) -> (getValue(arg1) | getValue(arg2));

    static BiFunction<String, String, Integer> lShiftOperation = (arg1, arg2) -> (getValue(arg1) << getValue(arg2));

    static BiFunction<String, String, Integer> rShiftOperation = (arg1, arg2) -> (getValue(arg1) >> getValue(arg2));

    private static final Pattern singleArgumentInstructionPattern = Pattern.compile("(.+) (.+)");

    private static final Pattern doubleArgumentInstructionPattern = Pattern.compile("(.+) (.+) (.+)");

    private static Map<String, String> circuit = new HashMap<>();

    private static int part1Solution = 0;

    @Override
    public String part1(List<String> input) {
        initCircuit(input);
        part1Solution = getValue("a");
        return String.valueOf(part1Solution);
    }

    @Override
    public String part2(List<String> input) {
        initCircuit(input);
        circuit.put("b", String.valueOf(part1Solution));
        int part2Solution = getValue("a");
        return String.valueOf(part2Solution);
    }

    private static void initCircuit(List<String> input) {
        circuit.clear();
        input.stream().map(str -> str.split(" -> ")).forEach(str -> setValue(str[1], str[0]));
    }

    protected static void setValue(String key, String instruction) {
        circuit.put(key, instruction);
    }

    private static int getValue(String arg) {
        int value = 0;
        if (arg.matches("\\d+")) {
            value = Integer.valueOf(arg);
        } else {
            value = processWiringInstruction(circuit.get(arg));
            circuit.put(arg, String.valueOf(value));
        }
        return value;
    }

    public static int processWiringInstruction(String instruction) {
        Matcher matcher = doubleArgumentInstructionPattern.matcher(instruction);
        Matcher matcher1 = singleArgumentInstructionPattern.matcher(instruction);
        int result = 0;
        if (matcher.find()) {
            String arg1 = matcher.group(1);
            String operation = matcher.group(2);
            String arg2 = matcher.group(3);
            switch (operation) {
                case "AND":
                    result = andOperation.apply(arg1, arg2);
                    break;
                case "OR":
                    result = orOperation.apply(arg1, arg2);
                    break;
                case "LSHIFT":
                    result = lShiftOperation.apply(arg1, arg2);
                    break;
                case "RSHIFT":
                    result = rShiftOperation.apply(arg1, arg2);
                    break;
                default:
                    break;
            }
        } else if (matcher1.find()) {
            String operation = matcher1.group(1);
            String arg1 = matcher1.group(2);
            if (operation.equals("NOT")) {
                result = notOperation.apply(arg1);
            }
        } else {
            String arg1 = instruction;
            result = getValue(arg1);
        }
        return result;
    }
}
