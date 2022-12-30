package aoc.day01;

import aoc.DayOld;
import aoc.parser.ParseUtils;

import java.util.List;

public class Day01 implements DayOld {

    private static int[] inputs;

    @Override
    public String part1(List<String> input) {
        inputs = ParseUtils.castInputToIntArray(input);
        int mesurement = 0;
        for (int i = 1; i<inputs.length; i++){
            if(inputs[i-1] < inputs[i]){
                mesurement++;
            }
        }
        return String.valueOf(mesurement);
    }

    @Override
    public String part2(List<String> input) {
        int mesurement = 0;
        for(int i = 3; i<inputs.length; i++){
            if(inputs[i-3] + inputs[i-2] + inputs[i-1] < inputs[i] + inputs[i-2] + inputs[i-1]){
                mesurement++;
            }
        }
        return String.valueOf(mesurement);
    }
}
