package aoc.utils;

import java.util.List;

public class CastInputUtils {

    public static long[] castInputToLongArray(List<String> input){
        return input.stream().mapToLong(Long::parseLong).toArray();
    }

    public static int[] castInputToIntArray(List<String> input){
        return input.stream().mapToInt(Integer::parseInt).toArray();
    }

    public static char[][] castInputToBiCharArray(List<String> input) {
        return input.stream().map(String::toCharArray).toArray(char[][]::new);
    }
}
