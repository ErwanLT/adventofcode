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

        return times.stream()
                .mapToLong(raceTime -> countWaysToWin(raceTime, records.get(times.indexOf(raceTime))))
                .reduce(1, (a, b) -> a * b);
    }

    private long countWaysToWin(int raceTime, int record) {
        return java.util.stream.IntStream.range(1, raceTime)
                .filter(time -> (raceTime - time) * time > record)
                .count();
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
