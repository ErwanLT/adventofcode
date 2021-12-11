package aoc.parser;

import java.util.List;

public class ParseUtils {

    public static long[] castInputToLongArray(List<String> input){
        return input.stream().mapToLong(Long::parseLong).toArray();
    }

    public static int[] castInputToIntArray(List<String> input){
        return input.stream().mapToInt(Integer::parseInt).toArray();
    }

    public static String[] castInoutToStringArray(List<String> input){
        return input.toArray(new String[input.size()]);
    }

    public static char[][] castInputToBiCharArray(List<String> input) {
        return input.stream().map(String::toCharArray).toArray(char[][]::new);
    }

    public static String castInputToString(CharSequence delimiter, List<String> input) {
        return String.join(delimiter, input);
    }

    public static int[][] castInputToBiIntarray(List<String> input) {
        return input.stream().map(row -> row.chars().map(i -> i - '0').toArray())
                .toArray(int[][]::new);
    }
}
