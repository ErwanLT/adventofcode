package aoc.day09;

import aoc.Day;

import java.util.*;

public class Day09 implements Day {
    @Override
    public String part1(List<String> input) {
        HashSet<String> towns = new HashSet<>();
        HashMap<String, Integer> dist = new HashMap<>();
        for (String l : input) {
            String[] p = l.split(" ");
            towns.add(p[0]);
            towns.add(p[2]);
            dist.put(p[0]+" "+p[2], Integer.parseInt(p[4]));
            dist.put(p[2]+" "+p[0], Integer.parseInt(p[4]));
        }

        ArrayList<String[]> perm = permutations(towns);
        int min = sumLength(perm.get(0), dist);
        for (int i = 1; i < perm.size(); i++) {
            min = Math.min(min, sumLength(perm.get(i), dist));
        }

        return String.valueOf(min);
    }

    @Override
    public String part2(List<String> input) {
        HashSet<String> towns = new HashSet<>();
        HashMap<String, Integer> dist = new HashMap<>();
        for (String l : input) {
            String[] p = l.split(" ");
            towns.add(p[0]);
            towns.add(p[2]);
            dist.put(p[0]+" "+p[2], Integer.parseInt(p[4]));
            dist.put(p[2]+" "+p[0], Integer.parseInt(p[4]));
        }

        ArrayList<String[]> perm = permutations(towns);
        int max = sumLength(perm.get(0), dist);
        for (int i = 1; i < perm.size(); i++) {
            max = Math.max(max, sumLength(perm.get(i), dist));
        }

        return String.valueOf(max);
    }

    public static ArrayList<String[]> permutations(Set<String> towns) {
        ArrayList<String[]> list = new ArrayList<>();
        if (towns.size() == 1) {
            list.add(new String[]{ towns.iterator().next() });
            return list;
        }

        HashSet<String> copy = new HashSet<>(towns);
        for (String town : towns) {
            copy.remove(town);
            ArrayList<String[]> smallPerms = permutations(copy);
            for (String[] smallPerm : smallPerms) {
                String[] newPerm = new String[smallPerm.length + 1];
                System.arraycopy(smallPerm, 0, newPerm, 0, smallPerm.length);
                newPerm[smallPerm.length] = town;
                list.add(newPerm);
            }
            copy.add(town);
        }
        return list;
    }

    public static int sumLength(String[] towns, Map<String, Integer> dist) {
        int sum = 0;
        for (int i = 0; i < towns.length-1; i++) {
            sum += dist.get(towns[i] + " " + towns[i+1]);
        }
        return sum;
    }
}
