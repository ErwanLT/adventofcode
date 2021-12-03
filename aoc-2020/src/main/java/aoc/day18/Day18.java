package aoc.day18;

import aoc.Day;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Day18 implements Day {
    @Override
    public String part1(List<String> input) {
        return String.valueOf(getSolution(true, input));
    }

    @Override
    public String part2(List<String> input) {
        return String.valueOf(getSolution(false, input));
    }

    public static long resolveExpression(StringBuilder s, boolean simpleMath){
        var a = solve(s, simpleMath);
        return getSolution(a.getRight(), s, a.getLeft(), simpleMath);
    }

    private static long getSolution(boolean simpleMath, List<String> inputLines){
        return inputLines.stream().mapToLong(i -> resolveExpression(new StringBuilder(i), simpleMath)).sum();
    }

    private static Pair<Long, Integer> solve(StringBuilder s, boolean simpleMath){
        long leftHand;
        int i = s.length()-2;
        if(s.charAt(s.length()-1) == ')'){
            for(int nBrackets = 1; nBrackets>0; i--){
                if(s.charAt(i) == '(') nBrackets--;
                else if(s.charAt(i) == ')') nBrackets++;
            }
            i++;
            leftHand = resolveExpression(new StringBuilder(s.substring(i+1, s.length()-1)), simpleMath);
        } else {
            leftHand = Long.parseLong(s.substring(s.length()-1, s.length()));
            i = s.length()-1;
        }
        return Pair.of(leftHand, i);
    }

    private static long getSolution(int i, StringBuilder s, long leftHand, boolean simpleMath){
        if(i>0) {
            char operator = s.charAt(i-2);
            StringBuilder leftSide = new StringBuilder(s.substring(0, i-3));
            if (operator == '*'){
                return resolveExpression(new StringBuilder(s.substring(0, i-3)), simpleMath) * leftHand;
            }
            else if (operator == '+'){
                if(simpleMath) {
                    return resolveExpression(new StringBuilder(s.substring(0, i-3)), true) + leftHand;
                } else {
                    var sol = solve(leftSide, false);
                    return getSolution(sol.getRight(), leftSide, sol.getKey() + leftHand, false);
                }
            }
        } else if(i == 0){
            return leftHand;
        }
        throw new IllegalStateException();
    }
}
