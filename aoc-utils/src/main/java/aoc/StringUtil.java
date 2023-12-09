package aoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtil {
    public static Map<Character, Integer> tally(String string) {
        var characterCount = new HashMap<Character, Integer>();

        for (var c: string.toCharArray()) {
            characterCount.put(c, characterCount.getOrDefault(c, 0) + 1);
        }

        return characterCount;
    }

    public static List<Long> slongs(String s) {
        return Regex.matchAll("\\-?\\d+", s).stream().map(Long::parseLong).toList();
    }
}
