package aoc.day25;

import aoc.DayOld;

import java.util.List;

public class Day25 implements DayOld {

    private static final long cardPublicKey = 6930903L;
    private static final long doorPublicKey = 19716708L;

    @Override
    public String part1(List<String> input) {

        long encryptionKey;
        long value = 1;
        for(int loopSize = 1; true; loopSize++) {
            value = transform(value, loopSize-1, loopSize, 7);
            if(value == cardPublicKey) {
                encryptionKey = transform(1, 0, loopSize, doorPublicKey);
                break;
            }
        }
        return String.valueOf(encryptionKey);
    }

    private static long transform (long value, int start, int loopSize, long subjectNumber){
        for (int i = start; i < loopSize; i++) {
            value *= subjectNumber;
            value %= 20201227;
        }
        return value;
    }

    @Override
    public String part2(List<String> input) {
        return "Nothing here ! Merry Christmass !";
    }
}
