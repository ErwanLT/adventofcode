package aoc.day14;

import aoc.DayOld;

import java.util.*;

import static java.lang.Long.parseLong;
import static java.lang.Long.toBinaryString;
import static java.util.stream.IntStream.range;

public class Day14 implements DayOld {

    private static Instruction[] instructions;

    @Override
    public String part1(List<String> input) {
        getInstructionFromInput(input);

        Map<Long, Long> memory = new HashMap<>();
        String currentMask = "";
        for(Instruction i : instructions){
            Optional<Memory> mem = i.getMem();
            if(mem.isPresent()){
                StringBuilder bin = binWithLength(mem.get().value, currentMask.length());
                String finalCurrentMask = currentMask;
                range(0, bin.length())
                        .filter(j -> finalCurrentMask.charAt(j) != 'X')
                        .forEach(j -> bin.setCharAt(j, finalCurrentMask.charAt(j)));
                memory.put(mem.get().index, parseLong(bin.toString(), 2));
            } else currentMask = i.value;
        }
        long sum = memory.values().stream().mapToLong(e -> e).sum();

        return String.valueOf(sum);
    }

    @Override
    public String part2(List<String> input) {
        getInstructionFromInput(input);

        Map<Long, Long> memory = new HashMap<>();
        String currentMask = "";
        for(Instruction i : instructions){
            Optional<Memory> mem = i.getMem();
            if(mem.isPresent()){
                StringBuilder bin = binWithLength(mem.get().index, currentMask.length());
                List<Integer> floaters = applyMask(currentMask, bin);
                fillMemory(memory, mem, bin, floaters);
            } else {
                currentMask = i.value;
            }
        }
        long sum = memory.values().stream().mapToLong(e -> e).sum();

        return String.valueOf(sum);
    }

    private static void getInstructionFromInput(List<String> inputs) {
        instructions = inputs.stream().map(s -> Utils.readString(s, "%s = %s", Instruction.class)).toArray(Instruction[]::new);
    }

    private static StringBuilder binWithLength(long val, int s) {
        StringBuilder bin = new StringBuilder(toBinaryString(val));
        while (bin.length() < s) {
            bin.insert(0, '0');
        }
        return bin;
    }

    private static void fillMemory(Map<Long, Long> memory, Optional<Memory> mem, StringBuilder bin, List<Integer> floaters) {
        StringBuilder binary;
        for(long j = 0; (binary = binWithLength(j, floaters.size())).length() == floaters.size(); j++){
            for(int k = 0; k< floaters.size(); k++){
                bin.setCharAt(floaters.get(k), binary.charAt(k));
            }
            memory.put(parseLong(bin.toString(), 2), mem.get().value);
        }
    }

    private static List<Integer> applyMask(String currentMask, StringBuilder bin) {
        List<Integer> floaters = new ArrayList<>();
        for(int j = 0; j< bin.length(); j++){
            char c = currentMask.charAt(j);
            if(c=='X') floaters.add(j);
            else if(c == '1') bin.setCharAt(j, c);
        }
        return floaters;
    }
}
