package aoc.day10;

import aoc.Day2025;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 extends Day2025 {

    public Day10() {
        super(10, "Factory");
    }

    @Override
    public Object part1() {
        String[] lines = dayStrings();
        long totalPresses = 0;
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            totalPresses += solveMachinePart1(line);
        }
        return totalPresses;
    }

    private int solveMachinePart1(String line) {
        Pattern lightsPattern = Pattern.compile("\\[([.#]+)]");
        Matcher lightsMatcher = lightsPattern.matcher(line);
        if (!lightsMatcher.find()) {
            throw new IllegalArgumentException("Invalid line (lights): " + line);
        }
        String lightsStr = lightsMatcher.group(1);
        int numLights = lightsStr.length();
        int[] target = new int[numLights];
        for (int i = 0; i < numLights; i++) {
            target[i] = lightsStr.charAt(i) == '#' ? 1 : 0;
        }

        List<int[]> buttons = new ArrayList<>();
        Pattern buttonPattern = Pattern.compile("\\(([^)]+)\\)");
        Matcher buttonMatcher = buttonPattern.matcher(line);
        while (buttonMatcher.find()) {
            int[] button = new int[numLights];
            String[] indices = buttonMatcher.group(1).split(",");
            for (String indexStr : indices) {
                int index = Integer.parseInt(indexStr.trim());
                if (index < numLights) {
                    button[index] = 1;
                }
            }
            buttons.add(button);
        }
        int numButtons = buttons.size();
        if (numButtons == 0) {
            for (int i = 0; i < numLights; i++) {
                if (target[i] == 1)
                    return Integer.MAX_VALUE;
            }
            return 0;
        }

        int[][] matrix = new int[numLights][numButtons + 1];
        for (int j = 0; j < numButtons; j++) {
            int[] button = buttons.get(j);
            for (int i = 0; i < numLights; i++) {
                matrix[i][j] = button[i];
            }
        }
        for (int i = 0; i < numLights; i++) {
            matrix[i][numButtons] = target[i];
        }

        int pivotRow = 0;
        List<Integer> pivotCols = new ArrayList<>();
        for (int j = 0; j < numButtons && pivotRow < numLights; j++) {
            int i = pivotRow;
            while (i < numLights && matrix[i][j] == 0) {
                i++;
            }

            if (i < numLights) {
                int[] temp = matrix[i];
                matrix[i] = matrix[pivotRow];
                matrix[pivotRow] = temp;

                for (int k = 0; k < numLights; k++) {
                    if (k != pivotRow && matrix[k][j] == 1) {
                        for (int l = j; l <= numButtons; l++) {
                            matrix[k][l] ^= matrix[pivotRow][l];
                        }
                    }
                }
                pivotCols.add(j);
                pivotRow++;
            }
        }

        for (int i = pivotRow; i < numLights; i++) {
            if (matrix[i][numButtons] == 1) {
                return Integer.MAX_VALUE;
            }
        }

        List<Integer> freeCols = new ArrayList<>();
        for (int j = 0; j < numButtons; j++) {
            if (!pivotCols.contains(j)) {
                freeCols.add(j);
            }
        }

        int minPresses = Integer.MAX_VALUE;
        int numFreeVars = freeCols.size();
        for (int i = 0; i < (1 << numFreeVars); i++) {
            int[] solution = new int[numButtons];
            int currentPresses = 0;

            for (int k = 0; k < numFreeVars; k++) {
                if (((i >> k) & 1) == 1) {
                    solution[freeCols.get(k)] = 1;
                }
            }

            for (int k = pivotRow - 1; k >= 0; k--) {
                int pivotCol = -1;
                for (int l = 0; l < numButtons; l++) {
                    if (matrix[k][l] == 1) {
                        pivotCol = l;
                        break;
                    }
                }

                if (pivotCol != -1) {
                    int val = matrix[k][numButtons];
                    for (int fcIndex : freeCols) {
                        if (matrix[k][fcIndex] == 1) {
                            val ^= solution[fcIndex];
                        }
                    }
                    solution[pivotCol] = val;
                }
            }

            currentPresses = 0;
            for (int s : solution) {
                if (s == 1)
                    currentPresses++;
            }

            minPresses = Math.min(minPresses, currentPresses);
        }

        return minPresses == Integer.MAX_VALUE ? 0 : minPresses;
    }

    @Override
    public Object part2() {
        String[] lines = dayStrings();
        long totalPresses = 0;
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            totalPresses += solveMachinePart2(line);
        }
        return totalPresses;
    }

    private int solveMachinePart2(String line) {
        Pattern joltagePattern = Pattern.compile("\\{([^}]+)\\}");
        Matcher joltageMatcher = joltagePattern.matcher(line);
        if (!joltageMatcher.find()) {
            throw new IllegalArgumentException("Invalid line (joltage): " + line);
        }
        String[] joltageStrs = joltageMatcher.group(1).split(",");
        int numCounters = joltageStrs.length;
        int[] targetJoltage = new int[numCounters];
        for (int i = 0; i < numCounters; i++) {
            targetJoltage[i] = Integer.parseInt(joltageStrs[i].trim());
        }

        List<int[]> buttons = new ArrayList<>();
        Pattern buttonPattern = Pattern.compile("\\(([^)]+)\\)");
        Matcher buttonMatcher = buttonPattern.matcher(line);
        while (buttonMatcher.find()) {
            int[] button = new int[numCounters];
            String[] indices = buttonMatcher.group(1).split(",");
            for (String indexStr : indices) {
                int index = Integer.parseInt(indexStr.trim());
                if (index < numCounters) {
                    button[index] = 1;
                }
            }
            buttons.add(button);
        }
        int numButtons = buttons.size();

        if (numButtons == 0) {
            for (int target : targetJoltage) {
                if (target != 0)
                    return Integer.MAX_VALUE;
            }
            return 0;
        }

        // Build augmented matrix [A|b] where Ax = b
        double[][] matrix = new double[numCounters][numButtons + 1];
        for (int i = 0; i < numCounters; i++) {
            for (int j = 0; j < numButtons; j++) {
                matrix[i][j] = buttons.get(j)[i];
            }
            matrix[i][numButtons] = targetJoltage[i];
        }

        // Gaussian elimination to RREF
        int pivotRow = 0;
        List<Integer> pivotCols = new ArrayList<>();

        for (int col = 0; col < numButtons && pivotRow < numCounters; col++) {
            // Find pivot
            int maxRow = pivotRow;
            for (int row = pivotRow + 1; row < numCounters; row++) {
                if (Math.abs(matrix[row][col]) > Math.abs(matrix[maxRow][col])) {
                    maxRow = row;
                }
            }

            if (Math.abs(matrix[maxRow][col]) < 1e-9) {
                continue; // No pivot in this column
            }

            // Swap rows
            double[] temp = matrix[maxRow];
            matrix[maxRow] = matrix[pivotRow];
            matrix[pivotRow] = temp;

            // Scale pivot row
            double pivot = matrix[pivotRow][col];
            for (int j = col; j <= numButtons; j++) {
                matrix[pivotRow][j] /= pivot;
            }

            // Eliminate column
            for (int row = 0; row < numCounters; row++) {
                if (row != pivotRow && Math.abs(matrix[row][col]) > 1e-9) {
                    double factor = matrix[row][col];
                    for (int j = col; j <= numButtons; j++) {
                        matrix[row][j] -= factor * matrix[pivotRow][j];
                    }
                }
            }

            pivotCols.add(col);
            pivotRow++;
        }

        // Check for inconsistency
        for (int row = pivotRow; row < numCounters; row++) {
            if (Math.abs(matrix[row][numButtons]) > 1e-9) {
                return Integer.MAX_VALUE; // No solution
            }
        }

        // Identify free variables
        List<Integer> freeCols = new ArrayList<>();
        for (int col = 0; col < numButtons; col++) {
            if (!pivotCols.contains(col)) {
                freeCols.add(col);
            }
        }

        // Calculate upper bounds for each variable
        int[] upperBounds = new int[numButtons];
        for (int j = 0; j < numButtons; j++) {
            int maxBound = Integer.MAX_VALUE;
            for (int i = 0; i < numCounters; i++) {
                if (buttons.get(j)[i] == 1 && targetJoltage[i] < maxBound) {
                    maxBound = targetJoltage[i];
                }
            }
            upperBounds[j] = maxBound == Integer.MAX_VALUE ? 0 : maxBound;
        }

        // Search over free variables
        int minPresses = Integer.MAX_VALUE;
        int numFreeVars = freeCols.size();

        if (numFreeVars == 0) {
            // Unique solution, check if valid
            int[] solution = new int[numButtons];
            boolean valid = true;

            for (int i = 0; i < pivotCols.size(); i++) {
                int col = pivotCols.get(i);
                double val = matrix[i][numButtons];

                if (Math.abs(val - Math.round(val)) > 1e-9 || val < -1e-9) {
                    valid = false;
                    break;
                }
                solution[col] = (int) Math.round(val);
            }

            if (valid) {
                int total = 0;
                for (int s : solution) {
                    total += s;
                }
                minPresses = total;
            }
        } else {
            // Enumerate free variable assignments
            minPresses = searchFreeVariables(matrix, pivotCols, freeCols, upperBounds, numButtons, 0,
                    new int[numButtons]);
        }

        return minPresses == Integer.MAX_VALUE ? 0 : minPresses;
    }

    private int searchFreeVariables(double[][] matrix, List<Integer> pivotCols, List<Integer> freeCols,
                                    int[] upperBounds, int numButtons, int freeVarIndex, int[] solution) {
        if (freeVarIndex == freeCols.size()) {
            // Compute pivot variables
            for (int i = 0; i < pivotCols.size(); i++) {
                int pivotCol = pivotCols.get(i);
                double val = matrix[i][numButtons];

                // Subtract contributions from free variables
                for (int freeCol : freeCols) {
                    val -= matrix[i][freeCol] * solution[freeCol];
                }

                // Check if valid integer and non-negative
                if (Math.abs(val - Math.round(val)) > 1e-9 || val < -1e-9) {
                    return Integer.MAX_VALUE;
                }
                solution[pivotCol] = (int) Math.round(val);
            }

            // Calculate total presses
            int total = 0;
            for (int s : solution) {
                total += s;
            }
            return total;
        }

        int freeCol = freeCols.get(freeVarIndex);
        int minResult = Integer.MAX_VALUE;

        for (int val = 0; val <= upperBounds[freeCol]; val++) {
            solution[freeCol] = val;
            int result = searchFreeVariables(matrix, pivotCols, freeCols, upperBounds, numButtons, freeVarIndex + 1,
                    solution);
            minResult = Math.min(minResult, result);
        }

        return minResult;
    }
}
