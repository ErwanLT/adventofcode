package aoc.day05;

import aoc.Day;
import aoc.parser.ParseUtils;

import java.util.*;
import java.util.stream.Collectors;

import static aoc.parser.ReadFormatedString.readString;
import static java.lang.Math.toIntExact;

public class Day05 implements Day {
    @Override
    public String part1(List<String> input) {
        List<Deque<Integer>> stacks = input();
        List<Move> moves = getMoves(input);
        for(Move m : moves) {
            for(int i = 0; i< m.getWhich(); i++) {
                int top = stacks.get(toIntExact(m.getFrom() -1)).removeLast();
                stacks.get(toIntExact(m.getTo() -1)).addLast(top);
            }
        }

        return stacks.stream().map(Deque::peekLast).map(e -> Character.toString((char)(int)e)).collect(Collectors.joining());
    }

    @Override
    public String part2(List<String> input) {
        List<Deque<Integer>> stacks = input();
        List<Move> moves = getMoves(input);
        for(Move m : moves) {
            List<Integer> toBeMoved = new ArrayList<>();
            for(int i = 0; i< m.getWhich(); i++) toBeMoved.add(0, stacks.get(toIntExact(m.getFrom() -1)).removeLast());
            toBeMoved.forEach(i -> stacks.get(toIntExact(m.getTo() -1)).addLast(i));
        }
        return stacks.stream().map(Deque::peekLast).map(e -> Character.toString((char)(int)e)).collect(Collectors.joining());
    }

    private List<Move> getMoves(List<String> input) {
        return ParseUtils.castInputToStream(input)
                .map(String::trim).map(s -> readString(s, "move %n from %n to %n", Move.class))
                .collect(Collectors.toList());
    }

    private static List<Deque<Integer>> input() {
        List<Deque<Integer>> stacks = new ArrayList<>();
        for(int i = 1; i<=9; i++){
            Deque<Integer> s = new ArrayDeque<>();
            switch (i) {
                case 1 -> "JHGMZNTF".chars().forEach(s::add);
                case 2 -> "VWJ".chars().forEach(s::add);
                case 3 -> "GVLJBTH".chars().forEach(s::add);
                case 4 -> "BPJNCDVL".chars().forEach(s::add);
                case 5 -> "FWSMPRG".chars().forEach(s::add);
                case 6 -> "GHCFBNVM".chars().forEach(s::add);
                case 7 -> "DHGMR".chars().forEach(s::add);
                case 8 -> "HNMVZD".chars().forEach(s::add);
                case 9 -> "GNFH".chars().forEach(s::add);
            }
            stacks.add(s);
        }
        return stacks;
    }
}
