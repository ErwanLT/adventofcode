package aoc.day23;

import aoc.Day;
import aoc.parser.ParseUtils;
import aoc.utils.CircularLinkedList;
import aoc.utils.Node;
import com.google.common.collect.Streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;

public class Day23 implements Day {

    private static int[] inputArray;

    @Override
    public String part1(List<String> input) {
        inputArray = ParseUtils.castInputToIntArray(input);
        long label = getSolution(true);
        return String.valueOf(label);
    }

    @Override
    public String part2(List<String> input) {
        inputArray = ParseUtils.castInputToIntArray(input);
        long label = getSolution(false);
        return String.valueOf(label);
    }

    private static long getSolution(boolean part1) {
        CircularLinkedList cups = new CircularLinkedList(
                Streams.concat(stream(inputArray), part1 ? IntStream.empty() : IntStream.rangeClosed(10, 1000000)
                ).toArray());
        for(int i = 0; i<(part1 ? 100 : 10000000); i++){
            int current = cups.current();
            int j;
            Node next = cups.currentNode().next;
            Node last = next.next.next;
            for(j = current - 2 + cups.size(); j>0; j--){
                int n = j % cups.size() + 1;
                if(next.value != n && next.next.value != n && last.value != n){
                    break;
                }
            }
            int d = j % cups.size() + 1;
            cups.insertAfter(next, last, d);
            cups.next();
        }
        cups.setCurrent(1);
        if(part1) return parseLong(stream(cups.next(8)).mapToObj(Integer::toString).collect(Collectors.joining()));
        int[] next = cups.next(2);
        return (long)next[0] * next[1];
    }
}
