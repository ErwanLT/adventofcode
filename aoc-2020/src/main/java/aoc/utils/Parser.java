package aoc.utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final HashMap<String, Pattern> CACHED_PATTERNS = new HashMap<>();


    public static Wrapper parseMatch(Matcher matcher) {
        return parseMatch(matcher, false);
    }

    private static Wrapper parseMatch(Matcher matcher, boolean find) {
        if (find) {
            if (!matcher.find())
                throw new IllegalStateException("Matcher did not find a match in the input string");
        } else {
            if (!matcher.matches())
                throw new IllegalStateException("Matcher does not match the full input string");
        }
        return new Wrapper(matcher.toMatchResult());
    }

    public static Wrapper parseMatch(String regex, String input) {
        Pattern pattern = CACHED_PATTERNS.computeIfAbsent(regex, Pattern::compile);
        return parseMatch(pattern.matcher(input));
    }
}
