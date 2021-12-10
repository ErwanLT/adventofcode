package aoc.day10;

import aoc.Day;

import java.util.*;

public class Day10 implements Day {

    private static final Map<Character, Character> brackets = new HashMap<>();
    private static final Map<Character, Integer> weightPart1 = new HashMap<>();
    private static final Map<Character, Integer> weightPart2 = new HashMap<>();

    static {
        brackets.put(')', '(');
        brackets.put(']', '[');
        brackets.put('>', '<');
        brackets.put('}', '{');

        weightPart1.put('(', 1);
        weightPart1.put('[', 2);
        weightPart1.put('{', 3);
        weightPart1.put('<', 4);

        weightPart2.put(')', 3);
        weightPart2.put(']', 57);
        weightPart2.put('}', 1197);
        weightPart2.put('>', 25137);
    }

    @Override
    public String part1(List<String> input) {
        return String.valueOf(getScore(input, false).get(0));
    }

    @Override
    public String part2(List<String> input) {
        List<Long> scores = getScore(input,true);
        return String.valueOf(scores.stream().sorted().skip(scores.size()/2).findFirst().get());
    }

    private List<Long> getScore(List<String> input, boolean part) {
        Map<Character, Integer> p = part ? weightPart1 : weightPart2;
        List<Long> scores = new ArrayList<>();
        long score1 = 0;
        out: for(String line : input){
            Stack<Character> s = new Stack<>();
            for(Character c : line.toCharArray()){
                if(brackets.containsKey(c)){
                    if(!s.isEmpty()){
                        Character stackC = s.pop();
                        if(!brackets.get(c).equals(stackC)){
                            if(!part) score1+=p.get(c);
                            continue out;
                        }
                    }
                } else {
                    s.push(c);
                }
            }
            if(!part) continue;
            long score = 0;
            while(!s.isEmpty()){
                Character c = s.pop();
                score = (score * 5) + p.get(c);
            }
            scores.add(score);
        }
        if(!part) return List.of(score1);
        return scores;
    }
}
