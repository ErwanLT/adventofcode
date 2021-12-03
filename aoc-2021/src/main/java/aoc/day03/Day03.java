package aoc.day03;

import aoc.Day;
import aoc.ParseUtils;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Day03 implements Day {

    private static String[] in;

    @Override
    public String part1(List<String> input) {

        in = ParseUtils.castInoutToStringArray(input);
        StringBuilder most = new StringBuilder();
        StringBuilder least = new StringBuilder();

        for(int i = 0; i<in[0].length(); i++){
            if(moreZeros(in, i)){
                most.append("0");
                least.append("1");
            } else {
                most.append("1");
                least.append("0");
            }
        }
        return String.valueOf(parseInt(most.toString(), 2) * parseInt(least.toString(), 2));
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(parseInt(findVal(input, true, 0).get(0), 2) * parseInt(findVal(input, false, 0).get(0), 2));
    }

    private boolean moreZeros(String[] in, int i) {
        int ones = 0, zeros = 0;
        for(String s : in){
            if(s.charAt(i) == '1'){
                ones++;
            } else {
                zeros++;
            }
        }
        return ones<zeros;
    }

    private List<String> findVal(List<String> in, boolean high, int pos) {
        List<String> res = new ArrayList<>(in);
        res.removeIf(e -> e.charAt(pos) == (!moreZeros(in.toArray(String[]::new), pos)^high ? '1' : '0'));
        if(res.size() == 1) return res;
        return findVal(res, high, pos+1);
    }
}
