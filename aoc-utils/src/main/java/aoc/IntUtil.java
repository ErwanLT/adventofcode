package aoc;

public class IntUtil {
    public static Integer parseUnsignedInteger(String s) {
        return Integer.parseInt(s.replaceAll("\\D+", ""));
    }

    public static Integer parseSignedInteger(String s) {
        return Integer.parseInt(s.replaceAll("[^\\d-]+", ""));
    }
}
