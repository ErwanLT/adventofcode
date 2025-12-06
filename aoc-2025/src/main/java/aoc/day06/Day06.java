package aoc.day06;

import aoc.Day2025;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Solution pour le jour 6 de l'Advent of Code 2025, "Trash Compactor".
 * Cette classe résout une feuille de calcul de mathématiques céphalopode en analysant des problèmes disposés en colonnes.
 * Les règles d'analyse des nombres changent entre la partie 1 et la partie 2.
 */
public class Day06 extends Day2025 {

    private final List<String> lines;
    /**
     * Représente un unique problème mathématique analysé.
     * @param numbers La liste des nombres dans le problème.
     * @param operator L'opérateur ('+' ou '*') à appliquer aux nombres.
     */
    private record Problem(List<Long> numbers, char operator) {}

    public Day06() {
        super(6, "Trash Compactor");
        this.lines = dayStream().collect(Collectors.toList());
    }

    /**
     * Résout la partie 1 du puzzle.
     * Dans la partie 1, les nombres sont analysés horizontalement à partir du bloc de texte du problème.
     * @return Le total général de tous les résultats des problèmes.
     */
    @Override
    public Object part1() {
        return calculateTotal(this::parseProblemPart1);
    }

    /**
     * Résout la partie 2 du puzzle.
     * Dans la partie 2, les nombres sont analysés verticalement, chaque colonne de chiffres dans un bloc de problème formant un nombre.
     * @return Le total général de tous les résultats des problèmes.
     */
    @Override
    public Object part2() {
        return calculateTotal(this::parseProblemPart2);
    }

    /**
     * Méthode générique pour calculer la solution pour l'une ou l'autre partie du puzzle.
     * Elle orchestre l'analyse des blocs de problèmes et la sommation de leurs résultats.
     * @param problemParser Une fonction qui définit comment analyser un seul problème à partir de sa représentation en colonnes.
     * @return Le total général calculé.
     */
    private long calculateTotal(Function<List<String>, Problem> problemParser) {
        if (lines.isEmpty()) {
            return 0L;
        }

        List<List<String>> problemColumnGroups = getProblemColumnGroups(lines);
        long grandTotal = 0;

        for (List<String> problemCols : problemColumnGroups) {
            Problem problem = problemParser.apply(problemCols);
            if (problem.operator() != ' ') {
                grandTotal += calculateProblemResult(problem);
            }
        }
        return grandTotal;
    }

    /**
     * Calcule le résultat d'un seul problème en fonction de ses nombres et de son opérateur.
     * @param problem Le problème à calculer.
     * @return Le résultat de l'application de l'opérateur aux nombres (somme pour '+' ou produit pour '*').
     */
    private long calculateProblemResult(Problem problem) {
        if (problem.operator() == '+') {
            return problem.numbers().stream().mapToLong(Long::longValue).sum();
        } else { // operator == '*'
            return problem.numbers().stream().reduce(1L, (a, b) -> a * b);
        }
    }

    /**
     * Analyse un problème selon les règles de la partie 1.
     * Elle transpose les colonnes du problème en lignes et lit les nombres horizontalement.
     * @param problemCols La liste des chaînes de colonnes pour un seul problème.
     * @return Un objet {@link Problem} contenant les nombres et l'opérateur analysés.
     */
    private Problem parseProblemPart1(List<String> problemCols) {
        List<String> problemRows = transpose(problemCols);
        List<Long> numbers = new ArrayList<>();
        char operator = ' ';

        for (String row : problemRows) {
            String[] tokens = row.trim().split("\\s+");
            for (String token : tokens) {
                if (token.equals("+")) {
                    operator = '+';
                } else if (token.equals("*")) {
                    operator = '*';
                } else if (!token.isEmpty()) {
                    numbers.add(Long.parseLong(token));
                }
            }
        }
        return new Problem(numbers, operator);
    }

    /**
     * Analyse un problème selon les règles de la partie 2.
     * Elle lit chaque colonne du bloc de problème comme un seul nombre vertical.
     * @param problemCols La liste des chaînes de colonnes pour un seul problème.
     * @return Un objet {@link Problem} contenant les nombres et l'opérateur analysés.
     */
    private Problem parseProblemPart2(List<String> problemCols) {
        List<Long> numbers = new ArrayList<>();
        char operator = ' ';

        for (String column : problemCols) {
            StringBuilder numStr = new StringBuilder();
            for (char c : column.toCharArray()) {
                if (Character.isDigit(c)) {
                    numStr.append(c);
                } else if (c == '+' || c == '*') {
                    operator = c;
                }
            }
            if (numStr.length() > 0) {
                numbers.add(Long.parseLong(numStr.toString()));
            }
        }
        return new Problem(numbers, operator);
    }

    /**
     * Analyse la grille d'entrée pour identifier et regrouper les colonnes appartenant à chaque problème.
     * Les problèmes sont séparés par des colonnes entièrement composées d'espaces.
     * @param lines L'entrée complète du puzzle.
     * @return Une liste où chaque élément est une liste de chaînes de colonnes pour un problème.
     */
    private List<List<String>> getProblemColumnGroups(List<String> lines) {
        List<String> rotatedLines = transpose(lines);

        List<List<String>> problemColumnGroups = new ArrayList<>();
        List<String> currentProblemColumns = new ArrayList<>();

        for (String rotatedLine : rotatedLines) {
            if (rotatedLine.trim().isEmpty()) { // This was a column of spaces
                if (!currentProblemColumns.isEmpty()) {
                    problemColumnGroups.add(currentProblemColumns);
                    currentProblemColumns = new ArrayList<>();
                }
            } else {
                currentProblemColumns.add(rotatedLine);
            }
        }
        if (!currentProblemColumns.isEmpty()) {
            problemColumnGroups.add(currentProblemColumns);
        }
        return problemColumnGroups;
    }

    /**
     * Transpose une matrice de caractères représentée comme une liste de chaînes.
     * Peut gérer des lignes de longueurs différentes en les complétant avec des espaces pour former une grille rectangulaire.
     * @param matrix La grille à transposer, où chaque chaîne est une ligne.
     * @return Une nouvelle liste de chaînes où chaque chaîne représente une colonne de la grille d'origine.
     */
    private List<String> transpose(List<String> matrix) {
        if (matrix.isEmpty()) {
            return Collections.emptyList();
        }

        int maxWidth = matrix.stream().mapToInt(String::length).max().orElse(0);
        int numRows = matrix.size();

        List<StringBuilder> builders = new ArrayList<>();
        for (int i = 0; i < maxWidth; i++) {
            builders.add(new StringBuilder(numRows));
        }

        for (String row : matrix) {
            for (int i = 0; i < maxWidth; i++) {
                builders.get(i).append(i < row.length() ? row.charAt(i) : ' ');
            }
        }

        return builders.stream().map(StringBuilder::toString).collect(Collectors.toList());
    }
}
