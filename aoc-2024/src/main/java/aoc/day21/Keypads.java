package aoc.day21;

import java.util.*;
import java.util.stream.Collectors;

public class Keypads {
    private final char[][] numericKeypad = {
        {'7', '8', '9'},
        {'4', '5', '6'},
        {'1', '2', '3'},
        {' ', '0', 'A'}
    };

    private final char[][] directionalKeypad = {
        {' ', '^', 'A'},
        {'<', 'v', '>'}
    };

    private final Map<int[], String> directions = Map.of(
        new int[]{-1, 0}, "^",
        new int[]{1, 0}, "v",
        new int[]{0, -1}, "<",
        new int[]{0, 1}, ">"
    );

    private final Map<String, int[]> reverseDirections = directions.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    private boolean isSafe(int x, int y, boolean num) {
        char[][] keypad = num ? numericKeypad : directionalKeypad;
        return x >= 0 && x < keypad.length && y >= 0 && y < keypad[0].length && keypad[x][y] != ' ';
    }

    public Set<String> allPaths(char startChar, char endChar, boolean num) {
        if (startChar == endChar) {
            return Set.of("A");
        }

        char[][] keypad = num ? numericKeypad : directionalKeypad;
        int[] start = findPosition(keypad, startChar);
        int[] end = findPosition(keypad, endChar);

        int dx = start[0] - end[0];
        int dy = start[1] - end[1];
        String path = (dx < 0 ? "v".repeat(Math.abs(dx)) : "^".repeat(Math.abs(dx))) +
                (dy < 0 ? ">".repeat(Math.abs(dy)) : "<".repeat(Math.abs(dy)));

        Set<String> correctOptions = new HashSet<>();
        List<String> permutations = generatePermutations(path);
        for (String p : permutations) {
            boolean valid = true;
            int curX = start[0], curY = start[1];
            for (char step : p.toCharArray()) {
                int[] move = reverseDirections.get(String.valueOf(step));
                curX += move[0];
                curY += move[1];
                if (!isSafe(curX, curY, num)) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                correctOptions.add(p + "A");
            }
        }

        return correctOptions;
    }

    public int shortestEndPath(String code, int depth, int maxDepth) {
        boolean num = depth == 1;
        int totalLength = 0;
        String start = "A";
        char startChar = start.charAt(0);

        for (char ch : code.toCharArray()) {
            Set<String> pathOptions = allPaths(startChar, ch, num);

            if (depth == maxDepth) {
                totalLength += pathOptions.stream().mapToInt(String::length).min().orElse(0);
            } else {
                Set<Integer> lengths = new HashSet<>();
                for (String pathOption : pathOptions) {
                    lengths.add(shortestEndPath(pathOption, depth + 1, maxDepth));
                }
                totalLength += lengths.stream().min(Integer::compare).orElse(0);
            }
            startChar = ch;
        }

        return totalLength;
    }

    public int[] calculateComplexity(String code, int maxDepth) {
        int number = Integer.parseInt(code.replaceAll("\\D", ""));
        int length = shortestEndPath(code, 1, maxDepth);
        return new int[]{length, number};
    }

    private int[] findPosition(char[][] keypad, char target) {
        for (int i = 0; i < keypad.length; i++) {
            for (int j = 0; j < keypad[i].length; j++) {
                if (keypad[i][j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("Character not found in keypad.");
    }

    private List<String> generatePermutations(String input) {
        if (input.length() == 1) {
            return List.of(input);
        }
        List<String> permutations = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            String remaining = input.substring(0, i) + input.substring(i + 1);
            for (String subPermutation : generatePermutations(remaining)) {
                permutations.add(current + subPermutation);
            }
        }
        return permutations;
    }
}
