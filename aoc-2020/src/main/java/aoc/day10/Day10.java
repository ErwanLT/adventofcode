package aoc.day10;

import aoc.Day;
import aoc.utils.CastInputUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

import static java.util.Arrays.sort;

public class Day10 implements Day {

    private static long[] inputs;

    @Override
    public String part1(List<String> input) {
        castInput(input);
        sortInput();
        MapDay10<Long> diffs = new MapDay10<>();
        for(int i = 1; i<inputs.length; i++) {
            diffs.increment(inputs[i] - inputs[i - 1]);
        }
        long number = diffs.get(1L) * diffs.get(3L);

        return String.valueOf(number);
    }

    @Override
    public String part2(List<String> input) {
        castInput(input);
        sortInput();

        MapDay10<Long> nRoutes = new MapDay10<>();
        nRoutes.increment(inputs[inputs.length - 1]);
        for (int i = inputs.length - 2; i >= 0; i--) {
            for (int j = i + 1; j < inputs.length && j <= i + 3; j++) {
                if (inputs[j] - inputs[i] <= 3) {
                    nRoutes.increment(inputs[i], nRoutes.get(inputs[j]));
                }
            }
        }
        long number = nRoutes.get(0L);

        return String.valueOf(number);
    }

    private void castInput(List<String> input) {
        inputs = CastInputUtils.castInputToLongArray(input);
    }

    private static void sortInput() {
        sort(inputs);
        inputs = ArrayUtils.add( inputs, inputs[inputs.length-1]+3);
        inputs = ArrayUtils.add(inputs, 0,0L);
    }
}
