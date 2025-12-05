package aoc.day02;

import aoc.Day2025;
import aoc.Range;

import java.util.ArrayList;
import java.util.List;

public class Day02 extends Day2025 {

    private final List<Range> ranges;

    public Day02() {
        super(2, "Gift Shop");
        this.ranges = new ArrayList<>();

        String line = day();
        String[] rangeStrings = line.split(",");
        for (String rangeStr : rangeStrings) {
            String[] parts = rangeStr.split("-");
            this.ranges.add(new Range(
                    Long.parseLong(parts[0]),
                    Long.parseLong(parts[1])
            ));
        }
    }

    @Override
    public Object part1() {
        return compute(false);
    }

    @Override
    public Object part2() {
        return compute(true);
    }

    private long compute(boolean part2Mode) {
        long total = 0;
        for (Range r : ranges) {
            for (long i = r.min(); i <= r.max(); i++) {
                if (isInvalid(i, part2Mode)) {
                    total += i;
                }
            }
        }
        return total;
    }

    /**
     * Critère commun — part2Mode détermine la finesse de l’analyse.
     */
    private boolean isInvalid(long number, boolean part2Mode) {
        String s = String.valueOf(number);
        int len = s.length();

        if (part2Mode) {
            // Mode "répétition complète" — Part 2
            if (len < 2) {
                return false;
            }
            for (int subLen = 1; subLen <= len / 2; subLen++) {
                if (len % subLen == 0) {
                    String seq = s.substring(0, subLen);
                    if (s.equals(seq.repeat(len / subLen))) {
                        return true;
                    }
                }
            }
            return false;

        } else {
            // Mode "deux moitiés identiques" — Part 1
            if (len % 2 != 0) {
                return false;
            }
            int half = len / 2;
            return s.substring(0, half).equals(s.substring(half));
        }
    }
}

