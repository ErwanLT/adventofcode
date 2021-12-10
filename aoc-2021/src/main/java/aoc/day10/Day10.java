package aoc.day10;

import aoc.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Day10 implements Day {
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
        Map<Character, Character> m = Map.of(')', '(', ']', '[', '>', '<', '}', '{');
        Map<Character, Integer> p = part ? Map.of('(', 1, '[', 2, '{', 3, '<', 4) : Map.of(')', 3, ']', 57, '>', 25137, '}', 1197);
        List<Long> scores = new ArrayList<>();
        long score1 = 0;
        out: for(String line : input){
            Stack<Character> s = new Stack<>();
            for(Character c : line.toCharArray()){
                if(m.containsKey(c)){
                    if(!s.isEmpty()){
                        Character stackC = s.pop();
                        if(!m.get(c).equals(stackC)){
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
