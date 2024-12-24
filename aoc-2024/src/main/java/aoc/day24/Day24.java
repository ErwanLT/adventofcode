package aoc.day24;

import aoc.Day2024;


import java.util.ArrayList;
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
        return null;
    }
}
