package aoc.day06;

import aoc.Day2023;
import aoc.ListUtils;
import com.google.common.base.Joiner;

import java.util.List;

public class Day06 extends Day2023 {

    List<Integer> times;
    List<Integer> records;

    public Day06() {
        super(6);
        var lines = dayStrings();
        times = ListUtils.extractUnsignedIntegers(lines[0]);
        records = ListUtils.extractUnsignedIntegers(lines[1]);
    }

    public static void main(String[] args){
        new Day06().printParts();
    }

    @Override
    public Object part1() {

        Long product = 1L;

        for (var i = 0; i < times.size(); i++) {
            var raceTime = times.get(i);
            var waysToWinCount = 0;

            for (var time = 1; time < raceTime; time++) {
                var distance = (raceTime - time) * time;

                if (distance > records.get(i)) {
                    waysToWinCount++;
                }
            }

            product *= waysToWinCount;
        }
        return product;
    }

    @Override
    public Object part2() {
        var totalTime = Long.parseLong(Joiner.on("").join(times));
        var totalRecord = Long.parseLong(Joiner.on("").join(records));
        var waysToWinCount = 0;

        for (var currentTime = 1L; currentTime < totalTime; currentTime++) {
            var distance = (totalTime - currentTime) * currentTime;

            if (distance > totalRecord) {
                waysToWinCount++;
            } else if (distance < totalRecord && waysToWinCount > 0) {
                break;
            }
        }

        return String.valueOf(waysToWinCount);
    }
}
