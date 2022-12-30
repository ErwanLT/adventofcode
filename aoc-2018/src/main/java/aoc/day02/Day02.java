package aoc.day02;

import aoc.DayOld;
import aoc.parser.ParseUtils;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.empty;

public class Day02 implements DayOld {
    @Override
    public String part1(List<String> input) {
        int two = 0, three = 0;
        String[] in = ParseUtils.castInoutToStringArray(input);
        for(String i : in){
            List<Integer> counts = i.chars().mapToObj(c -> (i+"_").split(Character.toString(c)).length - 1).toList();
            if(counts.contains(2)) {
                two++;
            }
            if(counts.contains(3)) {
                three++;
            }
        }
        return String.valueOf(two * three);
    }

    @Override
    public String part2(List<String> input) {
        var com = Sets.combinations(ParseUtils.castInputToStream(input).collect(Collectors.toSet()), 2);
        return com.stream().map(this::compareStrings).filter(Optional::isPresent).map(Optional::get).findFirst().get();
    }

    private Optional<String> compareStrings(Set<String> str) {
        var it = str.iterator();
        String str1 = it.next(), str2 = it.next();
        if (str1.length() != str2.length() || str1.equals(str2)) return empty();
        int differences = 0;
        int diffIndex = -1;
        for (int i = 0; i < str1.length(); i++) {
            if(str1.charAt(i) != str2.charAt(i)) {
                diffIndex = i;
                if (++differences > 1) {
                    return empty();
                }
            }
        }
        return Optional.of(new StringBuilder(str1).deleteCharAt(diffIndex).toString());
    }
}
