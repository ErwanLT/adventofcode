package aoc.day23;

import aoc.Day2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day23 extends Day2024 {
    public Day23() {
        super(23, "LAN Party");
    }

    public static void main(String[] args) {
        new Day23().printParts();
    }

    @Override
    public Object part1() {
        // Parse the connections into an adjacency map
        Map<String, Set<String>> networkMap = new HashMap<>();
        for (String connection : dayStrings()) {
            String[] parts = connection.split("-");
            String a = parts[0], b = parts[1];
            networkMap.computeIfAbsent(a, k -> new HashSet<>()).add(b);
            networkMap.computeIfAbsent(b, k -> new HashSet<>()).add(a);
        }

        // Find all sets of three interconnected computers
        Set<Set<String>> triplets = new HashSet<>();
        for (String computer : networkMap.keySet()) {
            Set<String> neighbors = networkMap.get(computer);
            for (String neighbor1 : neighbors) {
                for (String neighbor2 : neighbors) {
                    if (!neighbor1.equals(neighbor2) && networkMap.get(neighbor1).contains(neighbor2)) {
                        // Create a sorted triplet to avoid duplicates
                        List<String> triplet = Arrays.asList(computer, neighbor1, neighbor2);
                        Collections.sort(triplet);
                        triplets.add(new HashSet<>(triplet));
                    }
                }
            }
        }

        // Filter triplets containing a computer whose name starts with 't'
        List<Set<String>> filteredTriplets = triplets.stream()
                .filter(triplet -> triplet.stream().anyMatch(name -> name.startsWith("t")))
                .toList();

        return filteredTriplets.size();
    }

    @Override
    public Object part2() {
        return findLargestClique(dayStrings());
    }

    public static String findLargestClique(String[] connections) {
        // Construire la carte des connexions
        Map<String, Set<String>> networkMap = new HashMap<>();
        for (String connection : connections) {
            String[] parts = connection.split("-");
            String a = parts[0], b = parts[1];
            networkMap.computeIfAbsent(a, k -> new HashSet<>()).add(b);
            networkMap.computeIfAbsent(b, k -> new HashSet<>()).add(a);
        }

        // Trouver la plus grande clique
        Set<String> largestClique = new HashSet<>();
        findCliques(new HashSet<>(), new HashSet<>(networkMap.keySet()), new HashSet<>(), networkMap, largestClique);

        // Générer le mot de passe à partir de la clique
        List<String> sortedClique = new ArrayList<>(largestClique);
        Collections.sort(sortedClique);
        return String.join(",", sortedClique);
    }

    private static void findCliques(Set<String> currentClique, Set<String> candidates, Set<String> excluded,
                                    Map<String, Set<String>> networkMap, Set<String> largestClique) {
        if (candidates.isEmpty() && excluded.isEmpty()) {
            // Vérifier si la clique actuelle est la plus grande
            if (currentClique.size() > largestClique.size()) {
                largestClique.clear();
                largestClique.addAll(currentClique);
            }
            return;
        }

        // Itérer sur les candidats
        Iterator<String> iterator = candidates.iterator();
        while (iterator.hasNext()) {
            String node = iterator.next();

            // Ajouter le nœud à la clique actuelle
            currentClique.add(node);

            // Construire les nouveaux ensembles de candidats et exclus
            Set<String> newCandidates = new HashSet<>();
            Set<String> newExcluded = new HashSet<>();
            for (String neighbor : networkMap.get(node)) {
                if (candidates.contains(neighbor)) {
                    newCandidates.add(neighbor);
                }
                if (excluded.contains(neighbor)) {
                    newExcluded.add(neighbor);
                }
            }

            // Appel récursif
            findCliques(currentClique, newCandidates, newExcluded, networkMap, largestClique);

            // Retirer le nœud et ajouter aux exclus
            currentClique.remove(node);
            excluded.add(node);
            iterator.remove();
        }
    }
}
