package aoc.day02;

import aoc.Day2024;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day02 extends Day2024 {

    public Day02() {
        super(2);
    }

    public static void main(String[] args) {
        new Day02().printParts();
    }

    @Override
    public Object part1() {

        int isSafe = 0;

        for(final String line : dayStrings()){
            List<Integer> values = parseIntegerValues(line);
            if(isValid(values)){
                isSafe++;
            }

        }
        return isSafe;
    }

    @Override
    public Object part2() {
        int isSafe = 0;

        for(final String line : dayStrings()){
            List<Integer> values = parseIntegerValues(line);
            if(isSafeWithDampener(values)){
                isSafe++;
            }

        }
        return isSafe;
    }

    public boolean isValid(List<Integer> levels) {
        if (levels == null || levels.size() < 2) {
            return true; // Une liste vide ou avec un seul élément est valide par défaut
        }

        boolean isIncreasing = levels.get(1) > levels.get(0);

        return IntStream.range(1, levels.size())
                .allMatch(i -> {
                    int difference = levels.get(i) - levels.get(i - 1);
                    // Vérifier la différence et la monotonie
                    return (Math.abs(difference) >= 1 && Math.abs(difference) <= 3) &&
                            (!(isIncreasing && difference < 0) && !(!isIncreasing && difference > 0));
                });
    }

    public boolean isSafeWithDampener(List<Integer> levels) {
        if (isValid(levels)) {
            return true; // Already safe
        }

        // Vérifier si la suppression d'un seul niveau rend la liste valide
        return IntStream.range(0, levels.size())
                .anyMatch(i -> {
                    List<Integer> modified = new ArrayList<>(levels);
                    modified.remove(i);
                    return isValid(modified);
                });
    }

    private List<Integer> parseIntegerValues(String line) {
        String[] temp = line.split("\\s+");
        List<Integer> values = new ArrayList<>(temp.length);
        for (String s : temp) {
            values.add(Integer.parseInt(s));
        }
        return values;
    }
}
