package aoc.day19;

import aoc.Day;
import aoc.day19.helper.Helper;
import aoc.day19.helper.ParseUtils;
import aoc.day19.helper.RegexHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Day19 implements Day {

    private static Map<Integer, Rule> rules;
    private static List<String> messages;

    @Override
    public String part1(List<String> input) {
        parseInput(input);
        int matchCount = matchCount(false);
        return String.valueOf(matchCount);
    }

    @Override
    public String part2(List<String> input) {
        parseInput(input);
        int matchCount = matchCount(true);
        return String.valueOf(matchCount);
    }

    private static void parseInput(List<String> inputLines) {
        List<List<String>> input = Helper.split(inputLines, String::isBlank);
        rules = new HashMap<>();
        messages = input.get(1);

        for (String ruleStr : input.get(0)) {
            RegexHelper match = ParseUtils.parseMatch("(\\d+): (.+)", ruleStr);
            int id = match.groupInt(1);
            String raw = match.group(2);
            Rule rule = rules.computeIfAbsent(id, Rule::new);

            if (raw.startsWith("\"")) {
                rule.setRaw(Character.toString(raw.charAt(1)));
                rule.setLiteral(true);
            } else {
                rule.setLiteral(false);

                for (String groupStr : raw.split("\\|")) {
                    String[] split = groupStr.trim().split(" ");
                    List<Rule> group = new ArrayList<>(split.length);
                    for (String subId : split) {
                        group.add(rules.computeIfAbsent(Integer.parseInt(subId), Rule::new));
                    }
                    rule.children.add(group);
                }
            }
        }
    }

    private static int matchCount(boolean recursive) {
        String regex = rules.get(0).genRegex(recursive);
        Pattern pattern = Pattern.compile(regex);
        int count = 0;

        for (String message : messages) {
            if (pattern.matcher(message).matches())
                count++;
        }

        return count;
    }
}
