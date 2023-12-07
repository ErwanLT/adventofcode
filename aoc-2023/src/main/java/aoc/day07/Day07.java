package aoc.day07;

import aoc.Day2023;
import aoc.customMap.CountMap;

import java.util.TreeMap;

import static aoc.AOCUtils.zip;
import static aoc.parser.DataMapper.readString;
import static java.util.stream.IntStream.range;

public class Day07 extends Day2023 {


    public Day07() {
        super(7);
    }

    public static void main(String[] args){
        new Day07().printParts();
    }

    @Override
    public Object part1() {
        long sum = 0L;
        var handsScores = new TreeMap<Hand, Integer>(Hand::compare);
        var place = 1;

        for (var line: dayStrings()) {
            var parts = line.split(" ");
            handsScores.put(new Hand(parts[0], false), Integer.parseInt(parts[1]));
        }

        for (var handScore: handsScores.entrySet()) {
            sum += (long) place++ * handScore.getValue();
        }

        return sum;
    }

    @Override
    public Object part2() {
        long sum = 0L;
        var handsScores = new TreeMap<Hand, Integer>(Hand::compare);
        var place = 1;

        for (var line: dayStrings()) {
            var parts = line.split(" ");
            handsScores.put(new Hand(parts[0], true), Integer.parseInt(parts[1]));
        }

        for (var handScore: handsScores.entrySet()) {
            sum += (long) place++ * handScore.getValue();
        }
        return sum;
    }
}
