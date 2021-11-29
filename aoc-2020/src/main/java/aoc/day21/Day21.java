package aoc.day21;

import aoc.Day;
import aoc.utils.MultiMap;
import aoc.utils.Parser;
import aoc.utils.Processor;
import aoc.utils.Wrapper;

import java.util.*;

public class Day21 implements Day {

    private static Map<List<String>, Set<String>> foods;
    private static Set<String> allIngredients;
    private static MultiMap<String, String> allergenCandidates;

    @Override
    public String part1(List<String> input) {
        parseInput(input);
        allergenCandidates = new MultiMap<>();
        for (Map.Entry<List<String>, Set<String>> food : foods.entrySet()) {
            List<String> ingredients = food.getKey();
            Set<String> allergens = food.getValue();

            for (String allergen : allergens) {
                allergenCandidates.put(allergen, Processor.intersection(allergenCandidates.get(allergen), ingredients));
            }
        }

        Set<String> impossible = new HashSet<>(allIngredients);
        for (Set<String> possible : allergenCandidates.values()) {
            impossible.removeAll(possible);
        }

        long count = 0;
        for (List<String> ingredients : foods.keySet()) {
            for (String ingredient : ingredients) {
                if (impossible.contains(ingredient))
                    count++;
            }
        }

        return String.valueOf(count);
    }

    @Override
    public String part2(List<String> input) {
        Map<String, String> found = new TreeMap<>(Comparator.naturalOrder());

        // Similar to Day 16 part 2
        while (!allergenCandidates.isEmpty()) {
            for (Map.Entry<String, Set<String>> entry : allergenCandidates.entrySet()) {
                Set<String> set = entry.getValue();
                if (set.isEmpty())
                    continue;
                set.removeAll(found.values());
                if (set.size() == 1) {
                    found.put(entry.getKey(), set.iterator().next());
                }
            }
            allergenCandidates.entrySet().removeIf(e -> e.getValue().isEmpty());
        }

        String list = String.join(",", found.values());
        return list;
    }

    private void parseInput(List<String> input) {
        foods = new HashMap<>();
        allIngredients = new HashSet<>();

        for (String line : input) {
            Wrapper match = Parser.parseMatch("(.+) \\(contains (.+)\\)", line);

            List<String> ingredients = Arrays.asList(match.group(1).split(" "));
            Set<String> allergens = new HashSet<>(Arrays.asList(match.group(2).split(", ")));
            foods.put(ingredients, allergens);
            allIngredients.addAll(ingredients);
        }
    }
}
