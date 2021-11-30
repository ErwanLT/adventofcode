package aoc.day16;

import aoc.Day;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import java.util.*;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ArrayUtils.subarray;

public class Day16 implements Day {

    private static String inputs;

    private static Rules[] rules;
    private static long[] myTicket;
    private static List<List<Long>> tickets;

    @Override
    public String part1(List<String> input) {
        castInput(input);
        long ticketScanningErrorRate = tickets.stream().flatMapToLong(t -> t.stream().filter(n -> stream(rules).noneMatch(r -> r.check(n))).mapToLong(e -> e)).sum();
        return String.valueOf(ticketScanningErrorRate);
    }

    @Override
    public String part2(List<String> input) {
        castInput(input);
        List<List<Long>> valid = tickets.stream().filter(t -> t.stream().allMatch(n -> stream(rules).anyMatch(r -> r.check(n)))).collect(toList());

        Multimap<Integer, Rules> ruleIndex = MultimapBuilder.hashKeys().arrayListValues().build();
        for (Rules r : rules) {
            for (int j = 0; j < valid.get(0).size(); j++) {
                int finalJ = j;
                if (valid.stream().allMatch(t -> r.check(t.get(finalJ)))) {
                    ruleIndex.put(j, r);
                }
            }
        }

        Optional<Map.Entry<Integer, Collection<Rules>>> rs;
        Set<Integer> indices = new HashSet<>();
        while ((rs = ruleIndex.asMap().entrySet().stream().filter(e -> e.getValue().size() == 1 && !indices.contains(e.getKey())).findAny()).isPresent()) {
            Map.Entry<Integer, Collection<Rules>> r = rs.get();
            int index = r.getKey();
            Rules rule = ((List<Rules>) r.getValue()).get(0);
            for (int i = 0; i < rules.length; i++) {
                Map.Entry<Integer, Collection<Rules>> t = new ArrayList<>(ruleIndex.asMap().entrySet()).get(i);
                if (t.getKey() != index) {
                    t.getValue().remove(rule);
                }
            }
            indices.add(index);
        }

        long ticketScanningErrorRate = ruleIndex.asMap().entrySet().stream().filter(e -> e.getValue().stream().anyMatch(Rules::isDeparture)).mapToLong(e -> myTicket[e.getKey()]).reduce((a, b) -> a * b).getAsLong();
        return String.valueOf(ticketScanningErrorRate);
    }

    private void castInput(List<String> input) {
        inputs = String.join("\n", input);
        String[] inputArray = inputs.split("\n\n");
        rules = stream(inputArray[0].split("\n")).map(s -> Utils.readString(s, "%s: %n-%n or %n-%n", Rules.class)).toArray(Rules[]::new);
        myTicket = stream(inputArray[1].split("\n")[1].split(",")).mapToLong(Long::parseLong).toArray();
        String[] ticketStrings = inputArray[2].split("\n");
        tickets = stream(subarray(ticketStrings, 1, ticketStrings.length)).map(s -> stream(s.split(",")).map(Long::parseLong).collect(toList())).collect(toList());
    }
}
