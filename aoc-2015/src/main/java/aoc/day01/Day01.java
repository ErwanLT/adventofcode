package aoc.day01;

import aoc.Day;

import java.util.List;

public class Day01 implements Day {

    private static char[] inputArray;
    private static int floor = 0;

    @Override
    public String part1(List<String> input) {
        inputArray = input.get(0).toCharArray();

        for (char c : inputArray) {
            if(c == '('){
                floor++;
            } else {
                floor--;
            }
        }

        return String.valueOf(floor);
    }

    @Override
    public String part2(List<String> input) {
        floor=0;
        int position = 0;
        for (int i = 0; i<inputArray.length; i++){
            if(inputArray[i]=='('){
                floor++;
            } else {
                floor--;
            }
            if(floor == -1){
                position = i+1;
                break;
            }
        }
        return String.valueOf(position);
    }
}
