package aoc.day15;

import aoc.DayOld;
import aoc.parser.ParseUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.IntStream.range;

public class Day15 implements DayOld {

    private static long[] inputs;

    @Override
    public String part1(List<String> input) {
        castInput(input);
        return String.valueOf(getNumber(2020L));
    }

    @Override
    public String part2(List<String> input) {
        castInput(input);
        return String.valueOf(getNumber(30000000L));
    }

    private void castInput(List<String> input) {
        inputs = ParseUtils.castInputToLongArray(input);
    }

    private static long getNumber(long position){
        Map<Long, Long> turnNumbers = new HashMap<>();
        range(0, inputs.length-1).forEach(i -> turnNumbers.put(inputs[i], (long)i));
        long lastNumber = inputs[inputs.length-1];
        for(long turnNumber = turnNumbers.size(); turnNumber <= position -2L; turnNumber++){
            long newLastNumber = turnNumbers.containsKey(lastNumber) ? turnNumber - turnNumbers.get(lastNumber) : 0;
            turnNumbers.put(lastNumber, turnNumber);
            lastNumber = newLastNumber;
        }
        return lastNumber;
    }
}
