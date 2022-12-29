package aoc.day25;

import aoc.Day;

import java.util.List;

import static java.lang.Math.toIntExact;

public class Day25 implements Day {

    List<Character> digits = List.of('=', '-', '0', '1', '2');

    @Override
    public String part1(List<String> input) {
        long res = input.stream().mapToLong(this::decimal).sum();
        StringBuilder out = new StringBuilder();
        while(res > 0) {
            int n = toIntExact((res + 2) % 5);
            res = (res + 2) / 5;
            out.insert(0, digits.get(n));
        }
        return out.toString();
    }

    private long decimal(String s) {
        long num = 0;
        for(int i = 0; i< s.length(); i++) {
            char c = s.charAt(s.length()-1-i);
            long n = digits.indexOf(c) - 2L;
            long rad = (long)Math.pow(5,i);
            num+=n*rad;
        }
        return num;
    }

    @Override
    public String part2(List<String> input) {
        return "That's all folk!";
    }
}
