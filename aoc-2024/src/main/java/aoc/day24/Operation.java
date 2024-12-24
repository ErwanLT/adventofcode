package aoc.day24;

import java.util.Map;

public record Operation(String wire1, String instruction, String wire2, String wireOutput){

    public String evaluate(Map<String, String> wireValues) {
        String value1 = wireValues.get(wire1);
        String value2 = wireValues.get(wire2);
        return switch (instruction) {
            case "AND" -> (value1.equals("1") && value2.equals("1")) ? "1" : "0";
            case "OR" -> (value1.equals("1") || value2.equals("1")) ? "1" : "0";
            case "XOR" -> value1.equals(value2) ? "0" : "1";
            default -> throw new IllegalStateException("Unexpected instruction: " + instruction);
        };
    }
}