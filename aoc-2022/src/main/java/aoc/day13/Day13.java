package aoc.day13;

import aoc.Day;
import aoc.Either;
import aoc.parser.ParseUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Integer.parseInt;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.IntStream.range;

public class Day13 implements Day {

    private String inStr = "";

    @Override
    public String part1(List<String> input) {
        inStr = ParseUtils.castInputToString("\n", input);
        var in = inStr.split("\n\n");
        List<Integer> indices = new ArrayList<>();
        for(int i = 0; i<in.length; i++){
            String[] pair = in[i].split("\n");
            String left = pair[0];
            String right = pair[1];
            if(compare(node(left, findLevels(left)), node(right, findLevels(right))).orElse(false)){
                indices.add(i+1);
            }
        }
        var sum =  indices.stream().mapToInt(e -> e).sum();
        return String.valueOf(sum);
    }

    @Override
    public String part2(List<String> input) {
        inStr = inStr+"\n[[2]]\n[[6]]";
        inStr = inStr.replace("\n\n", "\n");
        var inArr = inStr.split("\n");
        var in = Arrays.stream(inArr)
                .map(this::node)
                .sorted((a, b) -> compare(a, b).map(c -> c ? -1 : 1).orElse(0))
                .toList();
        var sol = (in.indexOf(node(node(node(2)))) + 1) * (in.indexOf(node(node(node(6)))) + 1);
        return String.valueOf(sol);
    }

    private Node node(String s) {
        return node(s, findLevels(s));
    }

    private int[] findLevels(String str) {
        AtomicInteger l = new AtomicInteger();
        return str.chars().map(c -> l.addAndGet(c == '[' ? 1 : c == ']' ? -1 : 0)).toArray();
    }

    private Node node(String s, int[] levels) {
        if(s.charAt(0) >= '0' && s.charAt(0) <= '9') return node(parseInt(s));
        if(s.equals("[]")) return node(List.of());
        int[] commas = range(0, levels.length).filter(i -> i == 0 || i == levels.length - 1 || levels[i] == levels[0] && s.charAt(i) == ',').toArray();
        return node(range(1, commas.length).mapToObj(i -> node(s.substring(commas[i-1]+1, commas[i]))).toList());
    }

    private Optional<Boolean> compare(Node a, Node b) {
        if(a.getValue().isB() && b.getValue().isB()) {
            int na = a.getValue().getB();
            int nb = b.getValue().getB();
            if(na < nb) return of(true);
            else if(na > nb) return of(false);
            else return empty();
        } else if(a.getValue().isA() && b.getValue().isA()) {
            List<Node> na = a.getValue().getA();
            List<Node> nb = b.getValue().getA();
            if(na.isEmpty() && !nb.isEmpty()) return of(true);
            else if(!na.isEmpty() && nb.isEmpty()) return of(false);
            else if(na.isEmpty() && nb.isEmpty()) return empty();
            else return compare(na.get(0), nb.get(0)).or(() -> compare(node(na.subList(1, na.size())), node(nb.subList(1, nb.size()))));
        }
        else if(a.getValue().isA()) return compare(a, node(b));
        else return compare(node(a), b);
    }

    private Node node(List<Node> nodes) {
        return new Node(new Either<>(nodes, null));
    }

    private Node node(int n) {
        return new Node(new Either<>(null, n));
    }

    private Node node(Node n) {
        return node(List.of(n));
    }
}
