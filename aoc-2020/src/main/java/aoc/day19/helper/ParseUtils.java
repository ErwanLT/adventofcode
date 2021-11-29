package aoc.day19.helper;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtils {
    private static final Pattern pattern = Pattern.compile("(\\d+,\\d+)");
    private static final HashMap<String, Pattern> CACHED_PATTERNS = new HashMap<>();

    

    public static void parseLines(Pattern pattern, List<String> lines, Consumer<RegexHelper> consumer) {
        for (String line : lines) {
            consumer.accept(parseMatch(pattern, line));
        }
    }


    public static RegexHelper parseMatch(Pattern pattern, String input) {
        return parseMatch(pattern.matcher(input));
    }

    public static RegexHelper parseMatch(Matcher matcher) {
        return parseMatch(matcher, false);
    }

    private static RegexHelper parseMatch(Matcher matcher, boolean find) {
        if (find) {
            if (!matcher.find())
                throw new IllegalStateException("Matcher did not find a match in the input string");
        } else {
            if (!matcher.matches())
                throw new IllegalStateException("Matcher does not match the full input string");
        }
        return new RegexHelper(matcher.toMatchResult());
    }

    public static RegexHelper parseMatch(String regex, String input) {
        Pattern pattern = CACHED_PATTERNS.computeIfAbsent(regex, Pattern::compile);
        return parseMatch(pattern.matcher(input));
    }

    public static RegexHelper findFirstMatch(String regex, String input) {
        Pattern pattern = CACHED_PATTERNS.computeIfAbsent(regex, Pattern::compile);
        return parseMatch(pattern.matcher(input), true);
    }
}
