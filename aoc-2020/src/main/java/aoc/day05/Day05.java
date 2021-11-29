package aoc.day05;

import aoc.Day;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day05 implements Day {
    @Override
    public String part1(List<String> input) {
        int seatId = seatID(input).stream().mapToInt(i->i).max().getAsInt();
        return String.valueOf(seatId);
    }

    @Override
    public String part2(List<String> input) {
        Set<Integer> l = seatID(input);
        int mySeatID = l.stream().mapToInt(e -> e).filter(n -> l.contains(n) && l.contains(n+2) && !l.contains(n+1)).sum() + 1;
        return String.valueOf(mySeatID);
    }

    private static Set<Integer> seatID(List<String> inputs) {
        Set<Integer> l = new HashSet<>();
        for(String s : inputs){
            int minRow = 0;
            int maxRow = 127;
            int minColumn = 0;
            int maxColumn = 7;
            for(char c : s.toCharArray()){
                if(c == 'F'){
                    maxRow -= (maxRow-minRow+1)/2;
                } else if(c == 'B'){
                    minRow += (maxRow-minRow+1)/2;
                } else if(c == 'L'){
                    maxColumn -= (maxColumn-minColumn+1)/2;
                } else if(c == 'R'){
                    minColumn += (maxColumn-minColumn+1)/2;
                }
            }
            l.add(maxRow * 8 + maxColumn);
        }
        return l;
    }
}
